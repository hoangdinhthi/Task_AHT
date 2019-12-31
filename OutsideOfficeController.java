/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.voffice.dao.system.outsideOffice;

import com.viettel.demo.worktemp.WorkingTemp;
import com.viettel.demo.worktempdaohe.WorktempDAO;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.viettel.utils.Constants;
import com.viettel.voffice.bo.document.Books;
import com.viettel.voffice.bo.document.OutsideOffice;
import com.viettel.voffice.dao.BaseComposer;
import com.viettel.voffice.daohe.BookDAOHE;
import com.viettel.voffice.daohe.BookDocumentDAO;
import com.viettel.voffice.daohe.DepartmentDAO;
import com.viettel.voffice.daohe.OutsideOfficeDAO;
import com.viettel.voffice.daohe.docIn.DocumentReceiveDAO;
import com.viettel.voffice.model.PagingListModel;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author hoangnv28
 *
 */
public class OutsideOfficeController extends BaseComposer {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(OutsideOfficeController.class);
    @Wire
    private Textbox tbSearchOfficeName, tbSearchOfficeName1;
    @Wire
    private Textbox tbSearchIdentifyCode;

    @Wire
    private Listbox lbListOffice;

    @Wire
    private Paging officePagingTop;
    
    @Wire
    private Paging officePagingTop1;

    @Wire
    private Window windowOutsideOffice;
    
    @Wire
    private Checkbox cbSearchOfficeLock;
    
    @Wire
    private Div div2, div1;
    @Wire
    private Listbox lbListWorkTemp;
    
    public int menuType = -1;
    private Long statusUpdate;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        String type = (String) Executions.getCurrent().getArg().get(Constants.PARAMETER.MENU_TYPE);
        menuType = -1;
        if (type != null) {
            try {
                menuType = Integer.parseInt(type);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        onSearch();
    }
    

//    @Listen("onClick=#btnSearch")
//    public void onSearch() {
//        OutsideOffice searchModel = new OutsideOffice();
//        long status=0;
//        if (menuType == 2) {
//            if (tbSearchOfficeName1 != null) {
//                searchModel.setOfficeName(tbSearchOfficeName1.getValue());
//            }
//            if (tbSearchIdentifyCode != null) {
//                searchModel.setIdentifyCode(tbSearchIdentifyCode.getValue());
//            }            
//            if(cbSearchOfficeLock.isChecked()){
//                status=1;
//            }else{
//                status =0;
//                }
//            searchModel.setStatus(status);
//            searchModel.setMenuType(2l);
//        } else {
//            if (tbSearchOfficeName != null) {
//                searchModel.setOfficeName(tbSearchOfficeName.getValue());
//            }
//            searchModel.setMenuType(0l);
//        }
//        searchModel.setDeptIdsPath(getDeptIdsSearch());
//        OutsideOfficeDAO outsideOfficeDAOHE = new OutsideOfficeDAO();
//        try {
//            int take = officePagingTop.getPageSize();
//            int start = officePagingTop.getActivePage() * officePagingTop.getPageSize();
//            PagingListModel<OutsideOffice> listData = outsideOfficeDAOHE.search(searchModel, start, take);
//            ListModelArray<OutsideOffice> outsideOfficeModelList = new ListModelArray<>(listData.getLstReturn());
//            outsideOfficeModelList.setMultiple(true);
//            officePagingTop.setTotalSize(listData.getCount());
//            lbListOffice.setModel(outsideOfficeModelList);
//            lbListOffice.renderAll();
//        } catch (Exception e) {
//            Clients.showNotification(Labels.getLabel("notify.systemError"), "error", null, "middle_center", 2000);
//            logger.error(e.getMessage(), e);
//        }
//    }
    @Listen("onClick=#btnSearch")
    public void onSearch() {
       this.div1.setVisible(false);
       this.div2.setVisible(true);
        WorkingTemp searchModel = new WorkingTemp();
        if (tbSearchOfficeName != null) {
            searchModel.setWorkName(tbSearchOfficeName.getText());
        }
        WorktempDAO worktempDAO = new WorktempDAO();
        try {
            int take = officePagingTop1.getPageSize();
            int start = officePagingTop1.getActivePage() * officePagingTop1.getPageSize();
            PagingListModel<WorkingTemp> listData = worktempDAO.search(searchModel, start, take);
            ListModelArray<WorkingTemp> WorkingTempModelList = new ListModelArray<>(listData.getLstReturn());
            WorkingTempModelList.setMultiple(true);
            officePagingTop1.setTotalSize(listData.getCount());
            lbListWorkTemp.setModel(WorkingTempModelList);
            lbListWorkTemp.renderAll();
        } catch (Exception e) {
            Clients.showNotification(Labels.getLabel("notify.systemError"), "error", null, "middle_center", 2000);
            logger.error(e.getMessage(), e);
        }
    }

    @Listen("onPaging=#officePagingTop1")
    public void onPaging(PagingEvent event) {
        onSearch();
    }

    // Xu li su kien khi xem office
    @Listen("onOpenView = #lbListOffice")
    public void onOpenView(ForwardEvent event) {
        OutsideOffice outsideOffice = (OutsideOffice) event.getData();
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("outsideOffice", outsideOffice);
        arguments.put(Constants.PARAMETER.MENU_TYPE, String.valueOf(menuType));
        if (!checkComponentIsExisted("/windowCRUDOffice")) {
            Window window = (Window) Executions.createComponents("/Pages/admin/outsideOffice/view.zul", null,
                    arguments);
            window.doModal();
        }
    }

    // Xu li su kien khi sua office
    @Listen("onOpenUpdate = #lbListOffice")
    public void onOpenUpdate(ForwardEvent event) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(Constants.PARAMETER.CRUD_MODE.NAME, Constants.PARAMETER.CRUD_MODE.UPDATE);
        arguments.put(Constants.PARAMETER.VMC_PARENT, windowOutsideOffice);
        arguments.put(Constants.PARAMETER.MENU_TYPE, String.valueOf(menuType));
        arguments.put("outsideOffice", event.getData());
        if (!checkComponentIsExisted("/windowCRUDOffice")) {
            Window window = (Window) Executions.createComponents("/Pages/admin/outsideOffice/insertOrUpdate.zul", null,
                    arguments);
            window.doModal();
        }
    }

