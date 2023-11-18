import { Outlet, useNavigation } from "react-router-dom";
import Loader from "./Loader";

function LandingLayout() {
  const naviagtion = useNavigation();
  console.log(naviagtion.state);
  const isLoading = naviagtion.state === "loading";

  return (
    <main className="flex min-h-screen items-center justify-center">
      {isLoading && <Loader />}
      <Outlet />
    </main>
  );
}

export default LandingLayout;
