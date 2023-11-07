import Header from "./Header";

function User() {
  return (
    <main className="flex flex-col gap-12">
      <Header />
      <section>
        <p>Your plan for this conference presents as follows...</p>
      </section>
    </main>
  );
}

export default User;
