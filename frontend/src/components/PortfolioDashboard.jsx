import { useState, useEffect } from "react";
import { getPortfolio } from "../api/api";

function PortfolioDashboard({ investorId }) {
    const [portfolio, setPortfolio] = useState(null);
    const [error, setError] = useState(null);

    // Runs once when the component first renders, and again if investorId changes.
    // Fetching data is a "side effect" — something that reaches outside this
    // component (to the network) — which is exactly what useEffect is for.
    useEffect(() => {
        getPortfolio(investorId)
            .then(setPortfolio)
            .catch((err) => setError(err.message));
    }, [investorId]);

    if (error) return <p className="error">Error loading portfolio: {error}</p>;
    if (!portfolio) return <p>Loading portfolio...</p>;

    return (
        <div className="portfolio-dashboard">
            <h2>{portfolio.name}</h2>
            <p>Age: {portfolio.age}</p>

            <h3>Products</h3>
            <ul>
                {portfolio.products.map((product) => (
                    <li key={product.id}>
                        <strong>{product.name}</strong> ({product.type}) — R
                        {product.balance.toLocaleString()}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PortfolioDashboard;