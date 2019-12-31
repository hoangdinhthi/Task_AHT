/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.demo.worktempcontrollers;

import com.viettel.demo.worktemp.WorkingTemp;
import com.viettel.demo.worktempdaohe.ValidateAll;
import com.viettel.demo.worktempdaohe.WorktempDAO;
import com.viettel.utils.Constants;
import com.viettel.voffice.dao.BaseComposer;
import com.viettel.voffice.dao.system.outsideOffice.OutsideOfficeCRUDController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author ABC
 */
public class WorktempCRUDController extends BaseComposer {
    private static final Logger logger = Logger.getLogger(WorktempCRUDController.class);
    @Wire
    private Textbox tbUserID, tbRoleID, tbDeptID, tbWorkName,
            tbComment, tbStatus;
    @Wire
    private Datebox dbStartime, dbEndTime, dbCreateDate;
    @Wire
    private Button btnSave;
    @Wire
    private Window windowCRUDWorktemp;
    private int menuType = -1;
    private String crudMode;
    private WorkingTemp workingTempCurrent;
    Map<?, ?> data = Executions.getCurrent().getArg();
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp); //To change body of generated methods, choose Tools | Templates.
        
//        String type = (String) Executions.getCurrent().getArg().get(Constants.PARAMETER.MENU_TYPE);
//        menuType = -1;
//        if (type != null) {
//            try {
//                menuType = Integer.parseInt(type);
//            } catch (NumberFormatException e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
        //Map<?, ?> data = Executions.getCurrent().getArg();
        crudMode = (String) data.get(Constants.PARAMETER.CRUD_MODE.NAME);
        if (null != crudMode) {
            switch (crudMode) {
                case Constants.PARAMETER.CRUD_MODE.CREATE:
                        windowCRUDWorktemp.setTitle("Them moi WorkTemp");
                        setDateBoxDefault();
                    break;
                case Constants.PARAMETER.CRUD_MODE.UPDATE:
                        windowCRUDWorktemp.setTitle("Update WorkTemp");
                        setDateBox(data);
                    break;
            }
        }
        windowCRUDWorktemp = (Window) data.get(Constants.PARAMETER.VMC_PARENT);
        
    }
    public void setDateBoxDefault() {
        this.dbStartime.setValue(new Date());
        this.dbEndTime.setValue(new Date());
        this.dbCreateDate.setValue(new Date());
    }
    public void setDateBox( Map<?,?> data) {
        workingTempCurrent = (WorkingTemp)data.get("workingTemp");
        this.dbStartime.setValue(workingTempCurrent.getStartTime());
        this.dbEndTime.setValue(workingTempCurrent.getEndTime());
        this.dbCreateDate.setValue(workingTempCurrent.getCreateTime());
    }
    @Listen("onClick = #btnSave")
    public void onSave() {
        
        WorkingTemp objWorkingTemp = new WorkingTemp();
        WorktempDAO workDao = new WorktempDAO();
        //List<String> listError = new ArrayList<String>();
        // ValidateAll check = new ValidateAll();
        //listError = check.validateForm(this.tbUserID.getValue(), this.tbRoleID.getValue(), this.tbDeptID.getValue(),this.tbWorkName.getValue(),this.tbComment.getValue(),this.tbStatus.getValue());
//        System.out.println("aaaa"+this.tbUserID.getValue());
        
        if (!"".equals(this.tbUserID.getValue())) {
            objWorkingTemp.setUserId(Long.parseLong(this.tbUserID.getValue()));
        }
        if ( !"".equals(this.tbRoleID.getValue())) {
            objWorkingTemp.setRoleId(Long.parseLong(this.tbRoleID.getValue()));
        }
        if (!"".equals(this.tbDeptID.getValue())) {
            objWorkingTemp.setDeptId(Long.parseLong(this.tbDeptID.getValue()));
        }
        if (!"".equals(this.tbWorkName.getValue()) ) {
            objWorkingTemp.setWorkName(this.tbWorkName.getValue());
        }
        if (!"".equals(this.tbComment.getValue())) {
            objWorkingTemp.setComments(this.tbComment.getValue());
        }
        if (!"".equals(this.tbStatus.getValue())) {
            objWorkingTemp.setStatus(Long.parseLong(this.tbStatus.getValue()));
        }

        objWorkingTemp.setStartTime(this.dbStartime.getValue());
        objWorkingTemp.setEndTime(this.dbEndTime.getValue());
        objWorkingTemp.setCreateTime(this.dbCreateDate.getValue());
        try {
            if (crudMode.equals(Constants.PARAMETER.CRUD_MODE.CREATE)) {
                workDao.onCreateOrUpdate(objWorkingTemp, false);
            }
            if (crudMode.equals(Constants.PARAMETER.CRUD_MODE.UPDATE)) {
                workingTempCurrent = (WorkingTemp)data.get("workingTemp");
                objWorkingTemp.setWorkingTempId(workingTempCurrent.getWorkingTempId());
                workDao.onCreateOrUpdate(objWorkingTemp, true);
            }
            this.tbUserID.setText("");
            this.tbRoleID.setText("");
            this.tbDeptID.setText("");
            this.tbComment.setText("");
            this.tbWorkName.setText("");
            this.tbStatus.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
