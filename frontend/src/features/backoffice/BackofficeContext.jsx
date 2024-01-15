import { createContext, useContext, useState } from "react";

const BackofficeContext = createContext();

export function useBackofficeContext() {
  return useContext(BackofficeContext);
}

export function BackofficeProvider({ children }) {
  const [attendeesArr, setAttendeesArr] = useState([
    {
      name: "John",
      surname: "Dough",
      email: "johndoe@example.com",
      affiliation: "University of Foo",
    },
  ]);
  const [timelineArr, setTimelineArr] = useState([]);

  function addAttendee(newAttendee) {
    setAttendeesArr([...attendeesArr, newAttendee]);
  }

  function addSession(newSession) {
    setTimelineArr([...timelineArr, newSession]);
  }

  function addEvent(newEvent) {
    setTimelineArr([...timelineArr, newEvent]);
  }

  function removeAttendee(id) {
    setAttendeesArr((prev) => prev.filter((attendee) => attendee.id !== id));
  }

  function removeSession(id) {
    setTimelineArr((prev) => prev.filter((session) => session.id !== id));
  }

  function removeEvent(id) {
    setTimelineArr((prev) => prev.filter((event) => event.id !== id));
  }

  function modifyAttendee(id, updatedInfo) {
    setAttendeesArr((prev) =>
      prev.map((attendee) =>
        attendee.id === id ? { ...attendee, ...updatedInfo } : attendee,
      ),
    );
  }

  return (
    <BackofficeContext.Provider
      value={{
        attendeesArr,
        timelineArr,
        addAttendee,
        addSession,
        addEvent,
        removeAttendee,
        removeSession,
        removeEvent,
        modifyAttendee,
      }}
    >
      {children}
    </BackofficeContext.Provider>
  );
}
