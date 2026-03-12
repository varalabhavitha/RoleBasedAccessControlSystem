# RBAC Role Management System (Spring Boot)

## Overview

This project is a **Role-Based Access Control (RBAC) system** built using **Spring Boot, Spring Data JPA, and PostgreSQL**.
It manages **Users, Roles, and Permissions** and defines relationships between them to control access to system functionality.

RBAC Flow:

```
Permission → Role → User
```

Example:

```
Permissions:
CREATE_USER
UPDATE_USER
DELETE_USER

Role:
ADMIN → [CREATE_USER, UPDATE_USER, DELETE_USER]

User:
srinu → ADMIN
```

---

# Technologies Used

* Java 17+
* Spring Boot
* Spring Data JPA (Hibernate)
* PostgreSQL
* Lombok
* Maven
* REST APIs
* Postman (for testing)

---

# Project Structure

```
src/main/java/com/uniquehire/rolemanagement

controller
 ├── RoleController
 ├── UserController
 └── PermissionController

service
 ├── RoleService
 ├── UserService
 └── PermissionService

service/impl
 ├── RoleServiceImpl
 ├── UserServiceImpl
 └── PermissionServiceImpl

repository
 ├── RoleRepository
 ├── UserRepository
 └── PermissionRepository

entity
 ├── Role
 ├── User
 └── Permission

dto
 ├── request
 │    ├── RoleRequest
 │    └── UserRequestDTO
 │
 └── response
      ├── RoleResponse
      └── UserResponseDTO
```

---

# Database Design

## USERS

| Column     | Type      |
| ---------- | --------- |
| id         | BIGINT    |
| username   | VARCHAR   |
| email      | VARCHAR   |
| password   | VARCHAR   |
| status     | BOOLEAN   |
| created_at | TIMESTAMP |
| role_id    | BIGINT    |

---

## ROLES

| Column      | Type      |
| ----------- | --------- |
| role_id     | BIGINT    |
| role_name   | VARCHAR   |
| description | VARCHAR   |
| status      | ENUM      |
| created_at  | TIMESTAMP |

---

## PERMISSIONS

| Column      | Type    |
| ----------- | ------- |
| id          | BIGINT  |
| name        | VARCHAR |
| description | VARCHAR |

---

## ROLE_PERMISSIONS

| role_id | permission_id |
| ------- | ------------- |

This table maps **roles to permissions**.

---

# Entity Relationships

```
User  → ManyToOne → Role

Role  → ManyToMany → Permission

Permission → ManyToMany → Role
```

---

# APIs

## Permission APIs

### Create Permission

POST `/permissions`

```json
{
  "name": "CREATE_USER",
  "description": "Allows creating users"
}
```

---

### Get Permission

GET `/permissions/{id}`

---

### Update Permission

PUT `/permissions/{id}`

```json
{
  "name": "CREATE_USER",
  "description": "Permission to create users"
}
```

---

### Delete Permission

DELETE `/permissions/{id}`

---

# Role APIs

### Create Role

POST `/roles`

```json
{
  "roleName": "ADMIN",
  "description": "Administrator role",
  "status": "ACTIVE",
  "createdBy": "system"
}
```

---

### Get Role

GET `/roles/{roleId}`

---

### Get All Roles

GET `/roles`

---

### Update Role

PUT `/roles/{roleId}`

```json
{
  "roleName": "ADMIN",
  "description": "Updated role",
  "status": "ACTIVE"
}
```

---

### Delete Role

DELETE `/roles/{roleId}`

---

### Assign Permission to Role

POST `/roles/{roleId}/permissions/{permissionId}`

Example:

```
POST /roles/1/permissions/2
```

---

### Assign Role to User

POST `/roles/{roleId}/users/{userId}`

Example:

```
POST /roles/1/users/3
```

---

# User APIs

### Create User

POST `/users`

```json
{
  "username": "srinu",
  "email": "srinu@gmail.com",
  "password": "password123"
}
```

---

### Get All Users

GET `/users`

---

### Get User by ID

GET `/users/{id}`

---

### Update User

PUT `/users/{id}`

```json
{
  "username": "srinu_updated",
  "email": "srinu@gmail.com",
  "password": "newpassword"
}
```

---

### Delete User

DELETE `/users/{id}`

---

# Testing Flow (Recommended)

Follow this order when testing APIs in Postman:

1. Create Permissions
2. Create Roles
3. Assign Permissions to Roles
4. Create Users
5. Assign Roles to Users

---

# Example RBAC Setup

### Permissions

| id | name        |
| -- | ----------- |
| 1  | CREATE_USER |
| 2  | UPDATE_USER |
| 3  | DELETE_USER |

---

### Roles

| role_id | role_name |
| ------- | --------- |
| 1       | ADMIN     |

---

### Role Permissions

| role_id | permission_id |
| ------- | ------------- |
| 1       | 1             |
| 1       | 2             |
| 1       | 3             |

---

### Users

| id | username | role_id |
| -- | -------- | ------- |
| 1  | srinu    | 1       |

---

# Final RBAC Flow

```
Permissions
   ↓
Roles
   ↓
Users
```

Example:

```
ADMIN → CREATE_USER
ADMIN → UPDATE_USER
ADMIN → DELETE_USER

User srinu → ADMIN
```

---

# Future Improvements

* Add **JWT Authentication**
* Add **Spring Security**
* Add **Permission-based API authorization**

Example:

```java
@PreAuthorize("hasAuthority('CREATE_USER')")
```

* Add **Audit Logging**
* Add **User Login API**

---

# Conclusion

This project demonstrates a **complete RBAC implementation** using Spring Boot, including:

* Role Management
* Permission Management
* User Management
* Role-Permission Mapping
* Role-User Assignment

It forms the **foundation for enterprise authentication and authorization systems**.
