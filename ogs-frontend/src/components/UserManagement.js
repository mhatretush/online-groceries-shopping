import React, { useState, useEffect } from 'react';
import { userAPI } from '../services/apiService';

function UserManagement() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [registerForm, setRegisterForm] = useState({
    name: '',
    email: '',
    password: ''
  });
  const [loginForm, setLoginForm] = useState({
    email: '',
    password: ''
  });
  const [response, setResponse] = useState(null);

  useEffect(() => {
    fetchAllUsers();
  }, []);

  const fetchAllUsers = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await userAPI.getAllUsers();
      setUsers(res.data);
      setResponse({ success: true, data: res.data });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const res = await userAPI.register(registerForm);
      setResponse({ success: true, data: res.data });
      setRegisterForm({ name: '', email: '', password: '' });
      fetchAllUsers();
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const res = await userAPI.login(loginForm);
      setResponse({ success: true, data: res.data });
      setLoginForm({ email: '', password: '' });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
      setResponse({ success: false, error: err.response?.data || err.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="management-page">
      <h1>👥 User Management</h1>

      <div className="forms-container">
        <div className="form-section">
          <h2>Register New User</h2>
          <form onSubmit={handleRegister}>
            <input
              type="text"
              placeholder="Name"
              value={registerForm.name}
              onChange={(e) => setRegisterForm({...registerForm, name: e.target.value})}
              required
            />
            <input
              type="email"
              placeholder="Email"
              value={registerForm.email}
              onChange={(e) => setRegisterForm({...registerForm, email: e.target.value})}
              required
            />
            <input
              type="password"
              placeholder="Password"
              value={registerForm.password}
              onChange={(e) => setRegisterForm({...registerForm, password: e.target.value})}
              required
            />
            <button type="submit" disabled={loading}>Register</button>
          </form>
        </div>

        <div className="form-section">
          <h2>User Login</h2>
          <form onSubmit={handleLogin}>
            <input
              type="email"
              placeholder="Email"
              value={loginForm.email}
              onChange={(e) => setLoginForm({...loginForm, email: e.target.value})}
              required
            />
            <input
              type="password"
              placeholder="Password"
              value={loginForm.password}
              onChange={(e) => setLoginForm({...loginForm, password: e.target.value})}
              required
            />
            <button type="submit" disabled={loading}>Login</button>
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

      <div className="data-section">
        <h2>All Users</h2>
        <button onClick={fetchAllUsers} disabled={loading}>
          {loading ? 'Loading...' : 'Refresh Users'}
        </button>
        
        {users.length > 0 ? (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.userId || user.id}>
                  <td>{user.userId || user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No users found</p>
        )}
      </div>
    </div>
  );
}

export default UserManagement;
