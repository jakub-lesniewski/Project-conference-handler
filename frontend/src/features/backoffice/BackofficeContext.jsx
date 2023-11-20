import { createContext, useContext } from "react";
import useLocalStorage from "../../utils/useLocalStorage";

const BackofficeContext = createContext();

export function BackofficeProvider({ children }) {
  const [conferenceName, setConferenceName] = useLocalStorage(
    "conferenceName",
    "",
  );

  const value = {
    conferenceName,
    setConferenceName,
  };

  return (
    <BackofficeContext.Provider value={value}>
      {children}
    </BackofficeContext.Provider>
  );
}

export function useBackoffice() {
  return useContext(BackofficeContext);
}
