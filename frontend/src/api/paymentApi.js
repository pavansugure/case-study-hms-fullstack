
import axios from "axios";
const BASE_URL = "http://localhost:8091/pay"; 

export const createOrder = (reservationCode) =>
  axios.post(`${BASE_URL}/createOrder/${reservationCode}`, {});