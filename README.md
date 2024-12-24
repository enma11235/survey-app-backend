# Survey System API

This is an API for a survey system implemented in Java. The API allows users to register and log in to the system, as well as to create, delete, manage and vote in surveys. It is designed to be simple, scalable, and easy to integrate with other systems.

## Features

- **Create Surveys**: Allows users to create new surveys with various questions.
- **Manage Surveys**: Provides endpoints for editing, deleting, and retrieving surveys.
- **Respond to Surveys**: Allows users to submit responses to surveys.
- **Authentication**: Secure API with authentication mechanisms to ensure that only authorized users can manage surveys.

## Technologies Used

- **Java**: The core language used for implementing the API.
- **Spring Boot**: Framework used to build the RESTful API.
- **Hibernate**: Used for database operations.
- **JWT (JSON Web Token)**: For authentication and authorization.
- **PostgreSQL**: Relational database used for storing user and survey data.

## Endpoints

### Survey Endpoints

- `POST api/survey` - Create a new survey.
- `GET api/survey` - Retrieve a list of all surveys.
- `GET api/survey/{id}` - Get details of a specific survey by ID.
- `PUT api/survey/{id}` - Update a survey by ID.
- `DELETE api/survey/{id}` - Delete a survey by ID.

### User Endpoints

- `POST api/user` - Register a new user into the system.
- `GET api/user` - Retrieve all a list of all users.
- `GET api/user/{id}` - Get details of a specific user by ID.
- `PUT api/user/{id}` - Update a user by ID.
- `DELETE api/user/{id}` - Delete a user by ID.

### Authentication

- `POST /auth/login` - Log in and receive a JWT token for authenticated access.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/survey-api.git
