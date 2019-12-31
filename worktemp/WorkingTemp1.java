/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.demo.worktemp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author quangvv4!
 */
@Entity
@Table(name = "WORKING_TEMP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkingTemp1.findAll", query = "SELECT c FROM WorkingTemp1 c")
    , @NamedQuery(name = "WorkingTemp1.findByWorkingTempId", query = "SELECT c FROM WorkingTemp1 c WHERE c.workingTeampId = :workingTeampId")
    })
public class WorkingTemp1 implements Serializable, Comparable<WorkingTemp1> {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "WORKING_TEMP_SEQ", sequenceName = "WORKING_TEMP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKING_TEMP_SEQ")
    @Column(name = "WORKING_TEMP_ID")
    @SerializedName("workingTeampId")
    private Long workingTeampId;

    @Column(name = "USER_ID")
    @SerializedName("userId")
    private Long userId;
    @Column(name = "ROLE_ID")
    @SerializedName("roleId")
    private Long roleId;
    @Column(name = "DEPT_ID")
    @SerializedName("deptId")
    private Long deptId;
    @Column(name = "WORK_NAME")
    @Expose
    @SerializedName("workName")
    private String workName;
    public WorkingTemp1() {
    }

    public Long getWorkingTeampId() {
        return workingTeampId;
    }

    public void setWorkingTeampId(Long workingTeampId) {
        this.workingTeampId = workingTeampId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    @Override
    public int compareTo(WorkingTemp1 o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
