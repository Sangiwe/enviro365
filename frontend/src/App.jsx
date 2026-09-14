import PortfolioDashboard from "./components/PortfolioDashboard";
import "./App.css";

function App() {
  const investorId = 1; // hardcoded for now Thandiwe Mokoena from our seed data

  return (
      <div className="app">
        <h1>Enviro365 Investments</h1>
        <PortfolioDashboard investorId={investorId} />
      </div>
  );
}

export default App;