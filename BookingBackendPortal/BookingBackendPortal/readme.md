# Booking System API

A RESTful booking system built with Spring Boot 3.2, JWT authentication, and PostgreSQL.

## Features

- JWT-based authentication
- Role-based access control (ADMIN/USER)
- Resource management
- Reservation system with status tracking
- Filtering and pagination
- Overlapping reservation prevention

## Setup Instructions

### Prerequisites

- Java 17+
- PostgreSQL
- Maven

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE booking_db;