import jwtDecode from 'jwt-decode';

export const getUserRoles = () => {
  const token = localStorage.getItem('jwt'); // or from context
  if (!token) return [];
  try {
    const decoded = jwtDecode(token);
    return decoded.roles || decoded.authorities || [];
  } catch (err) {
    console.error('Invalid token:', err);
    return [];
  }
};
