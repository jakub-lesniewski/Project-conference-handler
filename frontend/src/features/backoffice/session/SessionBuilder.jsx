import { useState } from "react";
import SquareButton from "../../../ui/SquareButton";
import EventForm from "./EventForm";

function SessionBuilder() {
  const [eventList, setEventList] = useState([]);

  function addEvent() {
    const newEvent = { id: eventList.length + 1 };
    setEventList((prevList) => [...prevList, newEvent]);
  }

  return (
    <ul className="flex flex-col gap-5 p-5">
      {eventList.map((event) => (
        <EventForm key={event.id} id={event.id} />
      ))}
      <SquareButton onClick={addEvent}>Add event</SquareButton>
    </ul>
  );
}

export default SessionBuilder;
