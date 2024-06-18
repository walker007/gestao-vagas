# Job Management System

Job Management System is a Java-based application designed to manage job listings and applications. The system supports different permission levels, allowing companies to register and post job openings, while candidates can create accounts, browse available jobs, and apply for them. The application uses JWT for authentication and incorporates Spring Security, Spring Validation, and Lombok.

## Features

- **Company Management**: Companies can register, log in, and post job openings.
- **Job Applications**: Candidates can create accounts, log in, browse available jobs, and apply for them.
- **User Permissions**: Two levels of permissions - CANDIDATE and COMPANY.
- **Authentication**: Secure authentication using JWT.
- **Validation**: Input validation using Spring Validation.
- **Simplified Code**: Use of Lombok for boilerplate code reduction.

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Java JDK
- Maven

### Installation

1. Clone the repository:
   ```sh
   git clone git@github.com:walker007/gestao-vagas.git
   cd gestao-vagas
   ```
2. Install dependencies and build the project:
```sh
mvn clean install
```
## Built With
- Spring Boot - The Java framework used
- Spring Security - For authentication and authorization
- Spring Validation - For input validation
- Lombok - To reduce boilerplate code

## Usage
Companies can register and manage job listings, while candidates can browse and apply for jobs securely through the JWT authentication mechanism.
