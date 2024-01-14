import { useBackofficeContext } from "../BackofficeContext";

function AttendeeElement({ attendee, handleSetCurrentAttendee }) {
  const { id, name, surname, email, affiliation } = attendee;
  const { removeAttendee } = useBackofficeContext();

  return (
    <tr
      className="cursor-pointer border border-fmcsBlack transition-all duration-200 hover:bg-fmcsGray"
      onClick={() => handleSetCurrentAttendee(attendee)}
    >
      <td className="min-w-40 px-4 py-2">{name}</td>
      <td className="w-40 px-4 py-2">{surname}</td>
      <td className="w-60 px-4 py-2">{email}</td>
      <td className="w-40 px-4 py-2">{affiliation}</td>
      <td>
        <button
          onClick={() => removeAttendee(id)}
          className="px-4 py-2 transition-all duration-200 hover:bg-fmcsRed hover:text-fmcsWhite"
        >
          remove
        </button>
      </td>
    </tr>
  );
}

export default AttendeeElement;
