import React, { useState } from 'react';
import axios from 'axios';

function ApiTester() {
  const [method, setMethod] = useState('GET');
  const [url, setUrl] = useState('http://localhost:8080/');
  const [requestBody, setRequestBody] = useState('');
  const [response, setResponse] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setResponse(null);

    try {
      const config = {
        method: method,
        url: url,
        headers: {
          'Content-Type': 'application/json',
        },
      };

      if (['POST', 'PUT', 'PATCH'].includes(method) && requestBody) {
        config.data = JSON.parse(requestBody);
      }

      const startTime = Date.now();
      const res = await axios(config);
      const endTime = Date.now();

      setResponse({
        status: res.status,
        statusText: res.statusText,
        headers: res.headers,
        data: res.data,
        time: endTime - startTime
      });
    } catch (err) {
      setError({
        message: err.message,
        response: err.response?.data,
        status: err.response?.status
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="management-page api-tester">
      <h1>🧪 API Tester</h1>
      <p>Test any API endpoint with custom requests</p>

      <form onSubmit={handleSubmit} className="api-tester-form">
        <div className="method-url-group">
          <select 
            value={method} 
            onChange={(e) => setMethod(e.target.value)}
            className="method-select"
          >
            <option value="GET">GET</option>
            <option value="POST">POST</option>
            <option value="PUT">PUT</option>
            <option value="DELETE">DELETE</option>
            <option value="PATCH">PATCH</option>
          </select>
          
          <input
            type="text"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            placeholder="Enter API endpoint URL"
            className="url-input"
            required
          />
        </div>

        {['POST', 'PUT', 'PATCH'].includes(method) && (
          <div className="request-body-section">
            <label>Request Body (JSON):</label>
            <textarea
              value={requestBody}
              onChange={(e) => setRequestBody(e.target.value)}
              placeholder='{"key": "value"}'
              rows="10"
              className="request-body-textarea"
            />
          </div>
        )}

        <button type="submit" disabled={loading} className="send-button">
          {loading ? 'Sending...' : 'Send Request'}
        </button>
      </form>

      {error && (
        <div className="error-message">
          <h3>❌ Error</h3>
          <p><strong>Message:</strong> {error.message}</p>
          {error.status && <p><strong>Status:</strong> {error.status}</p>}
          {error.response && (
            <>
              <h4>Response:</h4>
              <pre>{JSON.stringify(error.response, null, 2)}</pre>
            </>
          )}
        </div>
      )}

      {response && (
        <div className="response-section success">
          <h3>✅ Response</h3>
          <div className="response-meta">
            <span className="status-badge">{response.status} {response.statusText}</span>
            <span className="time-badge">⏱️ {response.time}ms</span>
          </div>
          
          <h4>Response Data:</h4>
          <pre>{JSON.stringify(response.data, null, 2)}</pre>
          
          <details>
            <summary>Response Headers</summary>
            <pre>{JSON.stringify(response.headers, null, 2)}</pre>
          </details>
        </div>
      )}
    </div>
  );
}

export default ApiTester;
