const BASE_URL = "http://localhost:8080/api";

// every function below can reuse the same
// error-handling behavior instead of repeating it in every component.
async function handleResponse(response) {
    if (!response.ok) {
        const errorBody = await response.json();
        throw new Error(errorBody.message || "Something went wrong.");
    }
    return response.json();
}

export async function getPortfolio(investorId) {
    const response = await fetch(`${BASE_URL}/investors/${investorId}`);
    return handleResponse(response);
}

export async function getWithdrawalHistory(investorId) {
    const response = await fetch(`${BASE_URL}/withdrawals/investor/${investorId}`);
    return handleResponse(response);
}

export async function submitWithdrawal(productId, amount) {
    const response = await fetch(`${BASE_URL}/withdrawals`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ productId, amount }),
    });
    return handleResponse(response);
}

export function getCsvExportUrl(investorId) {
    return `${BASE_URL}/statements/export?investorId=${investorId}`;
}