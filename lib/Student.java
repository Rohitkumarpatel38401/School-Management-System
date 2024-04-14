package lib;
import java.sql.*;
public class Student extends DBConnect{
    
    public static Boolean add_Student(String SQL){
        int x=0;
        try{
            connect();
            x=stmt.executeUpdate(SQL);
        }catch(Exception ea){
            System.out.println("Add_Student Error: "+ea);
        }
        if(x>0) 
            return true;
        else
            return false;
    }

    public static ResultSet viewStudent(String SQL){
        ResultSet r=null;
        try{
            connect();
            r=stmt.executeQuery(SQL);
        }catch(Exception e){
            System.out.println("ViewStudents Error: "+e);
        }
        return r;
    }
    public static Boolean delete_Student(String SQL){
        int x=0;
        try{
            connect();
            x=stmt.executeUpdate(SQL);
        }catch(Exception e){
            System.out.println("delete_Student Error: "+e);
        }
        if(x>0) 
            return true;
        else
            return false;
    }
    public static int getMaxValue(String table,String column){
        int m=0;
         try{
            connect();
            String SQL="select max("+column+") from "+table;
           ResultSet rs=stmt.executeQuery(SQL);
           if(rs.next()){
                m=rs.getInt("MAX("+column+")");
           }
        }catch(Exception er){
            System.out.println("getRollNo Error: "+er);
        }
        return m;
    }

     public static int countRow(String table,String column){
        int m=0;
         try{
            connect();
            String SQL="select count("+column+") from "+table;
           ResultSet rs=stmt.executeQuery(SQL);
           if(rs.next()){
                m=rs.getInt("count("+column+")");
           }
        }catch(Exception er){
            System.out.println("Error: "+er);
        }
        return m;
    }
    public static int countRow(String table){
        int m=0;
         try{
            connect();
            String SQL="select count(id) from "+table;
           ResultSet rs=stmt.executeQuery(SQL);
           if(rs.next()){
                m=rs.getInt("count(id)");
           }
        }catch(Exception er){
            System.out.println("Error: "+er);
        }
        return m;
    }

}