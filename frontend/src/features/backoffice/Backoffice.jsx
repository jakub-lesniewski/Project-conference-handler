import AttendeesBox from "./attendee/AttendeesBox";
import { useBackofficeContext } from "./BackofficeContext";

function Backoffice() {
  return (
    <main>
      <AttendeesBox />
    </main>
  );
}

export default Backoffice;
