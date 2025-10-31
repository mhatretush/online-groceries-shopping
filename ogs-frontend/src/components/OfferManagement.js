import React, { useState } from 'react';
import { offerAPI } from '../services/apiService';

function OfferManagement() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [response, setResponse] = useState(null);
  
  const [offerForm, setOfferForm] = useState({
    code: '',
    validFrom: '',
    validTill: '',
    discount: '',
    discountType: 'PERCENTAGE'
  });

  const [getOfferId, setGetOfferId] = useState('');
  const [validateCode, setValidateCode] = useState('');
  const [applyOffer, setApplyOffer] = useState({ code: '', amount: '' });

  const handleCreateOffer = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    const data = {
      ...offerForm,
      discount: parseFloat(offerForm.discount)
    };

    try {
      const res = await offerAPI.createOffer(data);
      setResponse({ success: true, data: res.data });
      setOfferForm({
        code: '',
        validFrom: '',
        validTill: '',
        discount: '',
        discountType: 'PERCENTAGE'
      });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleGetOffer = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      const res = await offerAPI.getOfferById(parseInt(getOfferId));
      setResponse({ success: true, data: res.data });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleValidateOffer = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      const res = await offerAPI.validateOffer(validateCode);
      setResponse({ success: true, data: { valid: res.data, code: validateCode } });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleApplyOffer = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      const res = await offerAPI.applyOffer(applyOffer.code, parseFloat(applyOffer.amount));
      setResponse({ 
        success: true, 
        data: { 
          originalAmount: applyOffer.amount,
          discountedAmount: res.data,
          savings: parseFloat(applyOffer.amount) - res.data
        } 
      });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="management-page">
      <h1>🎁 Offer Management</h1>

      <div className="forms-container">
        <div className="form-section">
          <h2>Create Offer</h2>
          <form onSubmit={handleCreateOffer}>
            <input
              type="text"
              placeholder="Offer Code"
              value={offerForm.code}
              onChange={(e) => setOfferForm({...offerForm, code: e.target.value})}
              required
            />
            <input
              type="date"
              placeholder="Valid From"
              value={offerForm.validFrom}
              onChange={(e) => setOfferForm({...offerForm, validFrom: e.target.value})}
              required
            />
            <input
              type="date"
              placeholder="Valid Till"
              value={offerForm.validTill}
              onChange={(e) => setOfferForm({...offerForm, validTill: e.target.value})}
              required
            />
            <input
              type="number"
              step="0.01"
              placeholder="Discount"
              value={offerForm.discount}
              onChange={(e) => setOfferForm({...offerForm, discount: e.target.value})}
              required
            />
            <select
              value={offerForm.discountType}
              onChange={(e) => setOfferForm({...offerForm, discountType: e.target.value})}
            >
              <option value="PERCENTAGE">Percentage</option>
              <option value="FLAT">FLAT</option>
            </select>
            <button type="submit" disabled={loading}>Create Offer</button>
          </form>
        </div>

        <div className="form-section">
          <h2>Get Offer by ID</h2>
          <form onSubmit={handleGetOffer}>
            <input
              type="number"
              placeholder="Offer ID"
              value={getOfferId}
              onChange={(e) => setGetOfferId(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>Get Offer</button>
          </form>
        </div>

        <div className="form-section">
          <h2>Validate Offer Code</h2>
          <form onSubmit={handleValidateOffer}>
            <input
              type="text"
              placeholder="Offer Code"
              value={validateCode}
              onChange={(e) => setValidateCode(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>Validate</button>
          </form>
        </div>

        <div className="form-section">
          <h2>Apply Offer</h2>
          <form onSubmit={handleApplyOffer}>
            <input
              type="text"
              placeholder="Offer Code"
              value={applyOffer.code}
              onChange={(e) => setApplyOffer({...applyOffer, code: e.target.value})}
              required
            />
            <input
              type="number"
              step="0.01"
              placeholder="Amount"
              value={applyOffer.amount}
              onChange={(e) => setApplyOffer({...applyOffer, amount: e.target.value})}
              required
            />
            <button type="submit" disabled={loading}>Apply Offer</button>
          </form>
        </div>
      </div>

      {error && <div className="error-message">{error}</div>}
      
      {response && (
        <div className="response-section">
          <h3>API Response:</h3>
          <pre>{JSON.stringify(response, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default OfferManagement;
