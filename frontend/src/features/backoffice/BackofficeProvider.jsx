import { createContext, useContext } from "react";
import useLocalStorage from "../../utils/useLocalStorage";

const BackofficeContext = createContext();

export function useBackoffice() {
  return useContext(BackofficeContext);
}

export function BackofficeProvider({ children }) {
  const [sessionArr, setSessionArr] = useLocalStorage("sessionArr", {});

  function addSession(sessionId, sessionData) {
    setSessionArr((prevSessionArr) => ({
      ...prevSessionArr,
      [sessionId]: sessionData,
    }));
  }

  function modifySession(sessionId, modifiedData) {
    setSessionArr((prevSessionArr) => ({
      ...prevSessionArr,
      [sessionId]: {
        ...prevSessionArr[sessionId],
        ...modifiedData,
      },
    }));
  }

  function removeSession(sessionId) {
    setSessionArr((prevSessionArr) => {
      const newSessionArr = { ...prevSessionArr };
      delete newSessionArr[sessionId];
      return newSessionArr;
    });
  }

  function removeSessionArr() {
    setSessionArr({});
  }

  function getSessionArr() {
    return sessionArr;
  }

  function getSession(sessionId) {
    return sessionArr[sessionId];
  }

  return (
    <BackofficeContext.Provider
      value={{
        sessionArr,
        addSession,
        modifySession,
        removeSession,
        removeSessionArr,
        getSessionArr,
        getSession,
      }}
    >
      {children}
    </BackofficeContext.Provider>
  );
}
