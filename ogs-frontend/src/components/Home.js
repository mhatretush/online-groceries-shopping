import React from 'react';

function Home() {
  return (
    <div className="home">
      <h1>Welcome to Online Grocery Shopping API Showcase</h1>
      
      <div className="api-overview">
        <h2>Available APIs</h2>
        
        <div className="api-section">
          <h3>👥 User Management</h3>
          <ul>
            <li><strong>POST</strong> /api/user/register - Register new user</li>
            <li><strong>POST</strong> /api/user/login - User login</li>
            <li><strong>GET</strong> /api/user - Get all users</li>
            <li><strong>GET</strong> /api/user/{'{userId}'} - Get user by ID</li>
          </ul>
        </div>

        <div className="api-section">
          <h3>📦 Product Management</h3>
          <ul>
            <li><strong>POST</strong> /product - Add new product</li>
            <li><strong>PUT</strong> /product/{'{productId}'} - Update product</li>
            <li><strong>DELETE</strong> /product/{'{productId}'} - Delete product</li>
            <li><strong>GET</strong> /product/{'{productId}'} - Get product by ID</li>
            <li><strong>GET</strong> /product - Get all products</li>
          </ul>
        </div>

        <div className="api-section">
          <h3>🛒 Cart Management</h3>
          <ul>
            <li><strong>POST</strong> /cart/add - Add item to cart</li>
            <li><strong>DELETE</strong> /cart/remove - Remove item from cart</li>
            <li><strong>GET</strong> /cart/view - View cart</li>
          </ul>
        </div>

        <div className="api-section">
          <h3>📋 Order Management</h3>
          <ul>
            <li><strong>POST</strong> /order/place/{'{userId}'} - Place order</li>
            <li><strong>GET</strong> /order/view/{'{orderId}'} - View order</li>
            <li><strong>GET</strong> /order/users/userId - Get user orders</li>
          </ul>
        </div>

        <div className="api-section">
          <h3>🎁 Offer Management</h3>
          <ul>
            <li><strong>POST</strong> /api/offer - Create offer</li>
            <li><strong>GET</strong> /api/offer/{'{id}'} - Get offer by ID</li>
            <li><strong>PUT</strong> /api/offer/update/{'{offerId}'} - Update offer</li>
            <li><strong>DELETE</strong> /api/offer/{'{offerId}'} - Delete offer</li>
            <li><strong>GET</strong> /api/offer/validate/{'{code}'} - Validate offer code</li>
            <li><strong>GET</strong> /api/offer/apply - Apply offer to amount</li>
          </ul>
        </div>

        <div className="api-section">
          <h3>📊 Monitoring</h3>
          <ul>
            <li><strong>GET</strong> /actuator/prometheus - Prometheus metrics</li>
            <li>Grafana Dashboard - Integrated visualization</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Home;
