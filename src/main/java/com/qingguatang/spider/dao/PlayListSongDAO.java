package com.qingguatang.spider.dao;

import com.qingguatang.spider.dataobject.PlayListSongDO;
import org.apache.ibatis.annotations.Insert;

/**
 * 歌单列表
 *
 * @author zhaopei
 * @date 2018/7/6
 */
public interface PlayListSongDAO {

  @Insert("insert into play_list_song(id,play_list_id,song_id,gmt_modified,gmt_created)"
      + "values("
      + "#{id},#{playListId},#{songId},now(),now()"
      + ")")
  public int insert(PlayListSongDO playListSongDO);
}
