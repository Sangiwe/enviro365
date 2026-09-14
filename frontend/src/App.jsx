import { useState, useEffect } from "react";
import { getPortfolio, getWithdrawalHistory } from "./api/api";
import PortfolioDashboard from "./components/PortfolioDashboard";
import WithdrawalForm from "./components/WithdrawalForm";
import WithdrawalHistoryTable from "./components/WithdrawalHistoryTable";
import "./App.css";

function App() {
    const investorId = 1; // hardcoded — no login/investor-selection in this assessment's scope

    const [portfolio, setPortfolio] = useState(null);
    const [withdrawals, setWithdrawals] = useState([]);
    const [error, setError] = useState(null);

    function loadPortfolio() {
        getPortfolio(investorId)
            .then(setPortfolio)
            .catch((err) => setError(err.message));
    }

    function loadWithdrawals() {
        getWithdrawalHistory(investorId)
            .then(setWithdrawals)
            .catch((err) => setError(err.message));
    }

    function handleWithdrawalSuccess() {
        loadPortfolio();     // balance changed, so refresh it
        loadWithdrawals();   // new row to show
    }

    useEffect(() => {
        loadPortfolio();
        loadWithdrawals();
    }, []);

    return (
        <div className="app">
            <h1>Enviro365 Investments</h1>

            {error && <p className="error">{error}</p>}

            <PortfolioDashboard portfolio={portfolio} />

            {portfolio && (
                <WithdrawalForm
                    investorId={investorId}
                    products={portfolio.products}
                    onSuccess={handleWithdrawalSuccess}
                />
            )}

            <WithdrawalHistoryTable withdrawals={withdrawals} />
        </div>
    );
}

export default App;