package com.qingguatang.spider.dataobject;

import java.util.Date;

/**
 * 歌单列表
 *
 * @author zhaopei
 * @date 2018/7/6
 */
public class PlayListSongDO {

  private String id;
  private String playListId;
  private String songId;
  private Date gmtModified;
  private Date gmtCreated;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPlayListId() {
    return playListId;
  }

  public void setPlayListId(String playListId) {
    this.playListId = playListId;
  }

  public String getSongId() {
    return songId;
  }

  public void setSongId(String songId) {
    this.songId = songId;
  }

  public Date getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

  public Date getGmtCreated() {
    return gmtCreated;
  }

  public void setGmtCreated(Date gmtCreated) {
    this.gmtCreated = gmtCreated;
  }
}
