import axios from 'axios';

const axiosClient = axios.create({
  baseURL: 'http://localhost:8090', 
});

axiosClient.interceptors.request.use((config) => {  // runs before every hhtp request 
    const token = localStorage.getItem("token");
  
    const publicEndpoints = ["/auth/login", "/auth/register"];
    if (!publicEndpoints.includes(config.url) && token) { // for private endpoints
      config.headers.Authorization = `Bearer ${token}`;
    }
  
    return config;
  });
  

export default axiosClient; 