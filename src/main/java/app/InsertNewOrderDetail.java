package app;

import business.OrderDetail;

import java.sql.*;

public class InsertNewOrderDetail {
    public static void main(String[] args) {
        int orderNumber = 10100;
        String prodCode = "S10_1678";
        int quant = 5;
        double price = 50.50;
        int orderLine = 77;
        OrderDetail od = new OrderDetail(orderNumber, prodCode, quant, price, orderLine);

        String driver = "com.mysql.cj.jdbc.Driver";
        String database = "classicmodels";

        String url = "jdbc:mysql://127.0.0.1:3306/" + database;
        String username = "root";
        String password = "";

        Connection conn = null;
        PreparedStatement ps = null;
        int rowsAffected = -1;

        try {
            // Step 1: Load driver
            Class.forName(driver);

            // Step 2: Make connection
            conn = DriverManager.getConnection(url, username, password);

            // Step 3: Write query
            String query = "INSERT INTO orderdetails VALUES (?, ?, ?, ?, ?)";
            // Step 4: Prepare the statement/ compile query into SQL
            ps = conn.prepareStatement(query);
            // Step 4.1: Fill in the placeholder information
            ps.setInt(1, od.getOrderNumber());
            ps.setString(2, od.getProductCode());
            ps.setInt(3, od.getQuantityOrdered());
            ps.setDouble(4, od.getPriceEach());
            ps.setInt(5, od.getOrderLineNumber());
            rowsAffected = ps.executeUpdate();
        }catch (ClassNotFoundException e) {
            System.out.println("A ClassNotFoundException occurred:" + e.getMessage());
            throw new RuntimeException(e);
        }catch (SQLIntegrityConstraintViolationException e){
            rowsAffected = 0;
        }
        catch (SQLException e) {
            System.err.println("An SQLException occurred:" + e.getMessage());
        } finally {
            // Step 7: Close everything you open:
            // PreparedStatement
            // Connection
            try {
                if(ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(rowsAffected == -1){
            System.out.println("Insert did not run!");
        }
        else if(rowsAffected == 0){
            System.out.println("Order detail " + od + " could not be added");
        }else{
            System.out.println("Order detail was successfully inserted");
        }
    }
}
