package com.qingguatang.spider.control;

import com.qingguatang.spider.dao.DemoDAO;
import com.qingguatang.spider.dataobject.DemoDO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaopei
 * @date 2018/7/3
 */
@Controller
public class DemoControl {

  @Autowired
  private DemoDAO demoDAO;

  @RequestMapping(value = "/demo/queryAll")
  @ResponseBody
  public List<DemoDO> queryAll() {
    return demoDAO.selectAll();

  }

  @RequestMapping(value = "/demo/insert")
  @ResponseBody
  public DemoDO insert(DemoDO demoDO) {
    demoDO.setId(UUID.randomUUID().toString().replaceAll("_", ""));
    demoDAO.insert(demoDO);
    return demoDO;
  }
}
