function WithdrawalHistoryTable({ withdrawals }) {
    if (withdrawals.length === 0) {
        return (
            <div className="withdrawal-history">
                <h3>Withdrawal History</h3>
                <p>No withdrawals yet.</p>
            </div>
        );
    }

    return (
        <div className="withdrawal-history">
            <h3>Withdrawal History</h3>
            <table>
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Amount</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {withdrawals.map((w) => (
                    <tr key={w.id}>
                        <td>{w.dateRequested}</td>
                        <td>R{w.amount.toLocaleString()}</td>
                        <td className={w.status === "APPROVED" ? "status-approved" : "status-rejected"}>
                            {w.status}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default WithdrawalHistoryTable;