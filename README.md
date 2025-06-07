# Minimal-Task-Manager-with-Spring-Boot-Thymeleaf
A lightweight web-based task manager built with Spring Boot, Thymeleaf, and MySQL. Features include user login, task creation, due dates, priority levels, and tag filtering

# 🧠 SmartTask – Minimal Task Manager with Spring Boot & Thymeleaf

SmartTask is a lightweight, user-friendly task management web application built using **Spring Boot**, **Thymeleaf**, and **MySQL**. It allows users to manage tasks efficiently with features like due dates, priority levels, tag categorization, and a clean dashboard UI—all without using Spring Security or HTTP sessions.

---

## 🚀 Features

- ✅ User registration & login (without Spring Security)
- 🗂️ Create, edit, and delete tasks
- 🕒 Set due dates and priority levels
- 🔖 Add tags to categorize tasks
- 📊 Dashboard view of all tasks
- 🎨 Bootstrap-styled responsive UI

---

## 🛠️ Tech Stack

| Layer        | Technology           |
|--------------|----------------------|
| Backend      | Spring Boot          |
| Frontend     | Thymeleaf + Bootstrap |
| Database     | MySQL                |
| Language     | Java                 |
| Build Tool   | Maven                |

---
### ✅ Prerequisites

- Java 17+
- Maven
- MySQL
- Git

## 🛠 Create MySQL Database

1. **Start MySQL Server**  
   - Make sure your MySQL server is running.  
   - You can use tools like **XAMPP**, **MySQL Workbench**, or terminal.

2. **Create the Database**

sql
CREATE DATABASE taskmanager;
## ⚙️ Setup Instructions

### 3. Clone the repository:
bash
https://github.com/SureshB2938/Minimal-Task-Manager-with-Spring-Boot-Thymeleaf
cd Minimal-Task-Manager-with-Spring-Boot-Thymeleaf

### 4. Set Your Credentials

Open the `src/main/resources/application.properties` file and update the credentials as per your MySQL setup:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

> ✅ Replace `your_mysql_username` and `your_mysql_password` with your actual MySQL username and password.

💻 Running the Application
🔧 1. Open Command Prompt
Navigate to the root folder of your project:

⚙️ 2. Build the Project

mvn clean install

▶️ 3. Run the Application

mvn spring-boot:run

4. The app will start at:

http://localhost:8080
