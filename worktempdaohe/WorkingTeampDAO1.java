/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.demo.worktempdaohe;

import com.viettel.demo.worktemp.WorkingTemp1;
import com.viettel.voffice.daohe.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.zkoss.util.resource.Labels;

import com.viettel.utils.Constants;
import com.viettel.utils.StringUtils;
import com.viettel.voffice.bean.UserToken;
import com.viettel.voffice.bo.Category;
import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public class WorkingTeampDAO1 extends GenericDAOHibernate<WorkingTemp1, Long> {

    private static final Logger LOGGER = Logger.getLogger(WorkingTeampDAO1.class);

    public WorkingTeampDAO1() {
        super(WorkingTemp1.class);
    }

    /*
     * 
     * Hàm cập nhật danh mục
     */
    public boolean onCreateOrUpdate(WorkingTemp1 workingTemp1, boolean isUpdate) {
        try {
            if (isUpdate) {
                update(workingTemp1);
            } else {
                create(workingTemp1);
            }
            return true;
        } catch (Exception ex) {

            LOGGER.error(ex);
            return false;
        }
    }

   
}
