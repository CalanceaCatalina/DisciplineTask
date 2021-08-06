import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static void main(String[] args) {
        // Connecting to DB - SQL server
        String DBurl = "jdbc:sqlserver://EN411812\\PERF_SQLSERVER:1433;databaseName=BikeStores;user=sa;password=Performance123!";
        System.out.print("Successful connection to SQL Server.\n");

        try {
            //using getconnection() method to establish a connection to the database by using the given database URL
            Connection connectionGet = DriverManager.getConnection(DBurl);
            //A Statement is an interface that represents a SQL statement.
            //Statement: Used to implement simple SQL statements with no parameters.
            Statement stmt = connectionGet.createStatement();

            // Insert query
            //executeUpdate: Returns an integer representing the number of rows affected by the SQL statement.
            //Use this method if you are using INSERT, DELETE, or UPDATE SQL statements.
            stmt.executeUpdate(
                    "insert into sales.customers " +
                            "(first_name, last_name, phone, email, street, city, state, zip_code) " +
                            "values " +
                            "('Meredith', 'Grey', 327409, 'mgrey@gmail.com', '21 Washington street', 'Brooklyn', 'NY', 20223)"
            );

            // Insert query
            stmt.executeUpdate(
                    "insert into sales.customers " +
                            "(first_name, last_name, phone, email, street, city, state, zip_code) " +
                            "values " +
                            "('Alice', 'Grey', 324609, 'agrey@gmail.com', '20 Washington street', 'Brooklyn', 'NY', 2021)"
            );

            // Update query
            stmt.executeUpdate(
                    "update sales.customers " +
                            "set email = 'alice.grey@gmail.com'" +
                            "where last_name = 'Grey' and first_name = 'Alice'"
            );

            // Delete query
            stmt.executeUpdate(
                    "delete from sales.customers " +
                            "where last_name = 'Grey' and first_name = 'Alice'"
            );

            // Bulk insert
            String SQLBulk = "BULK INSERT sales.customers FROM 'C:\\Customers.csv' WITH (FIRSTROW = 2, FIELDTERMINATOR = ',', ROWTERMINATOR='\n') ";
            System.out.print("In the sales.customers table were successfully added 100 customers. \n");

            // Execute the SQL statement
            System.out.print("The results of the query are the following:\n");
            String SQL = "SELECT * FROM sales.customers WHERE last_name = 'Grey'";
            //when we execute Statement objects, so they generate ResultSet objects, which is a table of data representing a database result set.
            ResultSet resultSet = stmt.executeQuery(SQL);
            //An object that can be used to get information about the types and properties of the columns in a ResultSet object.
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            // Iterate through the data in the result set and display it
            while (resultSet.next()) {
                //get the number of columns from a JDBC ResultSet
                int nrCol = rsMetaData.getColumnCount();
                //StringBuilder creates an empty string builder with a capacity of 16 (16 empty elements).
                StringBuilder row = new StringBuilder("|| ");
                for (int i = 1; i <= nrCol; i++) {
                    row.append(resultSet.getString(i)).append(" || ");
                }
                System.out.println(row);
            }
        }
        // Handle any errors that may have occurred
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



