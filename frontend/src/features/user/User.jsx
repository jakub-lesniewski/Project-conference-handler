import { useLoaderData } from "react-router-dom";
import { getUser } from "../../services/api";

function User() {
  const user = useLoaderData();
  // console.log(user);

  return (
    <main className="flex flex-col gap-12">
      <header className="mt-24">
        <h1>{/* Welcome {name} {surname} */}</h1>
        {/* <h1>Visitor from {affiliation}</h1> */}
      </header>
      <section>
        <p>Your plan for this conference presents as follows...</p>
      </section>
    </main>
  );
}

export async function loader({ params }) {
  const user = getUser(params.userId);

  return user;
}

export default User;
