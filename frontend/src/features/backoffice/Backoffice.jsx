import AttendeesBox from "./attendee/AttendeesBox";
import TimelineBox from "./timeline/TimelineBox";
import { useBackofficeContext } from "./BackofficeContext";

function Backoffice() {
  const { attendeesArr, timelineArr } = useBackofficeContext();

  console.log(attendeesArr, timelineArr);

  return (
    <main className="grid grid-cols-5 sm:grid-cols-1 lg:grid-cols-5 2xl:mx-20">
      <div className="col-span-2">
        <AttendeesBox />
      </div>
      <div className="col-span-3">
        <TimelineBox />
      </div>
    </main>
  );
}

export default Backoffice;
