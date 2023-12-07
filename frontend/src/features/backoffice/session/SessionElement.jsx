import { useState } from "react";
import ModalWindow from "../../../ui/ModalWindow";
import SessionModal from "./SessionModal";

function SessionElement({ session }) {
  const [sessionDetails, setSessionDetails] = useState(session);
  const [isModal, setIsModal] = useState(false);

  function handleToggleModal() {
    setIsModal(!isModal);
  }

  function changeSessionDetails(newSessionDetails) {
    setSessionDetails(newSessionDetails);
  }

  const { id, name, room, attendees, maxAttendees } = sessionDetails;

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
          <SessionModal
            sessionDetails={sessionDetails}
            changeSessionDetails={changeSessionDetails}
          />
        </ModalWindow>
      )}
    </div>
  );
}

export default SessionElement;
