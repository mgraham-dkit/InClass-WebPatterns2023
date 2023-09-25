package app;

import business.OrderDetail;

import java.sql.*;

public class InsertNewOrderDetail {
    public static void main(String[] args) {
        // Variables to hold new Order Detail information to be added to the database
        int orderNumber = 10100;
        String prodCode = "S10_1678";
        int quant = 5;
        double price = 50.50;
        int orderLine = 77;
        OrderDetail od = new OrderDetail(orderNumber, prodCode, quant, price, orderLine);

        // Database-specific code:
        // MySQL driver class location
        String driver = "com.mysql.cj.jdbc.Driver";

        // Database to be used for the insert
        String database = "classicmodels";
        // URL of the database server, including the database name
        String url = "jdbc:mysql://127.0.0.1:3306/" + database;
        // Credentials for accessing the database
        String username = "root";
        String password = "";

        // Database interaction variables
        // We need a connection object that links us to the database, so we can run the insert
        Connection conn = null;
        // We need a prepared statement to compile the insert statement into SQL that the database can run
        PreparedStatement ps = null;
        // We need a variable to hold the result of the insert
        int rowsAffected = -1;

        try {
            // Step 1: Load driver
            Class.forName(driver);

            // Step 2: Make connection
            conn = DriverManager.getConnection(url, username, password);

            // Step 3: Write query
            // The ?s are placeholders, marking where we will include variable data
            // This way we are protected from an SQL injection
            String query = "INSERT INTO orderdetails VALUES (?, ?, ?, ?, ?)";
            // Step 4: Prepare the statement/ compile query into SQL
            ps = conn.prepareStatement(query);
            // Step 4.1: Fill in the placeholder information
            // This is done by SETTING the content of each placeholder
            // The number represents which placeholder (question mark) we are filling in
            ps.setInt(1, od.getOrderNumber());
            ps.setString(2, od.getProductCode());
            ps.setInt(3, od.getQuantityOrdered());
            ps.setDouble(4, od.getPriceEach());
            ps.setInt(5, od.getOrderLineNumber());
            // Step 5: Execute the SQL and store the result
            rowsAffected = ps.executeUpdate();
            // Handle where the driver is not found
        }catch (ClassNotFoundException e) {
            System.err.println("A ClassNotFoundException occurred:" + e.getMessage());
            throw new RuntimeException(e);
            // Handle where the primary/foreign key constraints aren't upheld
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("An SQLIntegrityConstraintViolationException occurred:" + e.getMessage());
            rowsAffected = 0;
            // Handle where any other issue occurs with the database interaction code
        }catch (SQLException e) {
            System.err.println("An SQLException occurred:" + e.getMessage());
            // No matter what happens - whether everything worked correctly or something failed - we need to
            // close the various resources
        } finally {
            // Step 7: Close everything you open:
            // PreparedStatement
            // Connection
            try {
                // Check that the prepared statement exists - if it does then close it
                if(ps != null) {
                    ps.close();
                }
                // Handle if something goes wrong when trying to close the prepared statement
            } catch (SQLException e) {
                System.err.println("An SQLException occurred while closing the prepared statement: " + e.getMessage());
            }
            try {
                // Check that the connection exists - if it does then close it
                if(conn != null) {
                    conn.close();
                }
                // Handle if something goes wrong when trying to close the connection
            } catch (SQLException e) {
                System.err.println("An SQLException occurred while closing the connection: " + e.getMessage());
            }
        }
        // If we never updated the rowsAffected value, then the insert statement never ran
        if(rowsAffected == -1){
            System.out.println("Insert did not run!");
        }// If the rowsAffected is set to 0, then there was an issue related to primary/foreign keys (this is the
        // only catch where the variable is set to 0, so it must have ended up in there
        else if(rowsAffected == 0){
            System.out.println("Order detail " + od + " could not be added");
        }else{
            // If the value is not -1 or 0, then there was something inserted!
            System.out.println("Order detail was successfully inserted");
        }
    }
}
