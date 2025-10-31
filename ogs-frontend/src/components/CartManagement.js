import React, { useState } from 'react';
import { cartAPI, offerAPI } from '../services/apiService';

function CartManagement() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [cartData, setCartData] = useState(null);
  const [response, setResponse] = useState(null);
  
  const [addToCartForm, setAddToCartForm] = useState({
    userId: '',
    productId: '',
    quantity: ''
  });

  const [viewCartUserId, setViewCartUserId] = useState('');
  const [removeForm, setRemoveForm] = useState({
    userId: '',
    productId: ''
  });

  // New state for offer application
  const [applyOfferForm, setApplyOfferForm] = useState({
    userId: '',
    offerCode: ''
  });
  const [appliedOffer, setAppliedOffer] = useState(null);
  const [cartTotal, setCartTotal] = useState(0);
  const [discountedTotal, setDiscountedTotal] = useState(0);

  const handleAddToCart = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    const data = {
      userId: parseInt(addToCartForm.userId),
      productId: parseInt(addToCartForm.productId),
      quantity: parseInt(addToCartForm.quantity)
    };

    try {
      const res = await cartAPI.addToCart(data);
      setResponse({ success: true, data: res.data });
      setAddToCartForm({ userId: '', productId: '', quantity: '' });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleViewCart = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setAppliedOffer(null);
    setDiscountedTotal(0);
    
    try {
      const res = await cartAPI.viewCart(parseInt(viewCartUserId));
      setCartData(res.data);
      
      // Calculate cart total
      let total = 0;
      if (res.data && res.data.cartItems) {
        res.data.cartItems.forEach(item => {
          total += (item.product.productPrice * item.quantity);
        });
      }
      setCartTotal(total);
      setDiscountedTotal(total);
      
      setResponse({ success: true, data: res.data });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleRemoveFromCart = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      const res = await cartAPI.removeFromCart(
        parseInt(removeForm.userId),
        parseInt(removeForm.productId)
      );
      setResponse({ success: true, data: res.data });
      setRemoveForm({ userId: '', productId: '' });
      
      if (viewCartUserId === removeForm.userId) {
        const cartRes = await cartAPI.viewCart(parseInt(viewCartUserId));
        setCartData(cartRes.data);
      }
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  // NEW: Apply offer to cart
  const handleApplyOfferToCart = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      // Validate offer code
      const validateRes = await offerAPI.validateOffer(applyOfferForm.offerCode);
      
      if (!validateRes.data) {
        throw new Error('Invalid offer code');
      }

      // Apply offer and get discounted amount
      const applyRes = await offerAPI.applyOffer(applyOfferForm.offerCode, cartTotal);
      
      setAppliedOffer({
        code: applyOfferForm.offerCode,
        originalAmount: cartTotal,
        discountedAmount: applyRes.data,
        savings: cartTotal - applyRes.data
      });
      
      setDiscountedTotal(applyRes.data);
      setResponse({ 
        success: true, 
        data: {
          message: 'Offer applied successfully!',
          offerCode: applyOfferForm.offerCode,
          originalAmount: cartTotal,
          discountedAmount: applyRes.data,
          savings: cartTotal - applyRes.data
        }
      });
      
      setApplyOfferForm({ userId: '', offerCode: '' });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="management-page">
      <h1>🛒 Cart Management</h1>

      <div className="forms-container">
        <div className="form-section">
          <h2>Add to Cart</h2>
          <form onSubmit={handleAddToCart}>
            <input
              type="number"
              placeholder="User ID"
              value={addToCartForm.userId}
              onChange={(e) => setAddToCartForm({...addToCartForm, userId: e.target.value})}
              required
            />
            <input
              type="number"
              placeholder="Product ID"
              value={addToCartForm.productId}
              onChange={(e) => setAddToCartForm({...addToCartForm, productId: e.target.value})}
              required
            />
            <input
              type="number"
              placeholder="Quantity"
              value={addToCartForm.quantity}
              onChange={(e) => setAddToCartForm({...addToCartForm, quantity: e.target.value})}
              required
            />
            <button type="submit" disabled={loading}>Add to Cart</button>
          </form>
        </div>

        <div className="form-section">
          <h2>View Cart</h2>
          <form onSubmit={handleViewCart}>
            <input
              type="number"
              placeholder="User ID"
              value={viewCartUserId}
              onChange={(e) => setViewCartUserId(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>View Cart</button>
          </form>
        </div>

        <div className="form-section">
          <h2>Remove from Cart</h2>
          <form onSubmit={handleRemoveFromCart}>
            <input
              type="number"
              placeholder="User ID"
              value={removeForm.userId}
              onChange={(e) => setRemoveForm({...removeForm, userId: e.target.value})}
              required
            />
            <input
              type="number"
              placeholder="Product ID"
              value={removeForm.productId}
              onChange={(e) => setRemoveForm({...removeForm, productId: e.target.value})}
              required
            />
            <button type="submit" disabled={loading}>Remove from Cart</button>
          </form>
        </div>
      </div>

      {/* NEW: Apply Offer Section */}
      {cartData && (
        <div className="form-section offer-section">
          <h2>🎁 Apply Offer Code</h2>
          <form onSubmit={handleApplyOfferToCart}>
            <input
              type="text"
              placeholder="Enter Offer Code"
              value={applyOfferForm.offerCode}
              onChange={(e) => setApplyOfferForm({...applyOfferForm, offerCode: e.target.value})}
              required
            />
            <button type="submit" disabled={loading}>Apply Offer</button>
          </form>

          {/* Display cart totals */}
          <div className="cart-totals">
            <div className="total-row">
              <span>Subtotal:</span>
              <span>₹{cartTotal.toFixed(2)}</span>
            </div>
            
            {appliedOffer && (
              <>
                <div className="total-row discount">
                  <span>Offer Code ({appliedOffer.code}):</span>
                  <span>-₹{appliedOffer.savings.toFixed(2)}</span>
                </div>
                <div className="total-row final">
                  <span><strong>Total after Discount:</strong></span>
                  <span><strong>₹{discountedTotal.toFixed(2)}</strong></span>
                </div>
              </>
            )}
          </div>
        </div>
      )}

      {error && <div className="error-message">{error}</div>}
      
      {response && (
        <div className="response-section">
          <h3>API Response:</h3>
          <pre>{JSON.stringify(response, null, 2)}</pre>
        </div>
      )}

      {cartData && (
        <div className="data-section">
          <h2>Cart Details</h2>
          <pre>{JSON.stringify(cartData, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default CartManagement;
