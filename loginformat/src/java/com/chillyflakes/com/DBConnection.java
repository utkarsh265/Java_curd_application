
package com.chillyflakes.com;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
    public static void main(String[] args){
        DBConnection db_obj=new DBConnection();
        System.out.println(db_obj.get_connection());
    } 
 
    public Connection get_connection(){
        Connection con = null;
        
        try{
        
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/msdioss","root","root");
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        return con;
        
    }
}
