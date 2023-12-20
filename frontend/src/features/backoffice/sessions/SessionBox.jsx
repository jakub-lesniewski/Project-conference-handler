import { useState } from "react";
import RowSession from "./RowSession";
import ModalWindow from "../../../ui/ModalWindow";
import ModalSession from "./ModalSession";

const tableHeadRow = [
  "id",
  "name",
  "room number",
  "starting date",
  "end date",
  "city",
  "street",
  "building",
];

function SessionBox() {
  const [sessionsArr, setSessionsArr] = useState([]);
  const [sessionId, setSessionId] = useState(1);
  const [showModal, setShowModal] = useState(false);
  const [currentSession, setCurrentSession] = useState(null);

  function addSession() {
    setSessionId((prevSessionId) => prevSessionId + 1);

    const newSession = {
      id: sessionId,
      name: `Session ${sessionsArr.length + 1}`,
      room_number: "Room ***",
      time_start: "01-10-23 TUE 12:04",
      time_end: "02-10-23 WED 12:04",
      city: "Łódź",
      street: "Banacha 4",
      building: "FMCS UŁ",
    };

    setSessionsArr((prevSessionsArr) => [...prevSessionsArr, newSession]);
  }

  function removeSession(id) {
    setSessionsArr((prevSessionsArr) =>
      prevSessionsArr.filter((session) => session.id !== id),
    );
  }

  function toggleModal() {
    setShowModal(!showModal);
  }

  return (
    <section>
      <div className="max-h-[800px] w-full overflow-auto border-2 shadow-md sm:rounded-lg">
        <table className=" w-full text-left rtl:text-right">
          <thead className="border-b text-xs uppercase">
            <tr>
              {tableHeadRow.map((item, index) => (
                <th key={index} className="p-3 text-center">
                  {item}
                </th>
              ))}
              <th className="w-24"></th>
            </tr>
          </thead>

          <tbody>
            {sessionsArr.map((session) => (
              <RowSession
                session={session}
                removeSession={removeSession}
                setCurrentSession={setCurrentSession}
                onClick={toggleModal}
                key={session.id}
              />
            ))}
          </tbody>
        </table>

        <button
          onClick={addSession}
          className="m-4 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite"
        >
          add session
        </button>
      </div>
      {showModal && (
        <ModalWindow onClose={toggleModal}>
          <ModalSession session={currentSession} toggleModal={toggleModal} />
        </ModalWindow>
      )}
    </section>
  );
}

export default SessionBox;
