import { useState } from "react";
import ModalWindow from "../../../ui/ModalWindow";
import SessionBuilder from "./SessionBuilder";

function SessionElement({ session }) {
  const [isModal, setIsModal] = useState(false);

  function handleToggleModal() {
    setIsModal(!isModal);
  }

  const { id, name, room, attendees, maxAttendees } = session;

  return (
    <div className="relative">
      <li
        onClick={handleToggleModal}
        className="tacking-wider hover:transf flex cursor-pointer border-b text-lg font-semibold"
      >
        <span className="border-r p-4">Id: {id}</span>
        <span className="border-r p-4">{name}</span>
        <span className="border-r p-4">Room: {room}</span>
        <span className="border-r p-4">
          Attendees:{" "}
          <span className={attendees > maxAttendees ? "text-fmcsRed" : ""}>
            {attendees}
          </span>
          /{maxAttendees}
        </span>
      </li>
      {isModal && (
        <ModalWindow onClose={handleToggleModal}>
          <SessionBuilder />
        </ModalWindow>
      )}
    </div>
  );
}

export default SessionElement;
