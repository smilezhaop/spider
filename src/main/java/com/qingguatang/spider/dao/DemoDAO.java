package com.qingguatang.spider.dao;

import com.qingguatang.spider.dataobject.DemoDO;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zhaopei
 * @date 2018/7/3
 */
@Mapper
public interface DemoDAO {

  @Select("select id,name,gmt_modified as gmtModified,gmt_created as gmtCreated from demo")
  public List<DemoDO> selectAll();

  @Insert("insert into demo(id,name,gmt_modified,gmt_created)"
      + "values(" + "#{id},#{name},now(),now()" + ")")
  public int insert(DemoDO demoDO);
}
