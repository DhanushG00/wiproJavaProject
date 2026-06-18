import api from "./api";

export const getRevenueAnalytics = () =>
  api.get("/admin/revenue-analytics");

export const getTopPizzas = () =>
  api.get("/admin/top-pizzas");

export const getDeliveryPerformance = () =>
  api.get("/admin/delivery-performance");

export const getCustomerTrends = () =>
  api.get("/admin/customer-trends");

export const getOrders = () =>
  api.get("/api/order/all"); 

export const getAllUsers = () =>
  api.get("/admin/getallusers");