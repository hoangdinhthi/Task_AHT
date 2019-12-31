/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.demo.worktemp;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aht-client16
 */
@Entity
@Table(name = "WORKING_TEMP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkingTemp.findAll", query = "SELECT w FROM WorkingTemp w")
    , @NamedQuery(name = "WorkingTemp.findByWorkingTempId", query = "SELECT w FROM WorkingTemp w WHERE w.workingTempId = :workingTempId")
    , @NamedQuery(name = "WorkingTemp.findByUserId", query = "SELECT w FROM WorkingTemp w WHERE w.userId = :userId")
    , @NamedQuery(name = "WorkingTemp.findByRoleId", query = "SELECT w FROM WorkingTemp w WHERE w.roleId = :roleId")
    , @NamedQuery(name = "WorkingTemp.findByDeptId", query = "SELECT w FROM WorkingTemp w WHERE w.deptId = :deptId")
    , @NamedQuery(name = "WorkingTemp.findByWorkName", query = "SELECT w FROM WorkingTemp w WHERE w.workName = :workName")
    , @NamedQuery(name = "WorkingTemp.findByStartTime", query = "SELECT w FROM WorkingTemp w WHERE w.startTime = :startTime")
    , @NamedQuery(name = "WorkingTemp.findByEndTime", query = "SELECT w FROM WorkingTemp w WHERE w.endTime = :endTime")
    , @NamedQuery(name = "WorkingTemp.findByStatus", query = "SELECT w FROM WorkingTemp w WHERE w.status = :status")
    , @NamedQuery(name = "WorkingTemp.findByComments", query = "SELECT w FROM WorkingTemp w WHERE w.comments = :comments")
    , @NamedQuery(name = "WorkingTemp.findByCreateTime", query = "SELECT w FROM WorkingTemp w WHERE w.createTime = :createTime")})
public class WorkingTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "WORKING_TEMP_SEQ", sequenceName = "WORKING_TEMP_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKING_TEMP_SEQ")
    @Column(name = "WORKING_TEMP_ID")
    private Long workingTempId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Column(name = "DEPT_ID")
    private Long deptId;
    @Size(max = 200)
    @Column(name = "WORK_NAME")
    private String workName;
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "STATUS")
    private Long status;
    @Size(max = 2000)
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private transient String userName;
    private transient String deptName;
    private transient String roleName;
    public WorkingTemp() {
    }

    public WorkingTemp(Long workingTempId) {
        this.workingTempId = workingTempId;
    }

    public Long getWorkingTempId() {
        return workingTempId;
    }

    public void setWorkingTempId(Long workingTempId) {
        this.workingTempId = workingTempId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workingTempId != null ? workingTempId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkingTemp)) {
            return false;
        }
        WorkingTemp other = (WorkingTemp) object;
        if ((this.workingTempId == null && other.workingTempId != null) || (this.workingTempId != null && !this.workingTempId.equals(other.workingTempId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.viettel.voffice.bo.document.WorkingTemp[ workingTempId=" + workingTempId + " ]";
    }
    
}