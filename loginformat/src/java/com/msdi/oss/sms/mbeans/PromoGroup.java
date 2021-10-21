/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msdi.oss.sms.mbeans;

import com.chillyflakes.com.DBConnection;
import com.msdi.oss.sms.models.SmsGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author hp
 */
@ManagedBean
@RequestScoped
public class PromoGroup {

    private SmsGroup newGroup;
    private String errMsg;
    private List<SmsGroup> groupList;

    DBConnection dbConn = new DBConnection();

    public PromoGroup() {

        errMsg = "";
        newGroup = new SmsGroup();
        groupList = new ArrayList<>();
        fetchGroupList();
    }

    public void createNewGroup() throws SQLException {
        Connection con = null;
        String groupName = newGroup.getGroupName();
        String groupSpecs = newGroup.getGroupSpecification();
        int status = newGroup.getStatus();
        int campanyId = newGroup.getCampanyId();
        groupName = (groupName != null && groupName.length() > 0) ? groupName.trim() : "NA";
        groupSpecs = (groupSpecs != null && groupSpecs.length() > 0) ? groupSpecs.trim() : "NA";

        if (!"NA".equalsIgnoreCase(groupName)) {
            //Write data insertion code here
            errMsg = "New Group Created Successfully!";

        } else {
            errMsg = "Invalid Group Name!";
        }

        System.out.println("Create Group Invoked." + newGroup.getGroupName());

        try {
            con = dbConn.get_connection();
            PreparedStatement st = con.prepareStatement("insert into tb_sms_groups (group_name,group_specs, company_id) "
                    + "values('" + groupName + "' ,'" + groupSpecs + "' , '"+ campanyId+"' )");
            st.executeUpdate();
        } catch (Exception e) {
        }
        con.close();

    }

    public void onEdit(RowEditEvent event) {
        Connection con=null;
        try {
            SmsGroup sm = (SmsGroup) event.getObject();
            con=dbConn.get_connection();
            PreparedStatement st = con.prepareStatement("update tb_sms_groups set group_name='"+sm.getGroupName()+"' ,"
                    + " group_specs='"+sm.getGroupSpecification()+"', status='"+sm.getStatus()+"' where group_id='"+sm.getGroupId()+"'");
            st.executeUpdate();
            System.out.println("Group to update is " + sm.getGroupName());
            sm=null;
            con.close();
        } catch (Exception e) {
        }
        
    }

    public void fetchGroupList() {
        Connection conn = null;
        try {
            conn = dbConn.get_connection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT group_id,group_name,group_specs,STATUS FROM tb_sms_groups;");

            groupList.clear();
            SmsGroup sgroup = null;
            while (rs.next()) {
                sgroup = new SmsGroup();
                sgroup.setGroupId(rs.getInt("group_id"));
                sgroup.setGroupName(rs.getString("group_name"));
                sgroup.setGroupSpecification(rs.getString("group_specs"));
                sgroup.setStatus(rs.getInt("status"));
                groupList.add(sgroup);
                sgroup = null;
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ee) {
            }
            conn = null;
        }

    }

    public SmsGroup getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(SmsGroup newGroup) {
        this.newGroup = newGroup;
    }

    public List<SmsGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<SmsGroup> groupList) {
        this.groupList = groupList;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
