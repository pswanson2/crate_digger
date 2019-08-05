package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectToDB {
    static final String databasePrefix ="crate_digger"; // need to change
    static final String netID ="root"; // Please enter your netId
    static final String hostName ="localhost"; //washington.uww.edu
    static final String databaseURL ="jdbc:mysql://"+hostName+"/"+databasePrefix+"?autoReconnect=true&useSSL=false";
    static final String password="DataBoyz6"; // please enter your own password
   
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public void Connection(){
  
      try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("databaseURL"+ databaseURL);
            connection = DriverManager.getConnection(databaseURL, netID, password);
            System.out.println("Successfully connected to the database");
         }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    } // end of Connection
    
    /**
     * TODO need to get this to return an array instead of printing to console.
     * Once it returns an array, we will be able to display the query result as a text field in the GUI.
     * @param sqlQuery
     */
    public String[][] Query(String sqlQuery) {
    
        String ans = "";
        String[] colNames = new String[0]; // column headers
        String[][] tuples = new String[0][0]; // 2D array of query results
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int cols = metaData.getColumnCount();

                // get column names
//            for (int i = 1; i <= cols; i++) {
//                ans += metaData.getColumnName(i) + "\t\t";
//            }

            int rows = 0;
            while (resultSet.next()) {

                for (int i = 1; i <= cols; i++) {
                    ans += resultSet.getObject(i) + "\t\t";
                }
                ans += "\n";
                rows++;
            }

            String[] rowStringData = ans.split("\n"); // all tuple data but in row strings
            tuples = new String[rows][cols]; // all data in 2D array

            for (int i = 0; i < rows; i++) {
                tuples[i] = rowStringData[i].split("\t\t");
                System.out.println(tuples[i][1]);
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return tuples;
    }
}
