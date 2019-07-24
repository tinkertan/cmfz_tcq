package com.baizhi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MyUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("MyUtil initialize applicationContext.....");
        this.applicationContext = applicationContext;
    }


    public Map<String,Object> queryAllByPage(Integer pageNum,Integer pageSize,Class dao,String albumId){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> map1 = new HashMap<>();
        try {
            map1.put("startIndex",(pageNum-1)*pageSize);
            map1.put("pageSize",pageSize);
            map1.put("albumId",albumId);
            Object daoInFactory = getBean(dao);
            Method selectAllMethod = dao.getMethod("selectAll", Map.class);

            List list = (List)selectAllMethod.invoke(daoInFactory, map1);
            Integer count = 0;
            if(albumId!=null){
                Method selectCountMethod = dao.getMethod("selectCount",String.class);
                count = (Integer) selectCountMethod.invoke(daoInFactory,albumId);
            }else {
                Method selectCountMethod = dao.getMethod("selectCount");
                count = (Integer) selectCountMethod.invoke(daoInFactory);
            }
            log.info("myutil count {}  pageSize {}",count,pageSize);
            Integer total = (count%pageSize==0)?(count/pageSize):(count/pageSize+1);
            map.put("countNum",count);
            map.put("pageNum",pageNum);
            map.put("pageTotal",total);
            map.put("list",list);
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return map;
    }

    public Map<String,Object> queryAllForJqgrid(Integer pageNum,Integer pageSize,Class dao,String albumId){
        Map<String, Object> map = queryAllByPage(pageNum, pageSize, dao,albumId);
        map.put("records",map.get("countNum"));
        map.put("page",map.get("pageNum"));
        map.put("total",map.get("pageTotal"));
        map.put("rows",map.get("list"));
        map.remove("countNum");
        map.remove("pageNum");
        map.remove("pageTotal");
        map.remove("list");
        log.info("myUtil return map {}",map);
        return map;
    }



    /**
     * 这是删除的方法
     * @param dao  dao所对应的类对象
     * @param entityId 需要删除的实体的id
     * @param entity 需要删除的实体类的类对象
     * @param getPath 如果有需要删除的文件的话，或者这个文件数据库的字段的方法名
     * @param dirName 文件存放的路径
     * @param session 获取文件路径所需要的session
     * @return 是否删除成功
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object remove(Class dao,String entityId,Class entity,String getPath,String dirName,HttpSession session){
        Object invoke=null;
        try {
           Object daoInFactory = getBean(dao);
           /*执行根据id查询的方法*/
           Method selectById = dao.getMethod("selectById", "".getClass());
           Object selectedEntity = selectById.invoke(daoInFactory, entityId);
           /*执行删除方法*/
           Method deleteById = dao.getMethod("deleteById", "".getClass());
           invoke = deleteById.invoke(daoInFactory, entityId);
           /*获取上传文件的路径*/
           Method getPathMethod = entity.getMethod(getPath);
           String path = (String) getPathMethod.invoke(selectedEntity);
           /*删除文件*/
           deleteFile(dirName,path,session);
       } catch (NoSuchMethodException e) {
           e.printStackTrace();
            throw new RuntimeException(e.getMessage());
       } catch (InvocationTargetException e) {
           e.printStackTrace();
            throw new RuntimeException(e.getMessage());
       } catch (IllegalAccessException e) {
           e.printStackTrace();
            throw new RuntimeException(e.getMessage());
       }
        return invoke;
    }


    /**
     * 这是获取工厂中bean的方法
     * @param clazz 需要获取的bean的类对象
     * @param <T> 需要获取的bean的类型
     * @return 从工厂中根据类型获取的bean
     */
    public  <T> T getBean(Class<T> clazz){
        return this.applicationContext.getBean(clazz);
    }

    /**
     * 这是删除文件的方法
     * */
    public Boolean deleteFile(String dir, String fileName, HttpSession session){
        try{
            if(session==null)throw new RuntimeException("httpSession==null");
            String realPath = session.getServletContext().getRealPath("/"+dir);
            String imgPath = realPath+"\\"+fileName;
            log.info("myUtil deleteFile filePath {}",imgPath);
            new File(imgPath).delete();
        }catch (Exception e){
            e.printStackTrace();
            /*throw e;*/
        }
        return true;
    }
}
