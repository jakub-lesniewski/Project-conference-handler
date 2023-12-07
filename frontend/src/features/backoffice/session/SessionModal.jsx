import { useState } from "react";
import { useForm } from "react-hook-form";
import Button from "../../../ui/Button";
import EventForm from "./EventForm";

function SessionModal({ sessionDetails, changeSessionDetails }) {
  const [currentSessionDetails, setCurrentSessionDetails] =
    useState(sessionDetails);
  const [eventList, setEventList] = useState([]);

  const { attendees, maxAttendees, id, name, room } = currentSessionDetails;

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();

  // function onSubmit(data) {
  //   console.log(data);
  // }

  function addEvent() {
    const newEvent = { id: eventList.length + 1 };
    setEventList((prevList) => [...prevList, newEvent]);
  }

  return (
    <section className="flex flex-col gap-5">
      <form className="flex flex-col gap-5">
        <input
          {...register("sessionAddress")}
          defaultValue="Banacha"
          placeholder="Session adress"
          className="rounded-md border-2 p-1 pl-3 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        />
        <input
          {...register("sessionRoom")}
          placeholder="Session room"
          defaultValue={room}
          className="rounded-md border-2 p-1 pl-3 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        />
        <input
          {...register("attendeeLimit")}
          defaultValue={maxAttendees}
          type="number"
          min="5"
          max="500"
          step="5"
          placeholder="Atendee limit"
          className="rounded-md border-2 p-1 pl-3 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        />
      </form>

      <ul className="flex h-60 flex-col gap-5 overflow-auto">
        {eventList.map((event) => (
          <EventForm key={event.id} id={event.id} />
        ))}
      </ul>
      <Button onClick={addEvent}>Add event</Button>
    </section>
  );
}

export default SessionModal;
