package com.qingguatang.spider.control;

import com.qingguatang.spider.dao.PlayListDAO;
import com.qingguatang.spider.dao.PlayListSongDAO;
import com.qingguatang.spider.dao.SongDAO;
import com.qingguatang.spider.dataobject.PlayListDO;
import com.qingguatang.spider.dataobject.PlayListSongDO;
import com.qingguatang.spider.dataobject.SongDO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaopei
 * @date 2018/7/6
 */
@Controller
public class PlayListControl {

  @Autowired
  private SongDAO songDAO;

  @Autowired
  private PlayListDAO playListDAO;

  @Autowired
  private PlayListSongDAO playListSongDAO;

  @RequestMapping(value = "/playlist/query")
  @ResponseBody
  public List<PlayListDO> query() {
    return playListDAO.selectAll();
  }

  @RequestMapping(value = "/playlist/add")
  @ResponseBody
  public boolean add(PlayListDO playListDO) {
    return playListDAO.insert(playListDO) > 0;
  }

  @RequestMapping(value = "/playlist/addsong")
  @ResponseBody
  public boolean addSong(@RequestParam("playListId") String playListId, SongDO songDO) {
    songDAO.insert(songDO);

    PlayListSongDO playListSongDO = new PlayListSongDO();
    playListSongDO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    playListSongDO.setPlayListId(playListId);
    playListSongDO.setSongId(songDO.getId());
    return playListSongDAO.insert(playListSongDO) > 0;
  }

  @RequestMapping(value = "/playlisr/querysongs")
  @ResponseBody
  public List<SongDO> querySongs(@RequestParam("playListId") String playListId) {
    return songDAO.selectByPlayListId(playListId);
  }
}
