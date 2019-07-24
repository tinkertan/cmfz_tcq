package com.baizhi.controller;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        return guruService.queryAll(page,rows);
    }
    @RequestMapping("queryAllNoPage")
    public List<Guru> queryAllNoPage(){
        return guruService.queryAllNoPage();
    }

    @RequestMapping("edit")
    public String edit(Guru guru,String[] ids,String oper){
        if("add".equals(oper)){
            return guruService.addGuru(guru);
        }else{
            for(String id:ids){
                Guru guru1 = guruService.queryById(id);
                if("正常".equals(guru1.getStatus())){
                    guru1.setStatus("冻结");
                }else {
                    guru1.setStatus("正常");
                }
                guruService.modifyGuru(guru1);
            }
        }
        return null;
    }
    @RequestMapping("upload")
    public void upload(String id, MultipartFile profile, HttpSession session){
        String originalFilename = profile.getOriginalFilename();
        try {
            if(!(originalFilename==null||"".equals(originalFilename))){
                long time = new Date().getTime();
                String realPath = session.getServletContext().getRealPath("/uploadProfile");
                profile.transferTo(new File(realPath,time+originalFilename));
                Guru guru = new Guru();
                guru.setId(id);
                guru.setProfile(time+originalFilename);
                guruService.modifyGuru(guru);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
