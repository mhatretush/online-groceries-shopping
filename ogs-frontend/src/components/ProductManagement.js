import React, { useState, useEffect } from 'react';
import { productAPI } from '../services/apiService';

function ProductManagement() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [productForm, setProductForm] = useState({
    productName: '',
    productPrice: '',
    productQty: ''
  });
  const [editingId, setEditingId] = useState(null);
  const [response, setResponse] = useState(null);

  useEffect(() => {
    fetchAllProducts();
  }, []);

  const fetchAllProducts = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await productAPI.getAllProducts();
      setProducts(res.data.data || res.data);
      setResponse({ success: true, data: res.data });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    const productData = {
      productName: productForm.productName,
      productPrice: parseFloat(productForm.productPrice),
      productQty: parseInt(productForm.productQty)
    };

    try {
      let res;
      if (editingId) {
        res = await productAPI.updateProduct(editingId, productData);
        setEditingId(null);
      } else {
        res = await productAPI.addProduct(productData);
      }
      setResponse({ success: true, data: res.data });
      setProductForm({ productName: '', productPrice: '', productQty: '' });
      fetchAllProducts();
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (product) => {
    setProductForm({
      productName: product.productName,
      productPrice: product.productPrice.toString(),
      productQty: product.productQty.toString()
    });
    setEditingId(product.productId);
  };

  const handleDelete = async (productId) => {
    if (!window.confirm('Are you sure you want to delete this product?')) return;
    
    setLoading(true);
    setError(null);
    try {
      const res = await productAPI.deleteProduct(productId);
      setResponse({ success: true, data: res.data });
      fetchAllProducts();
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="management-page">
      <h1>📦 Product Management</h1>

      <div className="form-section">
        <h2>{editingId ? 'Update Product' : 'Add New Product'}</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Product Name"
            value={productForm.productName}
            onChange={(e) => setProductForm({...productForm, productName: e.target.value})}
            required
          />
          <input
            type="number"
            step="0.01"
            placeholder="Product Price"
            value={productForm.productPrice}
            onChange={(e) => setProductForm({...productForm, productPrice: e.target.value})}
            required
          />
          <input
            type="number"
            placeholder="Product Quantity"
            value={productForm.productQty}
            onChange={(e) => setProductForm({...productForm, productQty: e.target.value})}
            required
          />
          <div className="button-group">
            <button type="submit" disabled={loading}>
              {editingId ? 'Update Product' : 'Add Product'}
            </button>
            {editingId && (
              <button 
                type="button" 
                onClick={() => {
                  setEditingId(null);
                  setProductForm({ productName: '', productPrice: '', productQty: '' });
                }}
              >
                Cancel
              </button>
            )}
          </div>
        </form>
      </div>

      {error && <div className="error-message">{error}</div>}
      
      {response && (
        <div className="response-section">
          <h3>API Response:</h3>
          <pre>{JSON.stringify(response, null, 2)}</pre>
        </div>
      )}

      <div className="data-section">
        <h2>All Products</h2>
        <button onClick={fetchAllProducts} disabled={loading}>
          {loading ? 'Loading...' : 'Refresh Products'}
        </button>
        
        {products.length > 0 ? (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {products.map((product) => (
                <tr key={product.productId}>
                  <td>{product.productId}</td>
                  <td>{product.productName}</td>
                  <td>₹{product.productPrice}</td>
                  <td>{product.productQty}</td>
                  <td>
                    <button onClick={() => handleEdit(product)} className="btn-edit">
                      Edit
                    </button>
                    <button 
                      onClick={() => handleDelete(product.productId)} 
                      className="btn-delete"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No products found</p>
        )}
      </div>
    </div>
  );
}

export default ProductManagement;
