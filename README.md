# Meeting_Calendar_Assistant

A Spring Boot-based REST API for managing meetings, scheduling conflicts, and finding free time slots between employees. The project uses in-memory storage and doesn't rely on any external database.

## Features

- **Book Meetings**: Schedule meetings for employees.
- **Find Free Time Slots**: Find available time slots for a meeting between two employees.
- **Check Conflicts**: Detect scheduling conflicts for meeting participants.
- **Employee Management**: Manage employee data for meeting scheduling.

## Technologies Used

- **Java 11+**
- **Spring Boot**
- **Maven**
- **JUnit** (for unit testing)
- **Lombok** (for reducing boilerplate code)

## Getting Started

### Prerequisites

- **Java 11** or higher
- **Maven** installed

The API will be accessible at http://localhost:8080.

## API Endpoints

### 1. Add an Employee
- **URL**: `POST /api/meetings/add-employee`
- **Description**: Adds a new employee to the system.
- **Parameters**:
  - `id` (Query Param): Employee ID
  - `name` (Query Param): Employee Name
- **Example Request**:

```http
POST http://localhost:8080/api/meetings/add-employee?id=1&name=John
```

### 2. Book a Meeting
- **URL**: `POST /api/meetings/book/{ownerId}`
- **Description**: Books a meeting for a specific employee (the owner) and invites other participants.
- **Path Variable**: `ownerId` (ID of the meeting owner)
```http
POST http://localhost:8080/api/meetings/book/1

Content-Type: application/json

{
  "startTime": "2024-10-15T10:00:00",
  "endTime": "2024-10-15T11:00:00",
  "participantIds": [2, 3]
}
```

### 3. Find Free Time Slots
- **URL**: `POST /api/meetings/find-free-slots`
- **Description**: Finds free time slots between two employees where a meeting of a specific duration can be scheduled.

- **Example Request**:
```http
POST http://localhost:8080/api/meetings/find-free-slots
Content-Type: application/json

{
  "employee1": { "id": 1 },
  "employee2": { "id": 2 },
  "duration": "PT30M"
}
```

### 4. Check Meeting Conflicts
- **URL**: `POST /api/meetings/check-conflicts`
- **Description**: Checks if any participants have scheduling conflicts with a proposed meeting time.

- **Example Request**:
```http
POST http://localhost:8080/api/meetings/check-conflicts
Content-Type: application/json

{
  "startTime": "2024-10-15T10:00:00",
  "endTime": "2024-10-15T11:00:00",
  "participantIds": [1, 2]
}
```


### 5. Get All Employees
- **URL**: `GET /api/meetings/employees`
- **Description**: Retrieves a list of all employees.
- **Example Request**:
```http
GET http://localhost:8080/api/meetings/employees
```


