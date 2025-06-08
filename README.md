# 🏨 Hotel Booking System – Java + MySQL

A **Java-based Hotel Booking System** using **JDBC** and **MySQL** for managing hotel room bookings. This console application allows users to book rooms, view and update customer details, and cancel bookings through a connected relational database.

---

## ✅ Features

- 🛏️ Book Hotel Rooms (Single, Double, Deluxe, etc.)
- 📋 View All Current Bookings
- ✏️ Update Customer Booking Details
- ❌ Cancel Existing Bookings
- 💾 MySQL Database Integration via JDBC

---

## 🛠️ Tech Stack

- **Java** (Core)
- **JDBC** (Java Database Connectivity)
- **MySQL** (RDBMS)
- **IDE**: IntelliJ IDEA / Eclipse
- **Build Tool** (Optional): Maven or Gradle

---


## ⚙️ How to Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/HotelBookingSystem.git
   cd HotelBookingSystem
   ```

2. **Set Up the Database**
   - Create a MySQL database named `hotel_booking`
   - Run the SQL script from `/db/hotel_booking.sql` to create necessary tables

3. **Configure JDBC**
   - In `DatabaseConnection.java`, edit:
     ```java
     String url = "jdbc:mysql://localhost:3306/hotel_booking";
     String username = "your_db_username";
     String password = "your_db_password";
     ```

4. **Compile and Run**
   - Compile and run `Main.java` from your IDE or terminal

---

## 🧾 Example Functionalities

- Booking a room:
  ```
  Enter customer name:
  Enter room type:
  Enter check-in and check-out dates:
  ```
- Viewing bookings:
  ```
  Displays a list of all bookings with customer and room details
  ```
- Updating a booking:
  ```
  Enter Booking ID to update:
  Enter new check-out date:
  ```
- Deleting a booking:
  ```
  Enter Booking ID to cancel:
  ```

---

## 🚀 Future Enhancements

- Add GUI using Java Swing or JavaFX
- Implement room availability checks
- Generate booking receipts
- Export booking data to CSV

---

## 👨‍💻 Author

Pradeep Rana  
[GitHub](https://github.com/Pradeep-rana-code)
