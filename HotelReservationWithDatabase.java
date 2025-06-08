import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;


public class HotelReservationWithDatabase {

    // database connections with their url, username, password
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Ranjana@2003";

    public static void main(String[] args) throws ClassNotFoundException, SQLException{   // tell to the compiler these exception may be occurs
        try {
            Class.forName  ("com.mysql.jdbc.Driver");    // load all jdbc driver whatever may be need
        }
        catch (ClassNotFoundException e){               // during this ClassNotFoundException may be occur then we handle this
            System.out.println(e.getMessage());
        }


        // connect to the database

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View a reservation");
                System.out.println("3. Get room number");
                System.out.println("4. Update reservation");
                System.out.println("5. Delete reservation");
                System.out.println("0. Exit");
                System.out.println("Choose an option : ");
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        reserveRoom(connection, sc);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, sc);
                        break;
                    case 4:
                        updateReservation(connection, sc);
                        break;
                    case 5:
                        deleteReservation(connection, sc);
                        break;
                    case 0:
                        exit();       // InterruptedException may be occur from here
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again");
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }


    // Now from here we are making function for different - different calls

    public static void reserveRoom(Connection connection, Scanner sc) {
        System.out.println("Enter guest name : ");
        String guestName = sc.next();
        sc.nextLine();
        System.out.println("Enter room number : ");
        int roomNumber = sc.nextInt();
        System.out.println("Enter contact number : ");
        String contactNumber = sc.next();

        // Inserting this data into the database
        String sql = "INSERT INTO reservation (guest_name, room_number, contact_number) " +
                "VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";

        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows > 0) {
                System.out.println("Reservation successful.");
            } else {
                System.out.println("Reservation failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void viewReservation(Connection connection) throws SQLException{
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservation;";

        try (Statement statement = connection.createStatement();        // crate statement and stor itin statement
             ResultSet resultSet = statement.executeQuery(sql)) {        // retrieve data using executeQuery()

            System.out.println("Current Reservations : ");
            System.out.println("+------------------+------------------+------------------+------------------+---------------------------------+");
            System.out.println("|  Reservation ID  |    Guest Name    |    Room Number   |  Contact Number  |          Reservation Date       |");
            System.out.println("+------------------+------------------+------------------+------------------+---------------------------------+");

            while (resultSet.next()){
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                // format and display the reservation data in table - like format
                System.out.printf("|  %-14d  |  %-15s  |  %-13d  |  %-20s  |  %19s  |\n",
                reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }
            System.out.println("+------------------+------------------+------------------+------------------+---------------------------------+");
        }
    }


    public static void getRoomNumber(Connection connection, Scanner sc){
        try {
            System.out.println("Enter reservation Id : ");
            int reservationId = sc.nextInt();
            System.out.println("Enter guest Name : ");
            String guestName = sc.next();

            String sql = "SELECT room_number FROM reservation " +
                         "WHERE reservation_id = " + reservationId +
                         "AND guest_name = " + guestName;
            try (Statement statement = connection.createStatement();        // crate statement and stor it in statement
                 ResultSet resultSet = statement.executeQuery(sql)) {        // retrieve data using executeQuery()
                if(resultSet.next()){
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for reservation ID " + reservationId + " and Guest " + guestName + " is : " + roomNumber);
                }
                else {
                    System.out.println("Reservation is not found for the given ID and guest name.");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateReservation(Connection connection, Scanner sc){
        try {
            System.out.println("Enter reservation ID to update : ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            if(!reservationExists(connection, reservationId)){
                System.out.println("Reservation not found for the given Id.");
                return;
            }

            System.out.println("Enter new guest name : ");
            String newName = sc.nextLine();
            System.out.println("Enter new room number : ");
            int newRoomNumber = sc.nextInt();
            System.out.println("Enter new contact number : ");
            String newContactNumber = sc.next();

            String sql = "UPDATE reservation SET guest_name = '" + newName + "' , " +
                         "room_number = " + newRoomNumber + " , " +
                         "contact_number = '" + newContactNumber + "' " +
                         "WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {       // crate statement and stor it in statement
                int affectedRow = statement.executeUpdate(sql);
                if (affectedRow > 0) {
                    System.out.println("Reservation updated sucessfully.");
                } else {
                    System.out.println("Reservation updated failed.");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static void deleteReservation(Connection connection, Scanner sc){
        try {
            System.out.println("Enter the reservation Id to delete : ");
            int reservationId = sc.nextInt();

            if(!reservationExists(connection, reservationId)){
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservation WHERE reservation_id = " + reservationId;


            try (Statement statement = connection.createStatement()) {       // crate statement and stor it in statement
                int affectedRow = statement.executeUpdate(sql);
                if (affectedRow > 0) {
                    System.out.println("Reservation deleted sucessfully.");
                } else {
                    System.out.println("Reservation deleted failed.");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static boolean reservationExists(Connection connection, int reservationId){
        try {
            String sql = "SELECT reservation_id FROM reservation WHERE reservation_id = " + reservationId;


            try (Statement statement = connection.createStatement();     // crate statement and stor it in statement
                 ResultSet resultSet = statement.executeQuery(sql))
            {
                return resultSet.next();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }



    public static void  exit() throws InterruptedException{
        System.out.print("Exiting system");
        int i = 5;
        while (i != 0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank You for using Hotel Management System !!!");
    }
}



