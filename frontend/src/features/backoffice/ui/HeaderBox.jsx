import SquareButton from "./SquareButton";

function HeaderBox() {
  return (
    <header className="flex flex-col justify-between gap-5 border border-t-0">
      <div className="p-10 text-2xl font-bold tracking-wider">
        <h1>Conference panel</h1>
        <h2>organisers_token123</h2>
      </div>

      <SquareButton>Create conference</SquareButton>
    </header>
  );
}

export default HeaderBox;
