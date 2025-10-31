import React, { useState } from 'react';
import { orderAPI, cartAPI, offerAPI } from '../services/apiService';

function OrderManagement() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [orderData, setOrderData] = useState(null);
  const [response, setResponse] = useState(null);
  
  const [placeOrderUserId, setPlaceOrderUserId] = useState('');
  const [viewOrderId, setViewOrderId] = useState('');
  const [userOrdersId, setUserOrdersId] = useState('');

  // NEW: Checkout with offer
  const [checkoutForm, setCheckoutForm] = useState({
    userId: '',
    offerCode: ''
  });
  const [checkoutSummary, setCheckoutSummary] = useState(null);

  const handlePlaceOrder = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      const res = await orderAPI.placeOrder(parseInt(placeOrderUserId));
      setOrderData(res.data);
      setResponse({ success: true, data: res.data });
      setPlaceOrderUserId('');
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleViewOrder = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      const res = await orderAPI.viewOrder(parseInt(viewOrderId));
      setOrderData(res.data);
      setResponse({ success: true, data: res.data });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleGetUserOrders = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      const res = await orderAPI.getUserOrders(parseInt(userOrdersId));
      setOrderData(res.data);
      setResponse({ success: true, data: res.data });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  // FIXED: Checkout with offer logic
  const handleCheckoutWithOffer = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setCheckoutSummary(null);

    try {
      const userId = parseInt(checkoutForm.userId);
      
      // Step 1: Get cart
      const cartRes = await cartAPI.viewCart(userId);
      
      // Calculate cart total from order items
      let cartTotal = 0;
      let cartItems = [];
      
      if (cartRes.data) {
        // Check if response has orderItems (from order endpoint)
        if (cartRes.data.orderItems && Array.isArray(cartRes.data.orderItems)) {
          cartRes.data.orderItems.forEach(item => {
            cartTotal += item.totalPrice;
          });
          cartItems = cartRes.data.orderItems;
        }
        // Or if it has cartItems
        else if (cartRes.data.cartItems && Array.isArray(cartRes.data.cartItems)) {
          cartRes.data.cartItems.forEach(item => {
            const itemTotal = item.product.productPrice * item.quantity;
            cartTotal += itemTotal;
          });
          cartItems = cartRes.data.cartItems;
        }
      }

      let finalAmount = cartTotal;
      let offerApplied = null;
      let savings = 0;

      // Step 2: Apply offer if provided
      if (checkoutForm.offerCode) {
        try {
          // Validate offer
          const validateRes = await offerAPI.validateOffer(checkoutForm.offerCode);
          
          if (!validateRes.data) {
            throw new Error('Invalid offer code');
          }

          // Apply offer
          const applyRes = await offerAPI.applyOffer(checkoutForm.offerCode, cartTotal);
          finalAmount = applyRes.data;
          savings = cartTotal - finalAmount;
          offerApplied = checkoutForm.offerCode;
        } catch (offerErr) {
          throw new Error('Offer Error: ' + offerErr.message);
        }
      }

      // Step 3: Create order
      const orderRes = await orderAPI.placeOrder(userId);
      
      // Step 4: Check if order was created and update UI accordingly
      let actualDiscount = 0;
      let actualFinalAmount = cartTotal;
      
      if (orderRes.data) {
        // If backend has totalAmount, use it
        if (orderRes.data.totalAmount) {
          cartTotal = orderRes.data.totalAmount;
        }
        
        // If we have discount from our calculation, show it
        if (offerApplied && savings > 0) {
          actualDiscount = savings;
          actualFinalAmount = finalAmount;
        } else {
          actualFinalAmount = orderRes.data.payableAmount || cartTotal;
        }
      }

      // Create checkout summary with CORRECT values
      setCheckoutSummary({
        userId: userId,
        cartTotal: cartTotal,
        offerCode: offerApplied,
        savings: actualDiscount,
        finalAmount: actualFinalAmount,
        orderDetails: orderRes.data,
        cartItems: cartItems
      });

      setOrderData(orderRes.data);
      setResponse({ 
        success: true, 
        data: {
          message: 'Order placed successfully!',
          checkoutSummary: {
            userId: userId,
            cartTotal: cartTotal,
            offerCode: offerApplied,
            savings: actualDiscount,
            finalAmount: actualFinalAmount
          },
          orderDetails: orderRes.data
        }
      });

      setCheckoutForm({ userId: '', offerCode: '' });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="management-page">
      <h1>📋 Order Management</h1>

      <div className="forms-container">
        <div className="form-section">
          <h2>Place Order</h2>
          <form onSubmit={handlePlaceOrder}>
            <input
              type="number"
              placeholder="User ID"
              value={placeOrderUserId}
              onChange={(e) => setPlaceOrderUserId(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>Place Order</button>
          </form>
        </div>

        <div className="form-section">
          <h2>View Order</h2>
          <form onSubmit={handleViewOrder}>
            <input
              type="number"
              placeholder="Order ID"
              value={viewOrderId}
              onChange={(e) => setViewOrderId(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>View Order</button>
          </form>
        </div>

        <div className="form-section">
          <h2>Get User Orders</h2>
          <form onSubmit={handleGetUserOrders}>
            <input
              type="number"
              placeholder="User ID"
              value={userOrdersId}
              onChange={(e) => setUserOrdersId(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>Get Orders</button>
          </form>
        </div>
      </div>

      {/* NEW: Checkout with Offer */}
      <div className="form-section checkout-section">
        <h2>🛍️ Checkout with Offer</h2>
        <form onSubmit={handleCheckoutWithOffer}>
          <input
            type="number"
            placeholder="User ID"
            value={checkoutForm.userId}
            onChange={(e) => setCheckoutForm({...checkoutForm, userId: e.target.value})}
            required
          />
          <input
            type="text"
            placeholder="Offer Code (Optional)"
            value={checkoutForm.offerCode}
            onChange={(e) => setCheckoutForm({...checkoutForm, offerCode: e.target.value})}
          />
          <button type="submit" disabled={loading}>Checkout & Place Order</button>
        </form>
      </div>

      {/* Display Checkout Summary - FIXED */}
      {checkoutSummary && (
        <div className="checkout-summary">
          <h3>✅ Order Confirmation</h3>
          <div className="summary-item">
            <span>User ID:</span>
            <span>#{checkoutSummary.userId}</span>
          </div>
          <div className="summary-item">
            <span>Order ID:</span>
            <span>#{checkoutSummary.orderDetails?.orderId || 'Processing...'}</span>
          </div>
          
          {/* Cart Items */}
          {checkoutSummary.cartItems && checkoutSummary.cartItems.length > 0 && (
            <div className="items-list">
              <h4>📦 Items in Order:</h4>
              {checkoutSummary.cartItems.map((item, idx) => (
                <div key={idx} className="item-row">
                  <span>{item.productName || item.product?.productName} × {item.quantity || 1}</span>
                  <span>₹{(item.totalPrice || item.priceAtOrder).toFixed(2)}</span>
                </div>
              ))}
            </div>
          )}
          
          <hr />
          
          <div className="summary-item">
            <span><strong>Subtotal:</strong></span>
            <span><strong>₹{checkoutSummary.cartTotal.toFixed(2)}</strong></span>
          </div>
          
          {checkoutSummary.offerCode && checkoutSummary.savings > 0 && (
            <>
              <div className="summary-item discount">
                <span>Offer Code ({checkoutSummary.offerCode}):</span>
                <span>-₹{checkoutSummary.savings.toFixed(2)}</span>
              </div>
              <div className="summary-item final">
                <span><strong>Final Amount:</strong></span>
                <span><strong>₹{checkoutSummary.finalAmount.toFixed(2)}</strong></span>
              </div>
            </>
          )}
          
          {(!checkoutSummary.offerCode || checkoutSummary.savings === 0) && (
            <div className="summary-item final">
              <span><strong>Total Amount:</strong></span>
              <span><strong>₹{checkoutSummary.cartTotal.toFixed(2)}</strong></span>
            </div>
          )}
          
          <div className="summary-item status">
            <span>Status:</span>
            <span>{checkoutSummary.orderDetails?.status || 'PENDING'}</span>
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

      {orderData && (
        <div className="data-section">
          <h2>Order Details</h2>
          <pre>{JSON.stringify(orderData, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default OrderManagement;