    @Listen("onSaved = #windowOutsideOffice")
    public void onSaved(Event event) {
        try {
            onSearch();
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }
    @Listen("onClick = #btnWorktemp")
    public void onClickWorktep() {
       this.div1.setVisible(false);
       this.div2.setVisible(true);
       WorktempDAO worktempDao = new WorktempDAO();
       ListModelList<WorkingTemp> list = new ListModelList<>(worktempDao.listAllWorkingTemp());
       lbListWorkTemp.setModel(list);
       
    }
    @Listen("onOpenViewWork = #lbListWorkTemp")
    public void onOpenViewWork(ForwardEvent event) {
        WorkingTemp workingTemp = (WorkingTemp)event.getData();
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("workingTemp", workingTemp);
        arguments.put(Constants.PARAMETER.MENU_TYPE, String.valueOf(menuType));
        if (!checkComponentIsExisted("/windowCRUDWorktemp")) {
            Window window = (Window) Executions.createComponents("/Pages/viewWorktemp/View.zul", null,
                    arguments);
            window.doModal();
        }
    }
    @Listen("onOpenUpdateWork= #lbListWorkTemp")
    public void onOpenUpdateWork(ForwardEvent event) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(Constants.PARAMETER.CRUD_MODE.NAME, Constants.PARAMETER.CRUD_MODE.UPDATE);
        arguments.put(Constants.PARAMETER.VMC_PARENT, windowOutsideOffice);
        arguments.put(Constants.PARAMETER.MENU_TYPE, String.valueOf(menuType));
        arguments.put("workingTemp", event.getData());
        if (!checkComponentIsExisted("/windowCRUDWorktemp")) {
            Window window = (Window) Executions.createComponents("/Pages/viewWorktemp/insertOrUpdate.zul", null,
                    arguments);
            window.doModal();
        }
    }
    @Listen("onOpenDelete=#lbListWorkTemp")
    public void onOpenDelete(ForwardEvent event) {
        onDLULWorkTemp(event);
        
    }
    public String getDeleteConfirm(String msg) {
        return String.format(Labels.getLabel("common.delete_confirm"), msg);
    }
    public String getDeleteSuccess(String msg) {
        return String.format(Labels.getLabel("common.delete_success"), msg);
    }
    public String getDeleteError(String msg) {
        return String.format(Labels.getLabel("common.delete_error"), msg);
    }
    public void showSuccessNotify(String message) {
        //showNotify(message);
        //Clients.showNotification(message);
        String zkLabel = Labels.getLabel(message);
        if (zkLabel == null || zkLabel.trim().length() == 0) {
            zkLabel = message;
        }
        Clients.showNotification(zkLabel, "success", null, "middle_center", 1500);
    }
    public void showErrorNotify(String message) {
        String zkLabel = Labels.getLabel(message);
        if (zkLabel == null || zkLabel.trim().length() == 0) {
            zkLabel = message;
        }
        Clients.showNotification(zkLabel, "error", null, "middle_center", 1500);
    }
     public void onDLULWorkTemp(ForwardEvent evts) {
        Set<Listitem> listSelectedItem = lbListWorkTemp.getSelectedItems();
        String mesConfirm = null;
        String message;
        if (!listSelectedItem.isEmpty()) {
            mesConfirm = getDeleteConfirm(Labels.getLabel("common.cat.bookdocument"));
        } else {
            message = String.format(Labels.getLabel("common.select_warning"), Labels.getLabel("common.cat.function"));
            showNotification(message, "warning");
            return;
        }
        
        Messagebox.show(mesConfirm, Labels.getLabel("Messagebox.CONFIRM"), Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evt) throws InterruptedException {
                if ("onOK".equals(evt.getName())) {
                    WorkingTemp w = new WorkingTemp();
                    w = (WorkingTemp) evts.getData();
                    WorktempDAO worktempDAO = new WorktempDAO();
                    String msg = "";
                    try {
                        worktempDAO.deleteWorkTemp(w);
                        WorktempDAO worktempDao = new WorktempDAO();
                        ListModelList<WorkingTemp> list = new ListModelList<>(worktempDao.listAllWorkingTemp());
                        lbListWorkTemp.setModel(list);
                        msg = getDeleteSuccess(Labels.getLabel("common.cat.bookdocument"));
                        showSuccessNotify(msg);
                    } catch (Exception e) {
                        logger.error(e);
                        if (statusUpdate.equals(Constants.Status.DELETE)) {
                            getDeleteError(Labels.getLabel("common.cat.bookdocument"));
                        }
                        showErrorNotify(msg);
                    }
                }
            }
        });
    }
    @Listen("onClick = #btnNewWorkTemp")
    public void onOpenCreateWorkTemp() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(Constants.PARAMETER.CRUD_MODE.NAME, Constants.PARAMETER.CRUD_MODE.CREATE);
        arguments.put(Constants.PARAMETER.VMC_PARENT, windowOutsideOffice);
        arguments.put(Constants.PARAMETER.MENU_TYPE, String.valueOf(menuType));
        if (!checkComponentIsExisted("/windowCRUDWorktemp")) {
            Window window = (Window) Executions.createComponents("/Pages/viewWorktemp/insertOrUpdate.zul", null,
                    arguments);
            window.doModal();
        }
    }
    @Listen("onClick = #toolbarOffice #btnNew, #btnNew")
    public void onOpenCreate() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(Constants.PARAMETER.CRUD_MODE.NAME, Constants.PARAMETER.CRUD_MODE.CREATE);
        arguments.put(Constants.PARAMETER.VMC_PARENT, windowOutsideOffice);
        arguments.put(Constants.PARAMETER.MENU_TYPE, String.valueOf(menuType));
        if (!checkComponentIsExisted("/windowCRUDOffice")) {
            Window window = (Window) Executions.createComponents("/Pages/admin/outsideOffice/insertOrUpdate.zul", null,
                    arguments);
            window.doModal();
        }
    }
    
    @Listen("onClick = #btnManageOutsideGroup")
    public void onOpenManageOutsideGroup() {
        Map<String, Object> arguments = new HashMap<>();
        if (!checkComponentIsExisted("/winGroupOutside")) {
            Window window = (Window) Executions.createComponents("/Pages/admin/outsideOffice/manageOutsideGroup.zul", null,
                    arguments);
            window.doModal();
        }
    }

    @Listen("onClick = #toolbarOffice #btnLock")
    public void onLock() {
        Set<Listitem> listSelectedItem = lbListOffice.getSelectedItems();

        String message;
        if (!listSelectedItem.isEmpty()) {
            boolean hasLockedItem = false;
            for (Listitem item : listSelectedItem) {
                OutsideOffice outsideOffice = item.getValue();
                if (Constants.Status.ACTIVE.equals(outsideOffice.getStatus())) {
                    hasLockedItem = true;
                    break;
                }
            }

            if (hasLockedItem) {
                if (menuType == 2) {
                    message = String.format(Labels.getLabel("common.lock_confirm"), Labels.getLabel("common.cat.outside_office_identify"));
                } else {
                    message = String.format(Labels.getLabel("common.lock_confirm"), Labels.getLabel("common.cat.outside_office"));
                }

            } else {
                message = String.format(Labels.getLabel("outsideoffice.message.isLock"));
                showNotification(message, "warning");
                return;
            }
        } else {
            if (menuType == 2) {
                message = String.format(Labels.getLabel("common.select_warning"), Labels.getLabel("common.cat.outside_office_identify"));
            } else {
                message = String.format(Labels.getLabel("common.select_warning"), Labels.getLabel("common.cat.outside_office"));
            }

            showNotification(message, "warning");
            return;
        }

        Messagebox.show(message, Labels.getLabel("common.confirm"), Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener<Event>() {
            @Override
            public void onEvent(Event e) {
                if (null != e.getName()) {
                    switch (e.getName()) {
                        case Messagebox.ON_OK:
                            // OK is clicked
                            lockOrUnlockOffice(Constants.Status.INACTIVE);
                            break;
                        case Messagebox.ON_CANCEL:
                            break;
                    }
                }
                onSearch();
                lbListOffice.clearSelection();
            }
        });
    }

    @Listen("onClick = #toolbarOffice #btnUnlock")
    public void onUnlock() {
        Set<Listitem> listSelectedItem = lbListOffice.getSelectedItems();
        String message;
        if (!listSelectedItem.isEmpty()) {
            boolean hasUnlockedItem = false;
            for (Listitem item : listSelectedItem) {
                OutsideOffice outsideOffice = item.getValue();
                if (Constants.Status.INACTIVE.equals(outsideOffice.getStatus())) {
                    hasUnlockedItem = true;
                    break;
                }
            }
            if (hasUnlockedItem) {
                if (menuType == 2) {
                    message = String.format(Labels.getLabel("common.unlock_confirm"), Labels.getLabel("common.cat.outside_office_identify"));
                } else {
                    message = String.format(Labels.getLabel("common.unlock_confirm"), Labels.getLabel("common.cat.outside_office"));
                }

            } else {
                message = String.format(Labels.getLabel("outsideoffice.message.isActive"));
                showNotification(message, "warning");
                return;
            }
        } else {
            if (menuType == 2) {
                message = String.format(Labels.getLabel("common.select_warning"), Labels.getLabel("common.cat.outside_office_identify"));
            } else {
                message = String.format(Labels.getLabel("common.select_warning"), Labels.getLabel("common.cat.outside_office"));
            }

            showNotification(message, "warning");
            return;
        }

        Messagebox.show(message, Labels.getLabel("common.confirm"), Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener<Event>() {
            @Override
            public void onEvent(Event e) {
                if (null != e.getName()) {
                    switch (e.getName()) {
                        case Messagebox.ON_OK:
                            // OK is clicked
                            lockOrUnlockOffice(Constants.Status.ACTIVE);
                            break;
                        case Messagebox.ON_CANCEL:
                            break;
                    }
                }
                onSearch();
                lbListOffice.clearSelection();
            }
        });
    }

    private void lockOrUnlockOffice(long desStatus) {
        try {
            Set<Listitem> listSelectedItem = lbListOffice.getSelectedItems();

            // Update to database
            OutsideOfficeDAO outsideOfficeDAOHE = new OutsideOfficeDAO();
            OutsideOffice outsideOffice;
            for (Listitem selectedItem : listSelectedItem) {
                // Thay doi status cua cac UNLOCKED application
                outsideOffice = (OutsideOffice) selectedItem.getValue();
                outsideOffice.setStatus(desStatus);
                outsideOfficeDAOHE.update(outsideOffice);
            }

            // Update UI
            if (desStatus == Constants.Status.ACTIVE) {
                showNotification(String.format(Labels.getLabel("common.unlock_success"), Labels.getLabel("common.cat.function")), "info");
            } else {
                showNotification(String.format(Labels.getLabel("common.lock_success"), Labels.getLabel("common.cat.function")), "info");
            }
        } catch (Exception e) {
            if (desStatus == Constants.Status.ACTIVE) {
                showNotification(String.format(Labels.getLabel("common.unlock_error"), Labels.getLabel("common.cat.function")), "error");
            } else {
                showNotification(String.format(Labels.getLabel("common.lock_success"), Labels.getLabel("common.cat.function")), "error");
            }
            logger.error(e.getMessage(), e);
        }
    }

    @Listen("onClick = #toolbarOffice #btnDelete")
    public void onDelete() {
        final Set<Listitem> listSelectedItem = lbListOffice.getSelectedItems();

        String message;
        if (!listSelectedItem.isEmpty()) {
            message = String.format(Labels.getLabel("common.delete_confirm"), Labels.getLabel("common.cat.function"));
        } else {
            message = String.format(Labels.getLabel("common.select_warning"), Labels.getLabel("common.cat.function"));
            showNotification(message, "warning");
            return;
        }

        Messagebox.show(message, Labels.getLabel("common.confirm"), Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener<Event>() {
            @Override
            public void onEvent(Event event) {
                if (null != event.getName()) {
                    switch (event.getName()) {
                        case Messagebox.ON_OK:
                            // OK is clicked
                            try {
                                List<Long> publishAgentOutsideIds = new ArrayList();
                                for (Listitem selectedItem : listSelectedItem) {
                                    OutsideOffice odo = (OutsideOffice) selectedItem.getValue();
                                    publishAgentOutsideIds.add(odo.getOfficeId());
                                }
                                int checkDel = 0;
                                DocumentReceiveDAO drDao = new DocumentReceiveDAO();
                                List<Long> drIds = drDao.getListOutsideOfficeId(publishAgentOutsideIds);
                                OutsideOfficeDAO outsideOfficeDAOHE = new OutsideOfficeDAO();
                                OutsideOffice odo;
                                for (Listitem selectedItem : listSelectedItem) {
                                    odo = (OutsideOffice) selectedItem.getValue();
                                    if (!drIds.contains(odo.getOfficeId())) {
                                        odo.setStatus(Constants.Status.DELETE);
                                        outsideOfficeDAOHE.saveOrUpdate(odo);
                                    } else {
                                        checkDel++;
                                    }
                                }
                                if (publishAgentOutsideIds.size() == checkDel) {
                                    showNotification(String.format(Labels.getLabel("common.record_used"), Labels.getLabel("common.cat.function")),
                                            "info");
                                } else {
                                    onSearch();
                                    showNotification(String.format(Labels.getLabel("common.delete_success"), Labels.getLabel("common.cat.function")),
                                            "success");
                                }
                            } catch (Exception e) {
                                showNotification(String.format(Labels.getLabel("common.delete_error"), Labels.getLabel("common.cat.function")),
                                        "error");
                                logger.error(e.getMessage(), e);
                            }
                            break;
                        case Messagebox.ON_CANCEL:
                            break;
                    }
                }
                onSearch();
                lbListOffice.clearSelection();
            }
        });
    }

    public int getMenuType() {
        if (-1 == menuType) {
            String type = (String) Executions.getCurrent().getArg().get(Constants.PARAMETER.MENU_TYPE);
            if (type != null) {
                try {
                    menuType = Integer.parseInt(type);
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return menuType;
    }
    
    private List<Long> getDeptIdsSearch() {
        Long deptIdSearch = getDeptId();
        DepartmentDAO deptHe = new DepartmentDAO();
        List<Long> deptParent = deptHe.getParentDeptIds(deptIdSearch);
        return deptParent;
    }
}
