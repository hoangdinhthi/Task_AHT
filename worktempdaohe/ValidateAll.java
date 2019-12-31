/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.demo.worktempdaohe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABC
 */
public class ValidateAll {
    public List<String> validateForm(String UserId, String RoleId, String DeptId, String WorkName, String comment, String status) {
        List<String> ListError = new ArrayList<String>();
        if (UserId == null && RoleId == null && DeptId == null && WorkName == null && comment == null && status == null ) {
            ListError.add("khong duoc de null");
        }
        if ("".equals(UserId) && "".equals(RoleId) && "".equals(DeptId)) {
            ListError.add("khong duoc de trong!");
        }
        return ListError;
    }
}
