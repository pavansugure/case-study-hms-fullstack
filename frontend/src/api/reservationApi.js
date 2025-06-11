import axios from 'axios';
const BASE_URL = 'http://localhost:8082/reservations';

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const addReservation = (data) => axios.post(BASE_URL, data, getAuthHeaders());
export const getReservationByCode = (code) => axios.get(`${BASE_URL}/${code}`, getAuthHeaders());
export const searchReservations = (start, end) => 
  axios.get(`${BASE_URL}/search`, {
    params: { startDate: start, endDate: end },
    ...getAuthHeaders(),
  });

export const getAllReservations = () => axios.get(BASE_URL, getAuthHeaders());
export const deleteReservation = (code) => axios.delete(`${BASE_URL}/${code}`, getAuthHeaders());
