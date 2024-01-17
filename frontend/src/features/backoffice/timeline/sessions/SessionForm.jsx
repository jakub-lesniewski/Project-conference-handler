import { useState } from "react";
import { useForm } from "react-hook-form";
import { useBackofficeContext } from "../../BackofficeContext";
import { v4 as uuidv4 } from "uuid";
import InputField from "../../../../ui/InputField";
import Button from "../../../../ui/Button";
import SessionEventArrayElement from "./SessionEventArrayElement";
import ModalWindow from "../../../../ui/ModalWindow";
import SessionEventForm from "../sessions/SessionEventForm";

function SessionForm({ currentTimelineElement, handleToggleSessionModal }) {
  const { register, handleSubmit } = useForm();
  const { addTimelineElement, modifyTimelineElement } = useBackofficeContext();
  const [currentSessionEvent, setCurrentSessionEvent] = useState(null);
  const [toggleModal, setToggleModal] = useState(false);
  const [sessionEventsArr, setSessionEventsArr] = useState(
    currentTimelineElement?.sessionEventsArr || [],
  );

  function addSessionEvent(event) {
    setSessionEventsArr([...sessionEventsArr, event]);
  }

  function removeSessionEventArr(id) {
    setSessionEventsArr((prev) => prev.filter((event) => event.id !== id));
  }

  function modifySessionEvent(id, updatedInfo) {
    setSessionEventsArr((prev) =>
      prev.map((event) =>
        event.id === id ? { ...event, ...updatedInfo } : event,
      ),
    );
  }

  function handleSetCurrentEvent(event) {
    setCurrentSessionEvent(event);
    handleToggleModal();
  }

  function handleToggleModal() {
    if (currentSessionEvent) {
      setCurrentSessionEvent(null);
    }
    setToggleModal(!toggleModal);
  }

  function onSubmit(data) {
    if (currentTimelineElement) {
      modifyTimelineElement(currentTimelineElement.id, data);
    } else {
      const newSession = {
        id: uuidv4(),
        name: data.name,
        room: data.room,
        attendeeLimit: data.attendeeLimit,
        building: data.building,
        street: data.street,
        dateStart: data.dateStart,
        dateEnd: data.dateEnd,
      };
      addTimelineElement(newSession);
    }
    handleToggleSessionModal();
  }

  return (
    <>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="grid gap-5 xl:grid-cols-2"
      >
        <div className="flex flex-col gap-2">
          <InputField
            label="name:"
            name="name"
            defaultValue={currentTimelineElement?.name}
            register={register}
            placeholder="De revolutionibus orbium coelestium"
            autoFocus={true}
            required={true}
          />
          <InputField
            label="room:"
            name="room"
            defaultValue={currentTimelineElement?.room}
            register={register}
            placeholder="C201"
            required={true}
          />
          <InputField
            label="attendee limit:"
            name="attendeeLimit"
            type="number"
            defaultValue={currentTimelineElement?.attendeeLimit}
            register={register}
            required={true}
          />
          <InputField
            label="building:"
            name="building"
            defaultValue={currentTimelineElement?.building}
            register={register}
            placeholder="Faculty of Mathematic and Computer Science UŁ"
            required={true}
          />
          <InputField
            label="street:"
            name="street"
            defaultValue={currentTimelineElement?.street}
            register={register}
            placeholder="Stefena Banacha 22"
            required={true}
          />
          <InputField
            label="start date:"
            name="dateStart"
            type="datetime-local"
            defaultValue={
              currentTimelineElement?.dateStart || "2024-01-16T10:00"
            }
            register={register}
            required={true}
          />
          <InputField
            label="end date:"
            name="dateEnd"
            type="datetime-local"
            defaultValue={currentTimelineElement?.dateEnd || "2024-01-16T12:00"}
            register={register}
            required={true}
          />
          <Button type="submit">submit</Button>
        </div>

        <div className="flex flex-col justify-between">
          <ul className="h-[300px] w-[800px] overflow-y-scroll rounded-lg border-4">
            {sessionEventsArr.map((event) => (
              <SessionEventArrayElement
                key={event.id}
                event={event}
                handleSetCurrentEvent={handleSetCurrentEvent}
                removeSessionEventArr={removeSessionEventArr}
              />
            ))}
          </ul>
          <Button onClick={handleToggleModal}>add event</Button>
        </div>
      </form>
      {toggleModal && (
        <ModalWindow onClose={handleToggleModal}>
          <SessionEventForm
            currentSessionEvent={currentSessionEvent}
            addSessionEvent={addSessionEvent}
            modifySessionEvent={modifySessionEvent}
            handleToggleModal={handleToggleModal}
          />
        </ModalWindow>
      )}
    </>
  );
}

export default SessionForm;
