import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: [],

  reducers: {

    addToCart: (state, action) => {
      const existing = state.find(item => item.id === action.payload.id);

      if (existing) {
        existing.quantity += 1; 
      } else {
        state.push({ ...action.payload, quantity: 1 });
      }
    },

    increaseQty: (state, action) => {
      const item = state.find(i => i.id === action.payload);
      if (item) item.quantity++;
    },

    decreaseQty: (state, action) => {
      const item = state.find(i => i.id === action.payload);
      if (item && item.quantity > 1) {
        item.quantity--;
      }
    },

    removeItem: (state, action) => {
      return state.filter(i => i.id !== action.payload);
    },

    clearCart: () => []
  }
});

export const {
  addToCart,
  increaseQty,
  decreaseQty,
  removeItem,
  clearCart
} = cartSlice.actions;

export default cartSlice.reducer;