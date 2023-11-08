import { useRouteError } from "react-router-dom";
import confused_cat from "../assets/confused_cat.gif";

function ErrorPage() {
  const error = useRouteError();
  console.log(error);

  return (
    <div className="flex h-screen items-center justify-center">
      <div className="flex flex-col items-center justify-center gap-5">
        <h1 className="text-lg">Oops!</h1>
        <img
          src={confused_cat}
          className="w-64"
          loading="lazy"
          alt="Confused Cat"
        />
        <p>Sorry, an unexpected error has occurred.</p>
        <p>
          <i className="border-2 p-3 text-lg not-italic">
            {error.statusText || error.message}
          </i>
        </p>
      </div>
    </div>
  );
}

export default ErrorPage;
