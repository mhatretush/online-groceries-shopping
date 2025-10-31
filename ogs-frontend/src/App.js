import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Navbar from './components/Navbar';
import Home from './components/Home';
import UserManagement from './components/UserManagement';
import ProductManagement from './components/ProductManagement';
import CartManagement from './components/CartManagement';
import OrderManagement from './components/OrderManagement';
import OfferManagement from './components/OfferManagement';
import ApiTester from './components/ApiTester';
import GrafanaMetrics from './components/GrafanaMetrics';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="container">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/users" element={<UserManagement />} />
            <Route path="/products" element={<ProductManagement />} />
            <Route path="/cart" element={<CartManagement />} />
            <Route path="/orders" element={<OrderManagement />} />
            <Route path="/offers" element={<OfferManagement />} />
            <Route path="/api-tester" element={<ApiTester />} />
            <Route path="/metrics" element={<GrafanaMetrics />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
