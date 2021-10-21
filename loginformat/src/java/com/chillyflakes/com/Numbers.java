/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chillyflakes.com;

import static java.lang.reflect.Array.set;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class Numbers {

    private String number;
    private ArrayList usersList;
    private String id;
    
   private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

    Connection connection;

    public void setid(String id) {

        this.id = id;
    }

    public String getid() {

        return id;
    }

    public void setnumber(String number) {
        this.number = number;

    }

    public String getnumber() {
        return number;
    }

// View data records 
    public ArrayList usersList() {
        

        try {
            usersList = new ArrayList();
            Connection connection = null;
            DBConnection obj_DBConnection = new DBConnection();
            connection = obj_DBConnection.get_connection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from userdata");
            while (rs.next()) {
                Numbers obj_numbers = new Numbers();

                obj_numbers.setnumber(rs.getString("number"));
                obj_numbers.setid(rs.getString("id"));
                usersList.add(obj_numbers);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;

    }

//    Add data records !
    public String savenumber() {
        int result = 0;
        System.out.println(result);
        try {
            Connection con = null;
            DBConnection obj_dbconnection = new DBConnection();
            con = obj_dbconnection.get_connection();
            PreparedStatement stmt = con.prepareStatement("insert into userdata(number) values('"+number+"')");

//            stmt.setString(1, number);
            result = stmt.executeUpdate();
            System.out.println(" hi i am result" +result);
            System.out.println(result);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (result != 0) {
            return "welcome1.xhtml?faces-redirect=true";
        } else {
            return "welcome1.xhtml?faces-redirect=true";
        }
    }

//    delete record 

    
    public String deletenumber(String id) {
//        System.out.println("working");
//        FacesContext fc = FacesContext.getCurrentInstance();
        
//        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
//        String field_id = params.get("action");
//        System.out.println("working");
        try {
            Connection con = null;
            DBConnection obj_dbconnection = new DBConnection();
            con = obj_dbconnection.get_connection();
            System.out.println(con);
            PreparedStatement stmt = con.prepareStatement("delete from userdata where id=" +id);
//            stmt.setString(1, field_id);
            System.out.println(stmt);
            stmt.executeUpdate();
//            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return "welcome1.xhtml?faces-redirect=true";
    }
    
    
    public String editnumber(String id){
    
     
     
     try {
          DBConnection obj_DB_connection=new DBConnection();
          Connection con=obj_DB_connection.get_connection();
          Statement st=con.createStatement();
          ResultSet rs=st.executeQuery("select * from userdata where id="+(id));
          Numbers obj_number=new Numbers();
          rs.next();
          obj_number.setnumber(rs.getString("number"));
          obj_number.setid(rs.getString("id"));
          sessionMap.put("editUser", obj_number);
          con.close();
          
           
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/edit.xhtml?faces-redirect=true";   
}

    public String updatenumber(Numbers num){
        
        try{
     DBConnection obj_dbconnection = new DBConnection();
      Connection con = obj_dbconnection.get_connection();
      PreparedStatement st = con.prepareStatement("update userdata set number=? where id =?");
      st.setString(1, num.getnumber());
      st.setString(2, num.getid());
      st.executeUpdate();
      con.close();
    }catch(Exception e){
            System.out.println(e);
    }
        return "welcome1.xhtml?faces-redirect=true";
    }
    
    
    
    
    
    
    
    public Numbers() {
    }

}
