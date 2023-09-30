package BasicJDBCExamples.app;

import BasicJDBCExamples.business.OrderDetail;

import java.sql.*;
import java.util.ArrayList;

public class SelectAllOrderDetails {
    public static void main(String[] args) {
        ArrayList<OrderDetail> orderDetails = new ArrayList();

        String driver = "com.mysql.cj.jdbc.Driver";
        String database = "classicmodels";

        String url = "jdbc:mysql://127.0.0.1:3306/" + database;
        String username = "root";
        String password = "";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Step 1: Load driver
            Class.forName(driver);

            // Step 2: Make connection
            conn = DriverManager.getConnection(url, username, password);

            // Step 3: Write query
            String query = "SELECT * FROM orderdetails";
            // Step 4: Prepare the statement/ compile query into SQL
            ps = conn.prepareStatement(query);

            // Step 5: Run query and get results
            rs = ps.executeQuery();

            // Step 6: Loop through results and put into DTO
            while(rs.next()){
                int orderNum = rs.getInt("orderNumbers");
                String pCode = rs.getString("productCode");
                int quant = rs.getInt("quantityOrdered");
                double price = rs.getDouble("priceEach");
                int orderLine = rs.getInt("orderLineNumber");
                OrderDetail od = new OrderDetail(orderNum, pCode, quant, price, orderLine);
                orderDetails.add(od);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("A ClassNotFoundException occurred:" + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("An SQLException occurred:" + e.getMessage());
        } finally {
            // Step 7: Close everything you open:
            // ResultSet
            // PreparedStatement
            // Connection

            try {
                // Check that the result set exists - if it does then close it
                if(rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("An SQLException occurred while closing the result set: " + e.getMessage());
            }
            try {
                // Check that the prepared statement exists - if it does then close it
                if(ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("An SQLException occurred while closing the prepared statement: " + e.getMessage());
            }
            try {
                // Check that the connection exists - if it does then close it
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("An SQLException occurred while closing the connection: " + e.getMessage());
            }
        }

        for(OrderDetail od : orderDetails){
            System.out.println(od);
        }
    }
}
