function PortfolioDashboard({ portfolio }) {
    if (!portfolio) return <p>Loading portfolio...</p>;

    return (
        <div className="portfolio-dashboard">
            <h2>{portfolio.name}</h2>
            <p>Age: {portfolio.age}</p>

            <h3>Products</h3>
            <ul>
                {portfolio.products.map((product) => (
                    <li key={product.id}>
                        <strong>{product.name}</strong>
                        <span className="product-type">{product.type}</span>
                        <span className="product-balance">R{product.balance.toLocaleString()}</span>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PortfolioDashboard;