package lib;
import java.sql.*;
public class DBConnect{
   
    static Statement stmt;
    static Connection con;
    static ResultSet rs;
    public static void connect(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
            stmt=con.createStatement();
        }catch(Exception e){
            System.out.println("Connect_Error:"+e);
        }

    }
    public static Boolean isExist(String SQL){
        Boolean b=false;
        try{
            connect();
            rs=stmt.executeQuery(SQL);
            b=rs.next();
        }catch(Exception se){
            System.out.println("Search Error: "+se);
        }
        return b;
    }

}