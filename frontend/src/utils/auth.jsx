import { createContext, useContext } from "react";
import useLocalStorage from "./useLocalStorage";

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [storedUser, setStoredUser] = useLocalStorage("user", null);

  function login(userData) {
    setStoredUser(userData);
  }

  function logout() {
    setStoredUser(null);
  }

  return (
    <AuthContext.Provider value={{ user: storedUser, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
