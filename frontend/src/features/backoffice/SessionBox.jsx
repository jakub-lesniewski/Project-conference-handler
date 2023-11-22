import SquareButton from "../../ui/SquareButton";
import SessionElement from "./SessionElement";

const dummySessions = [
  {
    id: 1,
    name: "Session 1",
    room: "C201",
    attendees: 43,
    maxAttendees: 20,
  },
  {
    id: 2,
    name: "Session 2",
    room: "D105",
    attendees: 25,
    maxAttendees: 30,
  },
  {
    id: 3,
    name: "Session 3",
    room: "A301",
    attendees: 18,
    maxAttendees: 25,
  },
];

function SessionBox() {
  return (
    <section className="col-span-full flex flex-col border border-t-0">
      {dummySessions.map((session) => (
        <SessionElement key={session.id} session={session} />
      ))}
      <SquareButton>Add session</SquareButton>
    </section>
  );
}

export default SessionBox;
