package BasicJDBCExamples.app;

import business.Customer;

import java.sql.*;
import java.util.ArrayList;

public class SampleParameterisedSelect {
    public static void main(String[] args) {
        ArrayList<Customer> customers = new ArrayList<>();
        String state = "CA";
        String country = "USA";
        double creditLimit = 60000;

        // Steps for JDBC activities

        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseName = "classicmodels";
        String url = "jdbc:mysql://127.0.0.1:3306/" + databaseName;
        String username = "root";
        String password = "";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load the database driver
            Class.forName(driver);

            // Get a connection to the database
            conn = DriverManager.getConnection(url, username, password);

            // Write the SQL query you wish to execute on the database
            // Use placeholders to mark where variables will be inserted
            String query = "SELECT * FROM customers WHERE state = ? AND country = ? AND creditLimit > ?";

            // Load the query into a statement and prepare it for execution
            ps = conn.prepareStatement(query);
            // Fill in the blanks/placeholders in the SQL statement
            ps.setString(1, state);
            ps.setString(2, country);
            ps.setDouble(3, creditLimit);
            // Execute the query and get results
            rs = ps.executeQuery();

            // Process the results – Either loop through the results retrieved or deal with the single result returned
            while(rs.next()){
                // Build a blank customer to hold the contents of this row of the resultset
                Customer c = new Customer();

                // Extract the information from this row (piece by piece) and add it to the customer object
                c.setCustomerNumber(rs.getInt("customerNumber"));
                c.setCustomerName(rs.getString("customerName"));
                c.setContactFirstName(rs.getString("contactFirstName"));
                c.setContactLastName(rs.getString("contactLastName"));
                c.setPhone(rs.getString("phone"));
                c.setAddressLine1(rs.getString("addressLine1"));
                c.setAddressLine2(rs.getString("addressLine2"));
                c.setCity(rs.getString("city"));
                c.setState(rs.getString("state"));
                c.setPostalCode(rs.getString("postalCode"));
                c.setCountry(rs.getString("country"));
                c.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                c.setCreditLimit(rs.getDouble("creditLimit"));

                // Add the completed customer object to the list of customers
                customers.add(c);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Exception message: " + e.getMessage());
            System.out.println("Driver class could not be found. Program terminating...");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Exception message: " + e.getMessage());
            System.out.println("Problem occurred when working with database. Program terminating...");
        }
        // Close everything you opened
        finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Exception message: " + e.getMessage());
                    System.out.println("Problem occurred when closing ResultSet.");
                }
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("Exception message: " + e.getMessage());
                    System.out.println("Problem occurred when closing PreparedStatement.");
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Exception message: " + e.getMessage());
                    System.out.println("Problem occurred when closing Connection.");
                }
            }
        }

        for(Customer c: customers){
            System.out.println(c);
        }
    }
}
