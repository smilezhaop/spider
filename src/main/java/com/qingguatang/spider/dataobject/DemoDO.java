package com.qingguatang.spider.dataobject;

import java.util.Date;

/**
 * @author zhaopei
 * @date 2018/7/3
 */
public class DemoDO {

  private String id;
  private String name;
  private Date gmtModified;
  private Date gmtCreated;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
