/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msdi.oss.sms.mbeans;

import com.msdi.oss.sms.models.LoginForm1;
import com.msdi.oss.util.DBConnection1;
import java.sql.Connection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hp
 */

@ManagedBean
@SessionScoped

public class LoginService1 {

    private String errorMessage;
    private LoginForm1 loginForm;
    DBConnection1 dbConn = new DBConnection1();

    public LoginService1() {
        errorMessage = "";
        loginForm = new LoginForm1();

    }

    public String checkLogin() {
        Connection conn = null;

        try {
            String loginId=loginForm.getLoginId();
            String pwd = loginForm.getPassword();
//            
//          Validate both loginid and password
            loginId=loginId!=null? loginId.trim() :"NA";
            pwd=(pwd!=null)? pwd.trim() : "NA";
            if("NA".equalsIgnoreCase(loginId)){
                errorMessage = "Invalid login id";
            
            }else if ("NA".equalsIgnoreCase(pwd)){
            
                errorMessage = "Invalid password";
            }else{
            conn = dbConn.getConnection();
            java.sql.Statement st = conn.createStatement();
            StringBuilder sbSql = new StringBuilder("SELECT a.user_id,a.login_id,a.login_pwd,a.role_id,b.`role_name`,a.status FROM tb_users a,tb_user_roles b WHERE a.`role_id`=b.`role_id` AND a.`status`>0 AND b.`status`>0 ");
            sbSql.append("and a.login_id='").append(loginId).append("'and a.login_pwd='").append(pwd).append("'");
            
            java.sql.ResultSet rs = st.executeQuery(sbSql.toString());
            while (rs.next()) {
                loginForm.setUserId(rs.getInt(1));
                   loginForm.setLoginId(rs.getString(2));
                   loginForm.setPassword(rs.getString(3));
                   loginForm.setRoleId(rs.getInt(4));
                   loginForm.setRoleName(rs.getString(5));
                   loginForm.setStatus(rs.getInt(6));

            }
            rs.close();
            rs = null;
            st.close();
            st = null;
            }
        } catch (Exception e) {
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ee) {
            }
            conn = null;
        }
        if(loginForm.getUserId()>0){
            errorMessage="Login Successful!";
            return "success";
            
        }else{
            errorMessage="Login Failed!";
            return "login";
        }
    }

    public LoginForm1 getLoginForm1() {
        return loginForm;
    }

    public void setLoginForm1(LoginForm1 loginForm) {
        this.loginForm = loginForm;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
