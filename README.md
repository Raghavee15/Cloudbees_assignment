# Cloudbees_assignment

# Spring Boot Product Management API

This repository contains a simple Spring Boot-based REST API for managing products on an e-commerce platform. The API supports CRUD operations for products and allows the application of either a discount or tax to a product.

## Features

- Create, Read, Update, and Delete (CRUD) operations for products
- Apply discount or tax to a product
- In-memory storage for data

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or later
- Maven (for building and managing dependencies)
- Git (for version control)
## Build and Run

mvn spring-boot:run

## API Endpoints

# Create Product
  POST /api/products
# Read Product
  GET /api/products/{productId}
# Update Product
  PUT /api/products
# Delete Product
  DELETE /api/products/{productId}
# Apply Discount or Tax
  POST /api/products/{productId}/apply?rate={rate}&isDiscount={true/false}

# Description

### 1. Product Entity (`Product.java`):
The `Product` class serves as the entity model for products. It is annotated with JPA annotations, indicating that instances of this class will be stored in a relational database. The key attributes include:

- `productId`: A unique identifier for each product.
- `name`: The name of the product.
- `description`: A description providing additional information about the product.
- `price`: The price of the product.
- `quantityAvailable`: The quantity of the product available in stock.

### 2. Product Repository (`ProductRepository.java`):
The `ProductRepository` interface extends `JpaRepository`, a Spring Data interface providing generic CRUD operations. This repository is used for interacting with the underlying database for managing `Product` entities. It allows operations like saving, retrieving, updating, and deleting products.

### 3. Product Service (`ProductService.java`):
The `ProductService` class contains the business logic for managing products. It communicates with the `ProductRepository` for database operations. The key methods include:

- `createProduct(Product product)`: Saves a new product to the database.
- `readProduct(Long productId)`: Retrieves a product by its unique identifier.
- `updateProduct(Product product)`: Updates an existing product in the database.
- `deleteProduct(Long productId)`: Deletes a product from the database.
- `applyDiscountOrTax(Long productId, double percentageOrTax, boolean isDiscount)`: Modifies the price of a product by applying either a discount or tax based on the provided percentage.

### 4. Product Controller (`ProductController.java`):
The `ProductController` class defines RESTful endpoints for interacting with products. It handles incoming HTTP requests, delegates operations to the `ProductService`, and returns appropriate HTTP responses. Key endpoints include:

- `POST /api/products/create`: Creates a new product.
- `GET /api/products/read/{productId}`: Retrieves product details by its ID.
- `PUT /api/products/update`: Updates an existing product.
- `DELETE /api/products/delete/{productId}`: Deletes a product.
- `POST /api/products/applyDiscountOrTax/{productId}/{percentageOrTax}/{isDiscount}`: Applies discount or tax to a product.

### 5. Main Application Class (`ECommerceApplication.java`):
The `ECommerceApplication` class serves as the entry point for the Spring Boot application. It is annotated with `@SpringBootApplication`, combining `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. This annotation enables auto-configuration and component scanning for the application.

### 6. Unit Tests:
Unit tests are provided for both the `ProductService` and `ProductController` classes:

- `ProductServiceTest.java`: Utilizes JUnit and Mockito to test various scenarios for creating, reading, updating, deleting, and applying discount or tax on products.
- `ProductControllerTest.java`: Uses `@WebMvcTest` to perform integration tests for the RESTful endpoints in the `ProductController`. It verifies the correct behavior of the controller methods in response to HTTP requests.

Overall, this Spring Boot application provides a foundation for managing an e-commerce product catalog, allowing users to perform essential operations on products through a RESTful API. The modular design and use of Spring Data JPA simplify database interactions, while the unit tests ensure the correctness of the implemented functionality.

