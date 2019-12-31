/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.demo.worktempdaohe;

import com.itextpdf.text.log.SysoLogger;
import com.viettel.demo.worktemp.WorkingTemp;
import com.viettel.demo.worktemp.WorkingTemp1;
import com.viettel.utils.HibernateUtil;
import com.viettel.utils.StringUtils;
import com.viettel.voffice.bo.document.OutsideOffice;
import com.viettel.voffice.daohe.GenericDAOHibernate;
import com.viettel.voffice.model.PagingListModel;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;

/**
 *
 * @author ABC
 */
public class WorktempDAO extends GenericDAOHibernate<WorkingTemp, Long> {
    private static final Logger LOGGER = Logger.getLogger(WorktempDAO.class);
    int batch_size = Integer.parseInt(Dialect.DEFAULT_BATCH_SIZE); 
    public WorktempDAO() {
         super(WorkingTemp.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<WorkingTemp> listAllWorkingTemp() {
        List<WorkingTemp> resultList = null;
        try{
            String hql = "FROM WorkingTemp WHERE STATUS = 1";
            Query query = getSession().createQuery(hql);
            resultList = query.list();
        } catch (Exception e) {
            LOGGER.error(e);
            return new ArrayList<>();
        }
        
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public void insertWorkingTemp (List<WorkingTemp> listInput) {
        try {
            for (int i = 0 ; i < listInput.size(); i++) {
                    getSession().save(listInput.get(i));
//                if (i == batch_size) {
//                    getSession().flush();
//                    getSession().clear();
//                }
            }
            /*for (WorkingTemp item: listInput) {
                getSession().saveOrUpdate(item);
            }*/
            System.out.println("OK");
        } catch (Exception e) {
            LOGGER.error(e);
            return;
        }
    }
    @SuppressWarnings("unchecked")
    public List<WorkingTemp> findWorktempById (long idWorktemp) {
        List<WorkingTemp> listWorktemp = null;
        try {
            String hql = "SELECT * FROM working_temp WHERE WORKING_TEMP_ID = :workID";
            SQLQuery query = getSession().createSQLQuery(hql);
            query.addEntity(WorkingTemp.class);
            query.setParameter("workID", idWorktemp);
            listWorktemp = query.list();
        } catch (Exception e) {
            LOGGER.error(e);
            return new ArrayList<>();
        }
        return listWorktemp;
    }
    @SuppressWarnings("unchecked")
     public boolean onCreateOrUpdate(WorkingTemp WorkingTemp, boolean isUpdate) {
        try {
            if (isUpdate) {
                update(WorkingTemp);
            } else {
                create(WorkingTemp);
            }
            return true;
        } catch (Exception ex) {

            LOGGER.error(ex);
            return false;
        }
    }
    @SuppressWarnings("unchecked")
    public void updateWorkTemp (List<WorkingTemp> listInput) {
        try {
            for (int i = 0 ; i < listInput.size(); i++) {
                getSession().update(listInput.get(i));
                if (i == batch_size) {
                    getSession().flush();
                    getSession().clear();
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return;
        }
    }
    
    @SuppressWarnings("unchecked")
    public void deleteWorkTemp (WorkingTemp work) {
       try {
              WorktempDAO obj = new WorktempDAO();
              work.setStatus(0l);
              work.setWorkingTempId(work.getWorkingTempId());
              obj.update(work);
        } catch (Exception e) {
            LOGGER.error(e);
            return;
        }
    }
    public PagingListModel<WorkingTemp> search(WorkingTemp searchData, int start, int take) throws Exception {
        Query query = createQuery(searchData, false, null);
        Query countQuery = createQuery(searchData, true, null);
        query.setFirstResult(start);
        if (take < Integer.MAX_VALUE) {
            query.setMaxResults(take);
        }
        List<WorkingTemp> lst = query.list();
        Long count = (Long) countQuery.list().get(0);
        PagingListModel<WorkingTemp> model = new PagingListModel<WorkingTemp>(lst, count);
        return model;
    }
    
     private Query createQuery(WorkingTemp searchData, boolean isCount, Integer isRound) {
        StringBuilder hql = new StringBuilder();
        if (isCount) {
            hql.append(" SELECT count(o) FROM WorkingTemp o where 1=1 ");
        } else {
            hql.append(" SELECT DISTINCT o FROM WorkingTemp o where 1=1 ");
        }
        List<Object> params = new ArrayList<Object>();
        if (searchData != null) {
            if ((searchData.getWorkName() != null) && (!"".equals(searchData.getWorkName()))) {
                hql.append(" AND lower(o.workName) LIKE ? ESCAPE '/' ");
                params.add(StringUtils.toLikeAndLowerCaseString(searchData.getWorkName()));
            }
            if (isRound != null) {
                hql.append(" AND ROWNUM < ? ");
                params.add(Long.valueOf(isRound) + 1);
            }
        }
        if (!isCount) {
            hql.append(" order by nlssort(lower(ltrim(o.workName))) ");
        }
        Query query = getSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        return query;
    }
    
    public static void main(String args[]) {
        //WorktempDAO obj = new WorktempDAO();
//        List<WorkingTemp> listMain = new ArrayList<WorkingTemp>();
//        WorkingTemp workingTemp = new WorkingTemp();
//        workingTemp.setWorkingTempId(9797l);
//       // workingTemp.setWorkingTempId(Long.MIN_VALUE);
//        workingTemp.setUserId(34l);
//        workingTemp.setDeptId(379l);
//        workingTemp.setRoleId(54l);
//        workingTemp.setWorkName("ThiHD");
//        workingTemp.setStartTime(new Date());
//        workingTemp.setEndTime(new Date());
//        workingTemp.setStatus(1l);
//        workingTemp.setComments("this is my test");
//        workingTemp.setCreateTime(new Date());
//        try{
//            obj.saveOrUpdate(workingTemp);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        System.out.println("ok");
        try {
            WorkingTeampDAO1 obj = new WorkingTeampDAO1();
            WorkingTemp1 workingTemp = new WorkingTemp1();
            workingTemp.setUserId(34l);
            workingTemp.setDeptId(379l);
            workingTemp.setRoleId(54l);
            workingTemp.setWorkName("ThiHD");
            obj.onCreateOrUpdate(workingTemp,false);
            HibernateUtil.commitCurrentSessions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        
        
        /*listMain.add(workingTemp);
        
        obj.insertWorkingTemp(listMain);*/
//        listMain = obj.findWorktempById(32l);
//        for(WorkingTemp item: listMain) {
//            System.out.println(item.getWorkName());
//        }
          //obj.deleteWorkTemp(4100l);
        //obj.saveOrUpdate(listMain.get(0));
        
    }
    
    
    
     
}
