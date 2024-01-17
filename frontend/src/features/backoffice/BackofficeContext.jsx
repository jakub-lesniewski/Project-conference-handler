import { createContext, useContext, useState } from "react";

const BackofficeContext = createContext();

export function useBackofficeContext() {
  return useContext(BackofficeContext);
}

export function BackofficeProvider({ children }) {
  const [attendeesArr, setAttendeesArr] = useState([
    {
      id: "123",
      name: "John",
      surname: "Dough",
      email: "johndoe@example.com",
      affiliation: "University of Foo",
    },
  ]);
  const [timelineArr, setTimelineArr] = useState([
    {
      // event example
      id: "456",
      name: "Coffee Break",
      dateStart: "2001-01-02T01:45",
      dateEnd: "2001-01-02T01:55",
    },
    {
      // session example
      id: "789",
      name: "De revolutionibus orbium coelestium",
      room: "C201",
      building: "Faculty of Mathematic and Computer Science UŁ",
      street: "Stefana Banacha 22",
      attendeeLimit: "50",
      city: "Łódź",
      dateStart: "2001-01-02T01:45",
      dateEnd: "2001-01-02T09:10",
      sessionEventsArr: [
        {
          id: "321",
          type: "lecture",
          abstract: "abstract.pdf",
          name: "Kapłony i Szczerzuje",
          dateStart: "2001-01-02T01:45",
          dateEnd: "2001-01-02T01:55",
          lecturers: "kowal@example.com",
        },
        {
          id: "654",
          type: "event",
          name: "Question break",
          dateStart: "2001-01-02T01:45",
          dateEnd: "2001-01-02T01:55",
          leadEmail: "kowal@example.com",
        },
      ],
    },
  ]);

  function addAttendee(newAttendee) {
    setAttendeesArr([...attendeesArr, newAttendee]);
  }

  function removeAttendee(id) {
    setAttendeesArr((prev) => prev.filter((attendee) => attendee.id !== id));
  }

  function modifyAttendee(id, updatedInfo) {
    setAttendeesArr((prev) =>
      prev.map((attendee) =>
        attendee.id === id ? { ...attendee, ...updatedInfo } : attendee,
      ),
    );
  }

  function addTimelineElement(newEvent) {
    setTimelineArr([...timelineArr, newEvent]);
  }

  function removeTimelineElement(id) {
    setTimelineArr((prev) => prev.filter((session) => session.id !== id));
  }

  function modifyTimelineElement(id, updatedInfo) {
    setTimelineArr((prev) =>
      prev.map((element) =>
        element.id === id ? { ...element, ...updatedInfo } : element,
      ),
    );
  }

  return (
    <BackofficeContext.Provider
      value={{
        attendeesArr,
        timelineArr,
        addAttendee,
        addTimelineElement,
        removeAttendee,
        removeTimelineElement,
        modifyAttendee,
        modifyTimelineElement,
      }}
    >
      {children}
    </BackofficeContext.Provider>
  );
}
