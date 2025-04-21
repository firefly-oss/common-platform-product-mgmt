# Common Platform Product Management Service

## Overview

The Common Platform Product Management Service is a microservice component of the Firefly platform that provides comprehensive product management capabilities. This service enables the creation, management, and maintenance of product data across the entire product lifecycle.

## Features

- **Core Product Management**: Create, read, update, and delete product information
- **Product Categorization**: Manage product categories and subtypes
- **Product Bundling**: Create and manage product bundles and bundle items
- **Product Features**: Define and manage product features
- **Fee Management**: Configure fee structures, components, and application rules
- **Product Lifecycle Management**: Track product lifecycle states and transitions
- **Product Limits**: Define and enforce product-specific limits
- **Localization**: Support for product information in multiple languages
- **Pricing**: Manage product pricing and currency-specific pricing
- **Product Relationships**: Define relationships between products
- **Versioning**: Track product versions and changes over time
- **Documentation**: Manage product-related documentation

## Architecture

The service follows a modular architecture with the following components:

### Modules

- **common-platform-product-mgmt-core**: Contains the business logic and service implementations
- **common-platform-product-mgmt-interfaces**: Defines DTOs, interfaces, and enums for the service
- **common-platform-product-mgmt-models**: Contains database entities and repository implementations
- **common-platform-product-mgmt-web**: Provides REST API controllers and web-specific configurations

### Technology Stack

- **Java 21**: Core programming language
- **Spring Boot**: Application framework
- **Spring WebFlux**: Reactive web framework
- **Spring Data**: Data access framework
- **Flyway**: Database migration tool
- **PostgreSQL**: Relational database
- **Maven**: Build and dependency management
- **Docker**: Containerization
- **Swagger/OpenAPI**: API documentation

## API Documentation

The service provides a RESTful API with the following main endpoints:

- `/api/v1/products`: Core product management
- `/api/v1/product-categories`: Product category management
- `/api/v1/product-bundles`: Product bundle management
- `/api/v1/product-features`: Product feature management
- `/api/v1/fee-structures`: Fee structure management
- `/api/v1/product-lifecycle`: Product lifecycle management
- `/api/v1/product-limits`: Product limit management
- `/api/v1/product-localizations`: Product localization management
- `/api/v1/product-pricing`: Product pricing management
- `/api/v1/product-relationships`: Product relationship management
- `/api/v1/product-versions`: Product version management

Detailed API documentation is available via Swagger UI when the service is running at `/swagger-ui.html`.

## Setup and Installation

### Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- Docker (for containerized deployment)
- PostgreSQL (for local development)

### Local Development Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/firefly-oss/common-platform-product-mgmt.git
   cd common-platform-product-mgmt
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   java -jar common-platform-product-mgmt-web/target/common-platform-product-mgmt.jar
   ```

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t common-platform-product-mgmt:latest .
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 common-platform-product-mgmt:latest
   ```

## Configuration

The service can be configured using standard Spring Boot configuration mechanisms. Key configuration properties include:

- Database connection settings
- Logging levels
- API security settings
- Caching configuration

Configuration can be provided via:
- `application.properties` or `application.yml` files
- Environment variables
- Command-line arguments

## Development Guidelines

### Code Style

- Follow standard Java coding conventions
- Use meaningful variable and method names
- Write comprehensive Javadoc comments
- Follow the package structure conventions

### Testing

- Write unit tests for all business logic
- Write integration tests for repository and controller layers
- Aim for high test coverage

### Branching Strategy

- `main`: Production-ready code
- `develop`: Integration branch for feature development
- Feature branches: For new features and bug fixes

### Continuous Integration

The project uses GitHub Actions for CI/CD. The workflow includes:
- Building the project
- Running tests
- Building and publishing Docker images

## Deployment

The service can be deployed in various environments:

### Development

- Deployed from the `develop` branch
- Used for integration testing and feature validation

### Production

- Deployed from the `main` branch
- Requires approval and successful testing in development environment

### Deployment Process

1. Changes are merged to the target branch
2. CI/CD pipeline builds and tests the code
3. Docker image is built and published to Azure Container Registry
4. Kubernetes deployment is updated with the new image

## Contributing

1. Create a feature branch from `develop`
2. Implement your changes
3. Write or update tests
4. Submit a pull request to `develop`
5. Ensure CI checks pass
6. Request code review