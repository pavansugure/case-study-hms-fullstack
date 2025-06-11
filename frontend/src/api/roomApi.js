import axios from "axios";

const BASE_URL = "http://localhost:8083/rooms";

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const addRoom = async (room) => await axios.post(BASE_URL, room, getAuthHeaders());
export const updateRoom = async (room) => await axios.put(BASE_URL, room, getAuthHeaders());
export const deleteRoom = async (id) => await axios.delete(`${BASE_URL}/${id}`, getAuthHeaders());
export const getRoomById = async (id) => await axios.get(`${BASE_URL}/${id}`, getAuthHeaders());
export const getAllAvailableRooms = async () => await axios.get(`${BASE_URL}/available-rooms`, getAuthHeaders());
export const checkRoomAvailability = async (roomId, checkInDate, checkOutDate) => {
  const token = localStorage.getItem("token");
  return await axios.get(`${BASE_URL}/available`, {
    params: { roomId, checkInDate, checkOutDate },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const getAllRooms = () => axios.get(BASE_URL, getAuthHeaders());