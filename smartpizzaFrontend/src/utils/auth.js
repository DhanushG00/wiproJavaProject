
export const getToken = () => localStorage.getItem("token");


export const getRole = () => localStorage.getItem("roles"); 


export const isAuthenticated = () => !!getToken();

export const getUserFromToken = () => {
  const token = getToken();

  if (!token) {
    return null; }

  try {
    const decoded = JSON.parse(atob(token.split(".")[1]));
    return decoded;
  } catch (e) {
    console.error("Invalid token");
    return null;
  }
};

export const getUserId = () => {
  const user = getUserFromToken();
  return user?.userId || user?.id || null;
};