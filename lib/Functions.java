package lib;
import java.sql.*;
public class Functions extends DBConnect{
    public static Boolean isExists(String table,String col_name,String value){
        Boolean res=false;
        try{
            connect();
            stmt=con.createStatement();
            String SQL="select * from "+table+" where "+col_name+"='"+value+"'";
            System.out.println(SQL);
            rs=stmt.executeQuery(SQL);
            if(rs.next()) 
                res=true;
        }catch(Exception e){
            System.out.println("isExist : "+e);
        }
        return res;
    }//Overloaded methods
    public static Boolean isExists(String table,String col_name,int value){
        Boolean res=false;
        try{
            connect();
            stmt=con.createStatement();
            String SQL="select * from "+table+" where "+col_name+"="+value;
            System.out.println(SQL);
            rs=stmt.executeQuery(SQL);
            if(rs.next()) 
                res=true;
        }catch(Exception e){
            System.out.println("isExist : "+e);
        }
        return res;
    }
    

}


