package BasicJDBCExamples.app;

import business.Customer;

import java.sql.*;
import java.util.ArrayList;

public class SampleBasicSelect {
    public static void main(String[] args) {
        ArrayList<Customer> customers = new ArrayList<>();

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
            String query = "SELECT * FROM customers";

            // Load the query into a statement and prepare it for execution
            ps = conn.prepareStatement(query);

            // Execute the query and get results
            rs = ps.executeQuery();

            // Process the results – Either loop through the results retrieved or deal with the single result returned
            while(rs.next()){
                Customer c = new Customer();

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
