import SquareButton from "./SquareButton";

function SessionBox() {
  return (
    <section className="col-span-full flex flex-col border border-t-0">
      <p className="tacking-wider border-b p-3 text-lg font-semibold">
        Some session name
      </p>
      <p className="tacking-wider border-b p-3 text-lg font-semibold">
        Some session name
      </p>
      <p className="tacking-wider border-b p-3 text-lg font-semibold">
        Some session name
      </p>

      <SquareButton>Add session</SquareButton>
    </section>
  );
}

export default SessionBox;
