# Gym CRM App

## Project Overview
This application is a **Gym CRM system** designed to manage user profiles, training sessions, and training types. It supports different user roles, including trainees and trainers, and helps track training information. The system initializes data from CSV files, processing it to set up user accounts, training schedules, and types of workouts. For each user, the application automatically generates usernames and passwords. Additionally, it logs important actions and provides robust error handling to ensure smooth operation.

## Prerequisites

To run this application, make sure you have the following installed:

- **Java Development Kit (JDK) 17**
- **Gradle** (compatible with Spring Boot 3.3.3)
- **Git** (for cloning the project repository)

## How to Install

1. Clone the project repository:
  ```bash
   git clone <repository-url>
  ```

2. Clone the project repository:
```bash
cd <project-directory>
```

3. Build the project using Gradle:
```bash
./gradlew build
```
## Run Application

To start the application locally, use the following command:
```bash
./gradlew bootRun
```

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.3**
- **Gradle** (for project management)
- **CSV Parsing** (for initial data loading)
- **AOP (Aspect-Oriented Programming)** for logging
- **Facade Pattern** for combining service logic
- **Custom Exceptions** for error handling
- **JUnit & Mockito** for testing
- **Jacoco** for test coverage reporting

## Tests

The application is fully covered with unit tests, using **JUnit** and **Mockito** to ensure the functionality of all components. The current test coverage is:

- **Line Coverage**: ![Coverage](.github/badges/jacoco.svg)
- **Branch Coverage**: ![Branches](.github/badges/branches.svg)

## License
This project is open-source and available under the [MIT License](LICENSE).