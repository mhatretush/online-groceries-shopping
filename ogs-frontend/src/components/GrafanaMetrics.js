import React, { useState } from 'react';

function GrafanaMetrics() {
  const grafanaUrl = process.env.REACT_APP_GRAFANA_URL || 'http://localhost:3000';
  const [customDashboard, setCustomDashboard] = useState('');

  return (
    <div className="management-page">
      <h1>📊 Grafana Metrics & Monitoring</h1>
      
      <div className="grafana-section">
        <h2>Application Metrics</h2>
        <p>Real-time monitoring powered by Prometheus and Grafana</p>
        
        <div className="metrics-info">
          <h3>Available Metrics Endpoints:</h3>
          <ul>
            <li>
              <strong>Prometheus Metrics:</strong> 
              <a href="http://localhost:8080/actuator/prometheus" target="_blank" rel="noopener noreferrer">
                http://localhost:8080/actuator/prometheus
              </a>
            </li>
            <li>
              <strong>Grafana Dashboard:</strong> 
              <a href={grafanaUrl} target="_blank" rel="noopener noreferrer">
                {grafanaUrl}
              </a>
            </li>
          </ul>
        </div>

        <div className="dashboard-embed">
          <h3>Embedded Grafana Dashboard</h3>
          <p className="info-text">
            Configure your Grafana dashboard and paste the dashboard URL below to embed it.
            Make sure to enable "Allow embedding" in Grafana dashboard settings.
          </p>
          
          <div className="form-section">
            <input
              type="text"
              placeholder="Enter Grafana Dashboard URL (e.g., http://localhost:3000/d/dashboard-id)"
              value={customDashboard}
              onChange={(e) => setCustomDashboard(e.target.value)}
              className="dashboard-input"
            />
          </div>

          {customDashboard ? (
            <div className="iframe-container">
              <iframe
                src={customDashboard}
                width="100%"
                height="600px"
                frameBorder="0"
                title="Grafana Dashboard"
              />
            </div>
          ) : (
            <div className="placeholder-dashboard">
              <p>📈 Enter a Grafana dashboard URL above to view metrics</p>
              <div className="setup-guide">
                <h4>Quick Setup Guide:</h4>
                <ol>
                  <li>Open Grafana at <code>{grafanaUrl}</code></li>
                  <li>Create or open a dashboard</li>
                  <li>Click "Share dashboard" → "Link"</li>
                  <li>Copy the dashboard URL and paste it above</li>
                  <li>In dashboard settings, enable "Allow embedding"</li>
                </ol>
              </div>
            </div>
          )}
        </div>

        <div className="metrics-cards">
          <div className="metric-card">
            <h4>🚀 JVM Metrics</h4>
            <p>Memory usage, GC activity, thread count</p>
          </div>
          <div className="metric-card">
            <h4>🌐 HTTP Metrics</h4>
            <p>Request rates, response times, status codes</p>
          </div>
          <div className="metric-card">
            <h4>💾 Database Metrics</h4>
            <p>Connection pool, query performance</p>
          </div>
          <div className="metric-card">
            <h4>⚡ System Metrics</h4>
            <p>CPU usage, disk I/O, network activity</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default GrafanaMetrics;
