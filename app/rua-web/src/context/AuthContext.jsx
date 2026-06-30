import { createContext, useState, useCallback, useMemo } from "react";
import { getStoredUser, storeUser, clearUser } from "../services/authService.js";

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => getStoredUser());

  const login = useCallback((userData) => {
    storeUser(userData);
    setUser(userData);
  }, []);

  const logout = useCallback(() => {
    clearUser();
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({ user, login, logout, isAuthenticated: user !== null }),
    [user, login, logout]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
