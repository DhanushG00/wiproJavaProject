import api from "./api";


export const getAllDeliveryAgents = () =>
  api.get("/deliveryagents");


export const getDeliveryAgentById = (agentId) =>
  api.get(`/deliveryagents/${agentId}`);

export const deleteDeliveryAgent = (agentId) =>
  api.delete(`/deliveryagents/${agentId}`);

export const assignOrderToAgent = (agentId, orderId) =>
  api.post(`/deliveryagents/${agentId}/assign/${orderId}`);

export const updateDeliveryAgentStatus = (agentId, status) =>
  api.put(`/deliveryagents/${agentId}/status`, { status });