import { createContext, useContext, useState } from "react";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext(); //other components use to consume authentication state and methods.

export const AuthProvider = ({ children }) => {    //provides auth state
  const [token, setToken] = useState(localStorage.getItem("token"));

  let decodedUser = null;
  if (token) {
    try {
      decodedUser = jwtDecode(token);
    } catch (err) {
      console.error("Invalid token in localStorage:", err.message);
      localStorage.removeItem("token"); 
    }
  }

  const [user, setUser] = useState(decodedUser);

  const login = (jwt) => {
    localStorage.setItem("token", jwt);
    setToken(jwt);
    setUser(jwtDecode(jwt));
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
// Makes the token, user, and login/logout functions available to  
// component that calls useAuth().

export const useAuth = () => useContext(AuthContext);