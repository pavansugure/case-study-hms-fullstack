# 🏨 Online Hotel Management System – Capgemini Internship Case Study project

A full-stack, microservice-based **Hotel Management System** designed and developed as part of my internship at **Capgemini**. This system streamlines operations for three key user roles — **Owner**, **Manager**, and **Receptionist** — by handling all hotel operations like room booking, guest management, staff assignments, payments, and inventory control.

---

## 📌 Project Overview

This system follows a **microservices architecture** with dedicated services for various hotel operations. It includes secure role-based access using **JWT authentication**, **React frontend**, and **MySQL** as the database.

---

## 👤 End Users

1. **Owner** – Full access to all services
2. **Manager** – Access to room, staff, and inventory modules
3. **Receptionist** – Access to guest, reservation, and payment modules

Each user type has restricted access based on their role via **Spring Security with JWT**.

---

## 🧩 Microservices Implemented

| Service         | Description                                                                 |
|------------------|-----------------------------------------------------------------------------|
| `GuestService`   | Add, update, delete guest info; fetch guest details                        |
| `RoomService`    | Manage rooms (add, update, delete, availability)                           |
| `ReservationService` | Book rooms, manage reservations, search by date range               |
| `PaymentService` | Handles payment integration and invoice generation                         |
| `InventoryService` | Manages hotel inventory (stock in/out, status)                          |
| `StaffService`   | Manage staff details, assign duties                                        |
| `ApiGateway`     | Centralized routing using Spring Cloud Gateway                             |
| `ServiceRegistry`| Eureka server for service discovery                                        |

---

## 🔒 Security

- **JWT (JSON Web Token)** for authentication and authorization
- Implemented via **Spring Security**
- Role-based access control per endpoint
- Unauthorized users cannot access restricted APIs

---

## 🔄 Service Communication

- Inter-service communication via **Feign Clients**
- Example: `PaymentService` fetches reservation and guest details using Feign

---

## ⚙️ Technologies Used

### 🖥 Backend
- Core Java
- Spring Boot
- Spring REST
- Spring Security (JWT)
- Spring Data JPA
- Spring Cloud (Eureka, Feign, Gateway)
- MySQL
- Postman (API testing)

### 💻 Frontend
- React.js
- Vite (for fast build)
- HTML, CSS, JavaScript, ES6+
- Axios (for HTTP communication)

---

## 🗄 Database

- **MySQL**
- All services have their own separate schema/tables
- Relationships managed via JPA

---

## 📁 Project Structure

```bash
CaseStudy-HMS-Fullstack/
├── frontend/              # React-based frontend
└── backend/               # Microservice modules
    ├── ApiGateway/
    ├── GuestService/
    ├── RoomService/
    ├── ReservationService/
    ├── PaymentService/
    ├── InventoryService/
    ├── StaffService/
    └── ServiceRegistry/
