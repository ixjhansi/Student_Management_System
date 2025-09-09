# **Student Management System
## **Overview:
The Student Management System is a web-based application developed using Spring Boot, JPA, and MS SQL Server. It provides an easy and efficient way to manage student, teacher, subject, and class information for educational institutions. This system allows administrators to allocate rooms for classes based on student count, manage teachers for different subjects, and keep track of student and class information.

## **Features:
- Student Management: Add, update, delete, and view student details.
- Teacher Management: Manage teacher information, including assigning subjects and classes.
- Subject Management: Add subjects and track which teachers handle them.
- Room Allocation: Allocation of rooms to classes.
- Pagination: Fetch records with paging support for better performance.
- RESTful APIs: Complete API support for front-end integration.
- Data Persistence: Uses MS SQL Server for storing all data.
- Validation: Ensures data consistency, e.g., students cannot be assigned to rooms exceeding capacity.

## **Technologies Used:
Backend: Java, Spring Boot 3.5.5, Spring Data JPA<br>
Database: MS SQL Server<br>
Build Tool: Maven<br>
API Documentation: Swagger <br>
IDE: Eclipse<br>

## **Database Structure:
<img width="2120" height="1645" alt="Student_management_system" src="https://github.com/user-attachments/assets/ed09c4f6-a7e5-45c4-a9b4-7bfdb5f38f6f" />

## **Installation
### **Prerequisites:
- Java JDK 17 or above
- Maven 3.x
- MS SQL Server
- IDE (Eclipse, IntelliJ IDEA, etc.)

## **Steps:
Clone the repository:
git clone <repository-url><br>
Configure the database:
Create a database in MS SQL Server (e.g., student_management_db)<br>
Update application.properties with your database URL, username, and password:<br>
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=student_management_db<br>
spring.datasource.username=your_username<br>
spring.datasource.password=your_password<br>
spring.jpa.hibernate.ddl-auto=update<br>

### **Build the project:
mvn clean install:
Run the application<br>
mvn spring-boot:run<br>
### **Access APIs:
Open http://localhost:8080/swagger-ui/index.html<br>
 
Use Postman or frontend to interact with REST APIs


Author<br>
Dola Jhansi Peraka
