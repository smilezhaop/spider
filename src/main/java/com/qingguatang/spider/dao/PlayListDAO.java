package com.qingguatang.spider.dao;

import com.qingguatang.spider.dataobject.PlayListDO;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 歌单接口
 *
 * @author zhaopei
 * @date 2018/7/6
 */
@Mapper
public interface PlayListDAO {

  @Insert("insert into play_list(id,title,cover,gmt_modified,gmt_created)"
      + "values("
      + "#{id},#{title},#{cover},now(),now()"
      + ")")
  public int insert(PlayListDO playListDO);

  @Select("select id,title,cover,gmt_modified as gmtModified,gmt_created as gmtCreated from play_list")
  public List<PlayListDO> selectAll();

  @Select(
      "select id,title,cover,gmt_modified as gmtModified,gmt_created as gmtCreated from play_list"
          + "where id = {id}")
  public PlayListDO get(@Param("id") String id);
}
