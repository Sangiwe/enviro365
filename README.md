# Enviro365 Withdrawal Notice System

Full-stack system for Enviro365 Investments allowing investors to view portfolios,
submit withdrawal notices, and download statements. Built as part of the eTalente
Junior Software Developer assessment (2026).

## Tech Stack

- **Backend:** Java 21, Spring Boot 4.1.1, Spring Data JPA, H2 (in-memory)
- **Frontend:** TBD
- **Testing:** JUnit 5, Mockito

## Project Structure

enviro365/
├── backend/ # Spring Boot API
└── frontend/ # UI (TBD)


## Business Rules Implemented

- Retirement product withdrawals only allowed if investor age > 65
- Withdrawal amount cannot exceed the product's current balance
- Withdrawal amount cannot exceed 90% of the product's current balance

## Setup Instructions

### Backend
1. Requires Java 21+
2. Navigate to `backend/`
3. Run `./mvnw spring-boot:run`
4. App starts on `http://localhost:8080`
5. H2 in-memory database seeds automatically on startup with test data (2 investors, 3 products)

### Frontend
*(To be added)*

## API Documentation

*(To be added as endpoints are built)*

## AI Usage Disclosure

This project was built with AI assistance (Claude, for planning/architecture/explanation,
and used to review and understand all generated code before committing). Key areas of
AI assistance so far:

- Entity relationship design (Investor → Product → WithdrawalNotice)
- JPA repository interface structure
- Business rule implementation in WithdrawalService
- Unit test structure using Mockito (mocking repositories, stubbing, verifying calls)
- DTO and mapper class structure to avoid exposing entities directly / circular JSON references

All AI-suggested code was reviewed, tested, and understood before being committed —
particularly the reasoning behind BigDecimal for currency, the exception hierarchy
design, and the mapper pattern for entity/DTO separation.

## Screenshots

*(To be added once frontend is built)*