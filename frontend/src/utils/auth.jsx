import { createContext, useContext } from "react";
import useLocalStorage from "./useLocalStorage";

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [user, setUser] = useLocalStorage("user", null);

  function login(userData) {
    setUser(userData);
  }

  function logout() {
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user: user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
