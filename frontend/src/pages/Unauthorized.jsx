export default function Unauthorized() {
  return (
    <div style={{
      display: "flex",
      flexDirection: "column",
      justifyContent: "center",
      alignItems: "center",
      height: "100vh",
      backgroundColor: "#f8f9fa",
      color: "#333",
      textAlign: "center"
    }}>
      <h2 style={{ fontSize: "2rem", marginBottom: "10px", color: "#dc3545" }}>
        Access Denied
      </h2>
      <p style={{ fontSize: "1.2rem" }}>
        You are not authorized to view this page.
      </p>
    </div>
  );
}
