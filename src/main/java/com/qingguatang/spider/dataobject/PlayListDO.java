package com.qingguatang.spider.dataobject;

import java.util.Date;

/**
 * 歌单
 *
 * @author zhaopei
 * @date 2018/7/6
 */
public class PlayListDO {

  private String id;
  private String title;
  private String cover;
  private Date gmtModified;
  private Date gmtCreated;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
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
