/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msdi.oss.util;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author hp
 */
public class DBConnection1 {

    public DBConnection1(){
    }
    
    public Connection getConnection(){
        Connection conn=null;
        try{
            conn=getMsdiossdb().getConnection();
        }catch(Exception e){
            conn=null;
        }
        return conn;
    }
    
    
    
    private DataSource getMsdiossdb() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/msdiossdb");
    }
    
}
