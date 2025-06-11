import { useState } from 'react';

function App() {
  const [message, setMessage] = useState("Welcome to Hotel Management System!");
  return (
    <div>
      <h1>{message}</h1>
    </div>
  );
}

export default App;