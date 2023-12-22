export function generateRandomID() {
  const characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  let id = "";

  for (let i = 0; i < 3; i++) {
    const randomIndex = Math.floor(Math.random() * characters.length);
    id += characters.charAt(randomIndex);
  }

  return id;
}
