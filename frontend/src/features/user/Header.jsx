function Header() {
  return (
    <header className="flex flex-col gap-2">
      <h1 className="text-2xl">
        Welcome{"  "}
        <em className="font-semibold not-italic tracking-wider">
          Dr. John Doe
        </em>
      </h1>

      <section>
        <h2 className="text-xl font-medium">login</h2>
        <h2 className="text-xl font-medium">token123</h2>
      </section>
    </header>
  );
}

export default Header;
