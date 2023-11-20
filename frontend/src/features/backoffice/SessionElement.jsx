import { Link } from "react-router-dom";

function SessionElement({ session }) {
  const { id, name, room, attendees, maxAttendees } = session;
  return (
    <Link className="tacking-wider hover:transf flex border-b text-lg font-semibold">
      <span className="border-r p-4 ">{name}</span>
      <span className="border-r p-4 ">Room: {room}</span>
      <span className="border-r p-4 ">
        Atendees:{" "}
        <span className={attendees > maxAttendees && "text-fmcsRed"}>
          {attendees}
        </span>
        /{maxAttendees}
      </span>
    </Link>
  );
}

export default SessionElement;
