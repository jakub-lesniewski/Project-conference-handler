import { useState } from "react";
import SquareButton from "../../../ui/SquareButton";
import SessionElement from "./SessionElement";

function SessionBox() {
  const [conferenceList, setConferenceList] = useState([]);

  function addSession() {
    console.log("session added");

    const newSession = {
      id: conferenceList.length + 1,
      name: `Session ${conferenceList.length + 1}`,
      room: `C${conferenceList.length + 1}`,
      attendees: 0,
      maxAttendees: 20,
    };

    setConferenceList((prevList) => [...prevList, newSession]);
  }

  return (
    <ul className="col-span-full flex flex-col border border-t-0">
      {conferenceList.map((session) => (
        <SessionElement key={session.id} session={session} />
      ))}

      <SquareButton onClick={addSession}>Add session</SquareButton>
    </ul>
  );
}

export default SessionBox;
