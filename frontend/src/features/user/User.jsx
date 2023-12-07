import { useLoaderData } from "react-router-dom";
import { getUser } from "../../services/api";
import { useAuth } from "../../utils/auth";
import Button from "../../ui/Button";

function User() {
  const user = useLoaderData();
  const { logout } = useAuth();
  const { name, surname, affilation } = user.user;

  return (
    <main className="flex flex-col gap-12">
      <header className="mt-24">
        <h1>
          Welcome{" "}
          <em className="font-semibold not-italic tracking-wider">
            {name} {surname}
          </em>
        </h1>
        <h1>
          Visitor from{" "}
          <em className="font-semibold not-italic tracking-wider">
            {affilation}{" "}
          </em>
        </h1>
        <Button onClick={logout}>Logout</Button>
      </header>

      <section>
        <p>Your plan for this conference presents as follows...</p>
      </section>
    </main>
  );
}

export async function loader({ params }) {
  const user = await getUser(params.userId);

  return user;
}

export default User;
