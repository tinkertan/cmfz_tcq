package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.baizhi.entity.User;
import com.baizhi.service.Userservice;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private Userservice userservice;
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        return userservice.queryAll(page,rows);
    }
    @RequestMapping("edit")
    public void edit(String[] ids){
        for (String ii:ids) {
            User user = userservice.queryById(ii);
            user.setId(ii);
            if ("正常".equals(user.getStatus())) {
                user.setStatus("冻结");
            } else {
                user.setStatus("正常");
            }
            userservice.update(user);
        }
    }
    @RequestMapping("exportFile")
    public void exportFile(HttpServletResponse response,HttpSession session){
        List<User> users = userservice.queryAllNoPage();
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户","用户表"),User.class,users);
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("user.xls","utf-8"));
            workbook.write(response.getOutputStream());
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping("importUser")
    public void importUser(MultipartFile file){
        try {
            Workbook workbook = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheet.getRow(i + 1);
                User user = new User();
                user.setId(row.getCell(0).getStringCellValue());
                user.setPhone(row.getCell(1).getStringCellValue());
                user.setDharmaname(row.getCell(4).getStringCellValue());
                user.setProvince(row.getCell(5).getStringCellValue());
                user.setCity(row.getCell(6).getStringCellValue());
                user.setGender(row.getCell(7).getStringCellValue());
                user.setPersonalSign(row.getCell(8).getStringCellValue());
                if(row.getCell(9)!=null){
                    user.setProfile(row.getCell(9).getStringCellValue());
                }else {
                    user.setProfile("empty");
                }
                try{
                    user.setRegistTime(row.getCell(11).getDateCellValue());
                }catch(Exception e){
                    e.printStackTrace();
                    String cellValue = row.getCell(11).getStringCellValue();
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(cellValue);
                    user.setRegistTime(date);
                }
                userservice.addUser(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping("regist")
    public Map<String,Object> regist(User user){
        return userservice.addUser(user);
    }
    @RequestMapping("login")
    public Map<String,Object> login(String phone,String password,String code){
        return userservice.queryByPhone(phone,password,code,null);
    }
    @RequestMapping("stats")
    public Map<String,Object> stats(){
        return userservice.stats();
    }
}
