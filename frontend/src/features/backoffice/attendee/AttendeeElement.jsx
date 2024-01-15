import { useBackofficeContext } from "../BackofficeContext";

function AttendeeElement({ attendee, handleSetCurrentAttendee }) {
  const { id, name, surname, email, affiliation } = attendee;
  const { removeAttendee } = useBackofficeContext();

  return (
    <tr
      className="cursor-pointer border-t-2 hover:bg-fmcsWhite"
      onClick={() => handleSetCurrentAttendee(attendee)}
    >
      <td className="p-4">{name}</td>
      <td className="p-4">{surname}</td>
      <td className="p-4">{email}</td>
      <td className="p-4">{affiliation}</td>
      <td
        onClick={(e) => {
          e.stopPropagation();
          removeAttendee(id);
        }}
        className="px-3 py-4 text-center transition-all duration-200 hover:bg-fmcsRed hover:text-fmcsWhite"
      >
        <p>remove</p>
      </td>
    </tr>
  );
}

export default AttendeeElement;
