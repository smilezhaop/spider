package com.qingguatang.spider.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingguatang.spider.service.DownloadService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhaopei
 * @date 2018/7/6
 */
@Component
public class DownloadServiceImpl implements DownloadService {

  private static final Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);
  private static OkHttpClient client = new OkHttpClient();
  private static ObjectMapper objectMapper = new ObjectMapper();
  private static Executor executor = Executors.newFixedThreadPool(5);


  @Scheduled(initialDelay = 15000, fixedRate = 30000)
  @PostConstruct
  public void init() {
    File root = new File("data", "music");
    if (!root.exists()) {
      return;
    }

    for (File file : root.listFiles()) {
      try {
        handlerDownload(file);
      } catch (Exception e) {
        logger.error("the music file:" + file.getName() + "download error", e);
      }
    }
  }

  private void handlerDownload(File file) throws IOException {
    Map map = objectMapper.readValue(file, Map.class);
    Integer id = (Integer) map.get("id");
    String url = (String) map.get("url");
    String fileName = id + ".mp3";

    if (StringUtils.isBlank(url)) {
      return;
    }

    File mp3 = new File(getRoot(), fileName);
    if (mp3.exists()) {
      return;
    }

    executor.execute(new Runnable() {
      @Override
      public void run() {
        download(fileName, url);
      }
    });

  }

  @Override
  public void download(String fileName, String url) {
    Request request = new Request.Builder().url(url).build();
    try {
      Response response = client.newCall(request).execute();
      FileOutputStream outputStream = new FileOutputStream(new File(getRoot(), fileName));
      IOUtils.write(response.body().bytes(), outputStream);
    } catch (Exception e) {
      logger.error("download" + fileName + "error:", e);
    }
  }

  private File getRoot() {
    File root = new File("musicdata");
    if (!root.exists()) {
      root.mkdir();
    }
    return root;
  }
}
