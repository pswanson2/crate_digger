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
    static final String password="dddddddd"; // please enter your own password
   
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
    public ResultSet Query(String sqlQuery) {
    
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columns = metaData.getColumnCount();
//
//            for (int i=1; i<= columns; i++) {
//                System.out.print(metaData.getColumnName(i)+"\t");
//            }
//
//            System.out.println();
//
//            while (resultSet.next()) {
//       
//                for (int i=1; i<= columns; i++) {
//                    System.out.print(resultSet.getObject(i)+"\t\t");
//                }
//                System.out.println();
//            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
