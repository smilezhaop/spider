'use strict';

var fs = require('fs');
var root = fs.absolute("./data/music");
fs.makeDirectory(root);
console.log('workingDirectory:' + root);

var page = require('webpage').create();

var playListFileNames = [];

page.open('https://music.163.com/#/song?id=35847388', function (status) {
  if (status !== 'success') {
    console.log('fall to load' + page.url);
    return;
  }
  console.log('success to load' + page.url);

  eachSongId(0);
});

page.onError = function () {
  if (message.indexOf('__getURL__') != -1) {
    var data = message.substring(10);
    var json = JSON.parse(data);
    var musicDataFile = root + fs.separator + json.id + ".json";
    fs.write(musicDataFile, data, 'W');
  }
};

/**
 * 循环监听歌单文件
 * @param time
 */
function eachSongId(time) {
  setTimeout(readPlayListFile(), time);
  eachSongId(10000);
}

/**
 * 读取文件
 */
function readPlayListFile() {
  var dataPath = fs.absolute("./data");
  if (!fs.exists(dataPath)) {
    return;
  }
  var playLists = fs.list(dataPath);
  for (var x = 0; x < playLists.length; x++) {
    var file = dataPath + fs.separator + playLists[x];
    if (playListFileNames.indexOf(file) != -1 || !fs.isFile(file)) {
      continue;
    }
    var content = fs.read(file);
    var json = JSON.parse(content);
    var songIds = [];
    for (var i = 0; i < json.songs.length; i++) {
      var songId = json.songs[i].id;
      var musicDataDile = root + fs.separator + songId + ".json";
      if (songIds.length > 0) {
        console.log(JSON.stringify(songIds));

        var evalJs = getUrl(songIds);
        page.evaluateAsync(evalJs);
        playListFileNames.push(file);
      }
    }
  }

}

/**
 * 获取歌曲的URL回调函数字符串
 * @param ids
 */
function getUrl(ids) {
  var jsonIds = JSON.stringify(ids);
  var result = function () {
    var root = window.NEJ.P("nej.j");
    var server;
    for (var n in root) {
      if (root[n].toString().indexOf('encText') != -1) {
        server = root[n];
      }
    }
    server("/api/song/enhance/player/url", {
      type: "json",
      query: {
        ids: jsongIds,
        br: 128000
      },
      onload: function (data) {
        for (var i = 0; i < data.data.length; i++) {
          var item = {
            id: data.data[i].id,
            url: data.data[i].url
          };
          console.error("__getURL__" + JSON.stringify(item));
        }
      }

    })

  };
  return result().toString().replace('jsonIds', jsonIds);
}


