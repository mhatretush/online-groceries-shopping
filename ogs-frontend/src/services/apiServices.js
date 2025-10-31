import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const userAPI = {
  register: (data) => apiClient.post('/api/user/register', data),
  login: (data) => apiClient.post('/api/user/login', data),
  getAllUsers: () => apiClient.get('/api/user'),
  getUserById: (userId) => apiClient.get(`/api/user/${userId}`),
};

export const productAPI = {
  addProduct: (data) => apiClient.post('/product', data),
  updateProduct: (productId, data) => apiClient.put(`/product/${productId}`, data),
  deleteProduct: (productId) => apiClient.delete(`/product/${productId}`),
  getProductById: (productId) => apiClient.get(`/product/${productId}`),
  getAllProducts: () => apiClient.get('/product'),
};

export const cartAPI = {
  addToCart: (data) => apiClient.post('/cart/add', data),
  removeFromCart: (userId, productId) => 
    apiClient.delete(`/cart/remove?userId=${userId}&productId=${productId}`),
  viewCart: (userId) => apiClient.get(`/cart/view?userId=${userId}`),
};

export const orderAPI = {
  placeOrder: (userId) => apiClient.post(`/order/place/${userId}`),
  viewOrder: (orderId) => apiClient.get(`/order/view/${orderId}?orderId=${orderId}`),
  getUserOrders: (userId) => apiClient.get(`/order/users/userId?userId=${userId}`),
};

export const offerAPI = {
  createOffer: (data) => apiClient.post('/api/offer', data),
  getOfferById: (offerId) => apiClient.get(`/api/offer/${offerId}`),
  updateOffer: (offerId, data) => apiClient.put(`/api/offer/update/${offerId}`, data),
  deleteOffer: (offerId) => apiClient.delete(`/api/offer/${offerId}`),
  validateOffer: (code) => apiClient.get(`/api/offer/validate/${code}`),
  applyOffer: (code, amount) => 
    apiClient.get(`/api/offer/apply?code=${code}&amount=${amount}`),
};

export default apiClient;
