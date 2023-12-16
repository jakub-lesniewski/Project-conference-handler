import { useState } from "react";

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

  return (
    <section>
      <div className="w-full overflow-auto border-2 shadow-md sm:rounded-lg">
        <table className="w-full text-left rtl:text-right">
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
            {sessionsArr.map((item) => (
              <tr
                key={item.id}
                className="hover:bg-fmcsGray cursor-pointer border-b text-sm transition-all duration-200"
              >
                <td className="p-3 text-center">{item.id}</td>
                <td className="text-center">{item.name}</td>
                <td className="text-center">{item.room_number}</td>
                <td className="text-center">{item.time_start}</td>
                <td className="text-center">{item.time_end}</td>
                <td className="text-center">{item.city}</td>
                <td className="text-center">{item.street}</td>
                <td className="text-center">{item.building}</td>
                <td className="relative w-24 text-center">
                  <button
                    onClick={() => removeSession(item.id)}
                    className="absolute inset-0 h-full w-24 overflow-hidden px-4 py-2 transition-all duration-200 hover:bg-fmcsRed hover:text-fmcsWhite"
                  >
                    remove
                  </button>
                </td>
              </tr>
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
    </section>
  );
}

export default SessionBox;
