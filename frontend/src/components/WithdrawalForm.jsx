import { useState } from "react";
import { submitWithdrawal } from "../api/api";

function WithdrawalForm({ investorId, products, onSuccess }) {
    const [productId, setProductId] = useState("");
    const [amount, setAmount] = useState("");
    const [error, setError] = useState(null);
    const [submitting, setSubmitting] = useState(false);

    function validate() {
        if (!productId) return "Please select a product.";
        if (!amount || Number(amount) <= 0) return "Amount must be greater than zero.";
        return null;
    }

    async function handleSubmit(e) {
        e.preventDefault(); // stops the browser's default full-page-reload form submission
        setError(null);

        const validationError = validate();
        if (validationError) {
            setError(validationError);
            return;
        }

        setSubmitting(true);
        try {
            await submitWithdrawal(Number(productId), Number(amount));
            setAmount("");
            setProductId("");
            onSuccess(); // tells the parent component to refresh (e.g. reload history/balance)
        } catch (err) {
            setError(err.message); // backend's business rule messages surface
        } finally {
            setSubmitting(false);
        }
    }

    return (
        <form onSubmit={handleSubmit} className="withdrawal-form">
            <h3>Submit a Withdrawal</h3>

            <label>
                Product:
                <select value={productId} onChange={(e) => setProductId(e.target.value)}>
                    <option value="">-- Select a product --</option>
                    {products.map((p) => (
                        <option key={p.id} value={p.id}>
                            {p.name} (R{p.balance.toLocaleString()} available)
                        </option>
                    ))}
                </select>
            </label>

            <label>
                Amount:
                <input
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    min="0"
                    step="0.01"
                />
            </label>

            <button type="submit" disabled={submitting}>
                {submitting ? "Submitting..." : "Submit Withdrawal"}
            </button>

            {error && <p className="error">{error}</p>}
        </form>
    );
}

export default WithdrawalForm;