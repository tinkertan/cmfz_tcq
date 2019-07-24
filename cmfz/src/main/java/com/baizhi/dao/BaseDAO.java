package com.baizhi.dao;



import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface BaseDAO<T> {
    /*查询所有带分页*/
    List<T> selectAll(Map map);

}
