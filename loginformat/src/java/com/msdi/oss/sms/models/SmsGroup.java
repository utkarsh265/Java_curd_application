/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msdi.oss.sms.models;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.UploadedFile;


@ManagedBean
@RequestScoped


    /**
     * Creates a new instance of WabaGroup
     */
    
    public class SmsGroup implements Serializable {

        private int groupId;
        private int campanyId;
        private String groupName;
        private String groupSpecification;
        private int status;
        private UploadedFile file;

        public SmsGroup() {
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getCampanyId() {
            return campanyId;
        }

        public void setCampanyId(int campanyId) {
            this.campanyId = campanyId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupSpecification() {
            return groupSpecification;
        }

        public void setGroupSpecification(String groupSpecification) {
            this.groupSpecification = groupSpecification;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        
        public UploadedFile getFile(){
            return file;
        }
        public void setFile(UploadedFile file){
            this.file=file;
        }

    }

   
    


