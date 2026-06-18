import api from "./api";

export const getDelivery = (orderId) =>
  api.get(`/delivery/${orderId}`);

export const updateLocation = (data) =>
  api.post("/delivery/update-location", data);

export const markDelivered = (orderId) =>
  api.post(`/delivery/delivered/${orderId}`);