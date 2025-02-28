# Technical Challenge: Library Management System

<!--toc:start-->

- [Technical Challenge: Library Management System](#technical-challenge-library-management-system)
  - [**Project Description**](#project-description)
  - [**Functional Requirements**](#functional-requirements)
    - [1. **Authentication and Authorization with JWT:**](#1-authentication-and-authorization-with-jwt)
    - [2. **Book Management:**](#2-book-management)
    - [3. **Loan Management:**](#3-loan-management)
    - [4. **Pagination:**](#4-pagination)
    - [5. **PDF Generation:**](#5-pdf-generation)
    - [6. **Java Mail:**](#6-java-mail)
    - [7. **Swagger Documentation:**](#7-swagger-documentation)
  - [**Technical Requirements**](#technical-requirements)
    - [1. **DTO Pattern:**](#1-dto-pattern)
    - [2. **Entity Relationships:**](#2-entity-relationships)
    - [3. **Pagination:**](#3-pagination)
    - [4. **PDF Generation:**](#4-pdf-generation)
    - [5. **Java Mail:**](#5-java-mail)
    - [6. **Swagger:**](#6-swagger)
  - [**Main Entities**](#main-entities)
    - [1. **User:**](#1-user)
    - [2. **Book:**](#2-book)
    - [3. **Author:**](#3-author)
    - [4. **Loan:**](#4-loan)
  - [**Suggested Endpoints**](#suggested-endpoints)
    - [1. **Authentication:**](#1-authentication)
    - [2. **Books:**](#2-books)
    - [3. **Loans:**](#3-loans)
    - [4. **Reports:**](#4-reports)
  - [**Additional Tasks (Optional)**](#additional-tasks-optional)
    - [1. **Data Validation:**](#1-data-validation)
    - [2. **Unit Testing:**](#2-unit-testing)
    - [3. **Deployment:**](#3-deployment)
    <!--toc:end-->

## **Project Description**

Develop a library management system that allows users to register, log in, manage books, handle loans, and generate PDF reports. The system must include JWT-based authentication and authorization, Swagger documentation, and use entity relationships such as one-to-one, one-to-many, and many-to-many.

---

## **Functional Requirements**

### 1. **Authentication and Authorization with JWT:**

- Users must be able to register and log in.
- Only authenticated users can access protected endpoints.
- Roles should include:
  - **ADMIN**: Can manage books, users, and loans.
  - **USER**: Can request loans and view their history.

### 2. **Book Management:**

- CRUD operations for books (Create, Read, Update, Delete).
- Each book should have a title, author, genre, publication year, and available quantity.
- Many-to-many relationship between `Book` and `Author` (a book can have multiple authors, and an author can have multiple books).

### 3. **Loan Management:**

- A user can request a loan for a book.
- A loan has a start date, return date, and status (active, returned).
- One-to-many relationship between `User` and `Loan`.
- One-to-one relationship between `Loan` and `Book`.

### 4. **Pagination:**

- The list of books and loans must be paginated.

### 5. **PDF Generation:**

- Users can generate a PDF report with their loan history.

### 6. **Java Mail:**

- When a user requests a loan, an email with the loan details is sent to them.

### 7. **Swagger Documentation:**

- Document all endpoints using Swagger.

---

## **Technical Requirements**

### 1. **DTO Pattern:**

- Use DTOs to transfer data between application layers.

### 2. **Entity Relationships:**

- One-to-one: `Loan` -> `Book`.
- One-to-many: `User` -> `Loan`.
- Many-to-many: `Book` -> `Author`.

### 3. **Pagination:**

- Use Spring Data's `Pageable` for paginated queries.

### 4. **PDF Generation:**

- Use a library like **iText** or **Apache PDFBox** to generate the PDF.

### 5. **Java Mail:**

- Configure Spring Boot to send emails.

### 6. **Swagger:**

- Configure Swagger to document the API.

---

## **Main Entities**

### 1. **User:**

- `id`, `name`, `email`, `password`, `role`.

### 2. **Book:**

- `id`, `title`, `genre`, `publicationYear`, `availableQuantity`.

### 3. **Author:**

- `id`, `name`.

### 4. **Loan:**

- `id`, `startDate`, `returnDate`, `status`, `user`, `book`.

---

## **Suggested Endpoints**

### 1. **Authentication:**

- `POST /auth/register`: User registration.
- `POST /auth/login`: User login.

### 2. **Books:**

- `GET /books`: Get all books (paginated).
- `GET /books/{id}`: Get a book by ID.
- `POST /books`: Create a book (ADMIN only).
- `PUT /books/{id}`: Update a book (ADMIN only).
- `DELETE /books/{id}`: Delete a book (ADMIN only).

### 3. **Loans:**

- `POST /loans`: Request a loan (USER only).
- `GET /loans`: Get all loans for the user (paginated).
- `PUT /loans/{id}/return`: Return a book (USER only).

### 4. **Reports:**

- `GET /reports/loans`: Generate a PDF with the user's loan history.

---

## **Additional Tasks (Optional)**

### 1. **Data Validation:**

- Validate input data using annotations like `@NotNull`, `@Size`, etc.

### 2. **Unit Testing:**

- Write unit tests for services and controllers.

### 3. **Deployment:**

- Deploy the application to a cloud service like Heroku or AWS.
