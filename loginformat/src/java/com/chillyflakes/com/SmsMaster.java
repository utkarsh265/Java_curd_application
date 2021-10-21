/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chillyflakes.com;

import com.sun.faces.context.SessionMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
/**
 *
 * @author hp
 */

public class SmsMaster {

    private int groupId;
    private int companyId;
    private String groupName;
    private String groupSpecs;
    private String status;
    private ArrayList userList;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 


    public int getGroupId() {
        return groupId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupSpecs() {
        return groupSpecs;
    }

    public String getStatus() {
        return status;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupSpecs(String groupSpecs) {
        this.groupSpecs = groupSpecs;
    }

    public void setStatus(String status) {
        this.status = status;
        if (this.status.equals("1")) {
            this.status = "active";

        } else {
            this.status = "inactive";
        }
    }

//    To DISPLAY DATA
    public ArrayList userList() {

        try {
            userList = new ArrayList();
            Connection con = null;
            DBConnection db = new DBConnection();

            con = db.get_connection();
            Statement st = con.createStatement();
            System.out.println("working fetching");
            ResultSet rs = st.executeQuery("select * from tb_sms_groups");
            while (rs.next()) {
                System.out.println(" fetching");
                SmsMaster sm = new SmsMaster();
                sm.setGroupId(rs.getInt("group_id"));
                sm.setCompanyId(rs.getInt("company_id"));
                sm.setGroupName(rs.getString("group_name"));
                sm.setGroupSpecs(rs.getString("group_specs"));
                sm.setStatus(rs.getString("status"));

                userList.add(sm);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return userList;
    }

//    TO create new file
    

    public void newFile() {
        int result = 0;
        try {
            Connection con = null;
            DBConnection db = new DBConnection();
            con = db.get_connection();
            PreparedStatement st = con.prepareStatement("insert into tb_sms_groups (company_id , group_name , group_specs)values('" + companyId + "' , '" + groupName + "' ,'" + groupSpecs + "') ");
            result = st.executeUpdate();
            System.out.println(result + "hi am result");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (result != 0) {
            
        } else {
            
        }
    }
    
//    TO DISABLE FILE
    
    public String disable(int groupId){
        try{
            Connection con = null ;
            DBConnection db = new DBConnection();
            con = db.get_connection();
            System.out.println("disable working");
            PreparedStatement st = con.prepareStatement("update tb_sms_groups set status = 0 where group_id ="+groupId);
            System.out.println(st);
            st.executeUpdate();
            
        }catch(Exception e){
            System.out.println(e);
        }
            return "SmsGroups.xhtml?faces-redirect=true";
    }

    
    
    //Edit File
    
    public String editfile(int groupId){
        try{
         Connection con = null;
         DBConnection db = new DBConnection();
         con = db.get_connection();
         Statement st = con.createStatement();
         
         ResultSet rs =  st.executeQuery("select * from tb_sms_groups where group_id= "+(groupId));
            System.out.println(rs+" result set");
              
         rs.next();
    SmsMaster sm = new SmsMaster();
         
         sm.setCompanyId(rs.getInt("company_id"));
         sm.setGroupId(rs.getInt("group_id"));
         sm.setGroupName(rs.getString("group_name"));
         sm.setGroupSpecs(rs.getString("group_specs"));
         sm.setStatus(rs.getString("status"));
         sessionMap.put("editFile", sm);
         con.close();
         
        }catch(Exception e ){
            System.out.println(e);
        }
        return "/edit.xhtml?faces-redirect=true";
        
    }
    
    // Update file
    
    public String updateFile(SmsMaster sm){
        
        try{
            Connection con = null;
            DBConnection db = new DBConnection();
            con = db.get_connection();
            PreparedStatement st = con.prepareStatement("update tb_sms_groups set company_id=? , group_name=? , group_specs=? , status=? where group_id =?");
            st.setInt(1, sm.getCompanyId());
            st.setString(2, sm.getGroupName());
            st.setString(3, sm.getGroupSpecs());
            st.setString(4, sm.getStatus());
            st.setInt(5, sm.getGroupId());
            st.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return "/welcome1.xhtml?faces-redirect=true";
    }
    /**
     * Creates a new instance of SmsMaster
     */
    public SmsMaster() {
    }

}
