package com.qingguatang.spider.service;

import com.qingguatang.spider.control.PlayListControl;
import com.qingguatang.spider.dao.PlayListDAO;
import com.qingguatang.spider.dao.PlayListSongDAO;
import com.qingguatang.spider.dao.SongDAO;
import com.qingguatang.spider.dataobject.PlayListDO;
import com.qingguatang.spider.dataobject.SongDO;
import java.io.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhaopei
 * @date 2018/7/6
 */
@Component
public class PhantomjsService {

  private static final Logger logger = LoggerFactory.getLogger(PhantomjsService.class);
  private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);

  @Autowired
  private PlayListDAO playListDAO;
  @Autowired
  private PlayListSongDAO playListSongDAO;
  @Autowired
  private SongDAO songDAO;
  @Autowired
  private PlayListControl playListControl;

  @PostConstruct
  public void init() {
    PlayListTask playListTask = new PlayListTask();
    threadPool.execute(playListTask);

    MusicTask musicTask = new MusicTask();
    threadPool.execute(musicTask);
  }


  class PlayListTask implements Runnable {

    @Override
    public void run() {
      ProcessBuilder processBuilder = new ProcessBuilder("phantomjs", "webserver.js", "8081");
      processBuilder.redirectErrorStream(true);
      try {
        Process process = processBuilder.start();
        ResultStreamHandler inputStreamHandler = new ResultStreamHandler(process.getInputStream());
        ResultStreamHandler errorStreamHandler = new ResultStreamHandler(process.getErrorStream());
        threadPool.execute(inputStreamHandler);
        threadPool.execute(errorStreamHandler);
      } catch (IOException e) {
        logger.error("", e);
      }
    }
  }

  class MusicTask implements Runnable {

    @Override
    public void run() {
      ProcessBuilder processBuilder = new ProcessBuilder("phantomjs", "music.js");
      processBuilder.redirectErrorStream(true);
      try {
        Process process = processBuilder.start();
        ResultStreamHandler inputStreamHandler = new ResultStreamHandler(process.getInputStream());
        ResultStreamHandler errorStreamHandler = new ResultStreamHandler(process.getErrorStream());
        threadPool.execute(inputStreamHandler);
        threadPool.execute(errorStreamHandler);
      } catch (IOException e) {
        logger.error("", e);
      }
    }
  }

  @Scheduled(initialDelay = 15000, fixedRate = 30000)
  public void initData() {
    File root = new File("data");
    if (!root.exists()) {
      return;
    }
    for (File file : root.listFiles()) {
      if (file.getName().endsWith(".json")) {
        try {
          Map map = objectMapper.readValue(file, Map.class);

          String playListId = map.get("id").toString();
          PlayListDO playListDO = playListDAO.get(playListId);

          if (playListDO == null) {
            playListDO.setId(playListId);
            playListDO.setTitle((String) map.get("title"));
            playListDO.setCover((String) map.get("cover"));
            playListDAO.insert(playListDO);
          }

          List<Map> songs = (List<Map>) map.get("songs");
          songs.forEach(song -> {
            String songId = song.get("id").toString();
            SongDO songDO = songDAO.selectById(songId);

            if (songDO == null) {
              songDO = new SongDO();
              songDO.setId(songId);
              songDO.setDuration((String) song.get("duration"));
              songDO.setSinger((String) song.get("singer"));
              songDO.setName((String) song.get("name"));

              playListControl.addSong(playListId, songDO);
            }
          });

        } catch (IOException e) {
          logger.error("", e);
        }
      }
    }
  }

  class ResultStreamHandler implements Runnable {

    private InputStream in;

    ResultStreamHandler(InputStream in) {
      this.in = in;
    }

    @Override
    public void run() {
      BufferedReader bufferedReader = null;
      try {
        bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
          logger.error("phantomjs:" + line);
        }
      } catch (Throwable t) {
        logger.error("", t);

      } finally {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          logger.error("", e);
        }
      }
    }
  }


}
