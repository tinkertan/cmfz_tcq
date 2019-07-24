package com.baizhi.cache;

import com.baizhi.util.SerializeUtils;
import com.baizhi.util.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MyCache implements Cache {
    private String id;

    public MyCache(String id){
        this.id = id;
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        System.out.println("添加至缓存");
        stringRedisTemplate.opsForHash().put(id,key.toString(), SerializeUtils.serialize(value));
    }

    @Override
    public Object getObject(Object key) {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        String val = (String) stringRedisTemplate.opsForHash().get(id, key.toString());
        if(val==null){
            System.out.println("缓存没有");
            return null;
        }else {
            System.out.println("缓存取到了");
            Object o = SerializeUtils.serializeToObject(val);
            return o;
        }
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        System.out.println("修改，清空");
        stringRedisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
