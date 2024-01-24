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

