import { useAuth } from "../../utlis/auth";

function User() {
  const { user, logout } = useAuth();
  const { id, name, surname, email_login, affilation } = user;
  console.log("user logged-user", user);

  return (
    <main className="flex flex-col gap-12">
      <header className="mt-24">
        <h1>
          Welcome {name} {surname}
        </h1>
        <h1>Visitor from {affilation}</h1>
      </header>
      <section>
        <p>Your plan for this conference presents as follows...</p>
      </section>
    </main>
  );
}

export default User;
