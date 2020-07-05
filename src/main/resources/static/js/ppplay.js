var play = play || {};

play.init2 = function (depth, map) {
    var depth = depth || 3;
    console.log(com.oppo);
    play.step = 0;
    play.my = com.oppo;
    // 玩家方
    if (play.my == 1) {
        var map = map || com.initMap;
    } else {
        var map = map || com.initMap2;
    }
    play.nowMap = map;
    play.map = com.arr2Clone(map); // 初始化棋盘
    play.nowManKey = false; // 现在要操作的棋子
    play.pace = [];
    play.isPlay = true;// 记录每一步
    if (com.oppo == -1) {
        play.isOffensive = false;
        // play.sBlack();
        console.log("submit");
    }
    var timer = 120;
    var flag = true;
    var flagb = true;
    var flagr = true;
    var flagw = true;
    setInterval(function () {
        if (play.my == 1 || play.my == -1) {
            if (timer < 1) {
                play.my = 13;
            }
            if (flagr && play.my == 1) {
                document.getElementById("timer").style.color = "red";
                flagr = false
            }
            if (!flagb && play.my == -1) {
                document.getElementById("timer").style.color = "black";
                flagb = true;
            }
            if (!flag) {
                timer = 120;
                flag = true;
            }
            if (timer < 31) {
                document.getElementById("timer").innerText = "警告！！！剩余时间："
                    + timer-- + "s";
            } else
                document.getElementById("timer").innerText = "下棋方剩余时间："
                    + timer-- + "s";

        } else {
            if (timer < 1) {
                play.my = 13;
            }
            if (play.my == 12) {
                document.getElementById("timer").innerText = "GAME OVER ! ! !";

            } else if (play.my == 13 && flagw) {
                if (document.getElementById("timer").style.color == "red") {
                    alert("黑色方赢了 ！ ！ ！");
                }

                if (document.getElementById("timer").style.color == "black") {
                    alert("红色方赢了 ！ ！ ！");
                }
                document.getElementById("timer").innerText = "GAME OVER ! ! !";
                flagw = false;
            } else if (flagw) {
                if (flag) {
                    timer = 120;
                    flag = false;
                }
                if (flagb) {
                    document.getElementById("timer").style.color = "red";
                    flagb = false;
                }
                if (!flagr) {
                    document.getElementById("timer").style.color = "black";
                    flagr = true
                }
                if (timer < 31) {
                    document.getElementById("timer").innerText = "警告！！！剩余时间："
                        + timer-- + "s";
                } else
                    document.getElementById("timer").innerText = "下棋方剩余时间："
                        + timer-- + "s";
            }
        }
    }, 1000);
    if (play.my == 1) {
        document.getElementById("mychess").innerText = "玩家方：【红色】";
        document.getElementById("mychess").style.color = "red";
        document.getElementById("currchess").innerText = "当前下棋方：【红色】";
        document.getElementById("currchess").style.color = "red";

    } else {
        document.getElementById("mychess").innerText = "玩家方：【黑色】";
        document.getElementById("mychess").style.color = "black";
    }
    console.log("Map")
    console.log(play.map);
    // 是否能走棋

    play.bylaw = com.bylaw2;
    play.show = com.show;
    play.showPane = com.showPane;
    // 是否先手
    play.depth = depth; // 搜索深度
    play.isFoul = false; // 是否犯规长将
    com.pane.isShow = false; // 隐藏方块

    // 清除所有旗子
    play.mans = com.mans = {};

    // 这么搞有点2，以后说不定出啥问题，先放着记着以后改
    com.childList.length = 3
    com.createMans2(map) // 生成棋子
    com.bg.show();

    // 初始化棋子
    for (var i = 0; i < play.map.length; i++) {
        for (var n = 0; n < play.map[i].length; n++) {
            var key = play.map[i][n];
            if (key) {
                com.mans[key].x = n;
                com.mans[key].y = i;
                com.mans[key].isShow = true;
            }
        }
    }
    play.show();
    play.start();
}
/*
 * play.sBlack = function() { var chessStr;
 * 
 * myajax=$.post({ url : "sblack", data : { userid : $("#userid").val() },//
 * success : function(data) { while(data == null) {
 * console.log("++++++++++++++++++++"); } chessStr = data[0];
 * console.log(chessStr); } }) return chessStr; }
 */
play.sRed = function () {
    var chessStr;

    myajax = $.post({
        url: "sred",
        data: {
            chess: play.map,
            userid: $("#userid").val()
        },//
        success: function (data) {
            while (data == null) {
                console.log("++++++++++++++++++++");
            }
            chessStr = data[0];
            console.log(chessStr);
        }
    })
    return chessStr;
}

function sleep(n) {
    var start = new Date().getTime();
    while (true) {
        if (new Date().getTime() - start > n) {
            break;
        }
    }
}

play.start = function () {
    if (play.my == -1 && play.step == 0) {
        play.init3(3);
    } else {
        console.log("ks4")
        com.canvas.addEventListener("click", play.clickCanvasP1);
    }
}
play.init3 = function (depth, map) {
    var chessStr;
    var temp = play.my;
    if (play.my == 1) {

        document.getElementById("currchess").innerText = "当前下棋方：【黑色】";
        document.getElementById("currchess").style.color = "black";
    } else {

        document.getElementById("currchess").innerText = "当前下棋方：【红色】";
        document.getElementById("currchess").style.color = "red";
    }
    play.my = 11;
    myajax = $.post({
        url: "sblack",
        data: {
            userid: $("#userid").val()
        },//
        success: function (data) {

            chessStr = data[0];
            console.log(chessStr);
            var str = chessStr.split(",");
            console.log(str.length);
            var count = 0;
            var map = [];
            if (temp == -1) {
                for (var i = 9; i >= 0; i--) {
                    var map2 = [];
                    for (var j = 0; j < 9; j++) {
                        map2[j] = str[count++];

                    }
                    map[i] = map2;
                    console.log(map[i]);
                }
            } else {
                for (var i = 0; i < 10; i++) {
                    var map2 = [];
                    for (var j = 0; j < 9; j++) {
                        map2[j] = str[count++];

                    }
                    map[i] = map2;
                    console.log(map[i]);
                }
            }

            console.log(chessStr);
            console.log(map);
            console.log(map.length);
            var depth = depth || 3;
            // play.my = 1; // 玩家方
            play.nowMap = map;
            play.map = com.arr2Clone(map); // 初始化棋盘
            play.nowManKey = false; // 现在要操作的棋子
            play.pace = []; // 记录每一步
            play.isPlay = true; // 是否能走棋

            play.bylaw = com.bylaw2;
            play.show = com.show;
            play.showPane = com.showPane;
            // play.isOffensive = true; // 是否先手
            // play.depth = depth; // 搜索深度
            play.isFoul = false; // 是否犯规长将
            com.pane.isShow = false; // 隐藏方块

            // 清除所有旗子
            play.mans = com.mans = {};

            // 这么搞有点2，以后说不定出啥问题，先放着记着以后改
            com.childList.length = 3;
            com.createMans2(map) // 生成棋子
            com.bg.show();

            // 初始化棋子
            for (var i = 0; i < play.map.length; i++) {
                for (var n = 0; n < play.map[i].length; n++) {
                    var key = play.map[i][n];
                    if (key) {
                        com.mans[key].x = n;
                        com.mans[key].y = i;
                        com.mans[key].isShow = true;
                    }
                }
            }
            play.show();

            // 绑定点击事件
            play.my = com.oppo;
            if (play.my == -1) {
                //console.log("+++++++++++++++++++yiking++++++++++")
                document.getElementById("currchess").innerText = "当前下棋方：【黑色】";
                document.getElementById("currchess").style.color = "black";
            } else {
                //console.log("+++++++++++++++++++yiking++++++++++")
                document.getElementById("currchess").innerText = "当前下棋方：【红色】";
                document.getElementById("currchess").style.color = "red";
            }
            // com.get("selectAudio").play();
            com.get("clickAudio").play();
            com.canvas.addEventListener("click", play.clickCanvasP1);
        }

    })

}
play.Pcommit = function () {
    var chessStr;
    var temp = play.my;
    if (play.my == 1) {

        document.getElementById("currchess").innerText = "当前下棋方：【黑色】";
        document.getElementById("currchess").style.color = "black";
    } else {

        document.getElementById("currchess").innerText = "当前下棋方：【红色】";
        document.getElementById("currchess").style.color = "red";
    }
    play.my = 11;
    myajax = $.post({
        url: "sred",
        data: {
            chess: play.map.toString(),
            userid: $("#userid").val()
        },//
        success: function (data) {
            chessStr = data[0];

            console.log(chessStr);
            if (chessStr == "giveup") {
                alert("对方认输了");
                play.my = 12;
            }
            var str = chessStr.split(",");
            console.log(str.length);
            var count = 0;
            var map = [];
            var rfalg = false;
            var bfalg = false;

            for (var i = 9; i >= 0; i--) {
                var map2 = [];
                for (var j = 0; j < 9; j++) {
                    map2[j] = str[count++];

                }
                if (map2.indexOf("j0") != -1) {
                    rfalg = true;
                }
                if (map2.indexOf("J0") != -1) {
                    bfalg = true;
                }
                map[i] = map2;
                console.log(map[i]);
            }


            console.log(chessStr);
            console.log(map);
            console.log(map.length);
            var depth = depth || 3;
            // play.my = 1; // 玩家方
            play.nowMap = map;
            play.map = com.arr2Clone(map); // 初始化棋盘
            play.nowManKey = false; // 现在要操作的棋子
            play.pace = []; // 记录每一步
            play.isPlay = true; // 是否能走棋

            play.bylaw = com.bylaw2;
            play.show = com.show;
            play.showPane = com.showPane;
            // play.isOffensive = true; // 是否先手
            // play.depth = depth; // 搜索深度
            play.isFoul = false; // 是否犯规长将
            com.pane.isShow = false; // 隐藏方块

            // 清除所有旗子
            play.mans = com.mans = {};

            // 这么搞有点2，以后说不定出啥问题，先放着记着以后改
            com.childList.length = 3;
            com.createMans2(map) // 生成棋子
            com.bg.show();

            // 初始化棋子
            for (var i = 0; i < play.map.length; i++) {
                for (var n = 0; n < play.map[i].length; n++) {
                    var key = play.map[i][n];
                    if (key) {
                        com.mans[key].x = n;
                        com.mans[key].y = i;
                        com.mans[key].isShow = true;
                    }
                }
            }
            play.show();
            play.my = com.oppo;
            if (com.oppo == 1 && !rfalg) {
                alert("你输了！");
                play.my = 12;
            }
            if (com.oppo == -1 && !bfalg) {
                alert("你输了！");
                play.my = 12;
            }
            if (play.my == -1) {
                //console.log("+++++++++++++++++++yiking++++++++++")
                document.getElementById("currchess").innerText = "当前下棋方：【黑色】";
                document.getElementById("currchess").style.color = "black";
            } else {
                //console.log("+++++++++++++++++++yiking++++++++++")
                document.getElementById("currchess").innerText = "当前下棋方：【红色】";
                document.getElementById("currchess").style.color = "red";
            }
            // com.get("selectAudio").play();
            // com.get("clickAudio").play();
            com.get("clickAudio").play();
            com.canvas.addEventListener("click", play.clickCanvasP1);
        }

    })
}

// 点击棋盘事件
play.clickCanvasP1 = function (e) {
    if (!play.isPlay)
        return false;
    var key = play.getClickMan(e);
    var point = play.getClickPoint(e);

    var x = point.x;
    var y = point.y;
    // 绑定点击事件

    if (key) {
        play.P1clickMan(key, x, y);
    } else {
        play.P1clickPoint(x, y);
    }

    play.isFoul = play.checkFoul(); // 检测是不是长将
}
// 点击棋子，两种情况，选中或者吃子
play.P1clickMan = function (key, x, y) {
    if (play.my == com.oppo) {
        var man = com.mans[key];
        // 吃子
        if (play.nowManKey && play.nowManKey != key
            && man.my != com.mans[play.nowManKey].my) {
            // man为被吃掉的棋子
            if (play.indexOfPs(com.mans[play.nowManKey].ps, [x, y])) {
                man.isShow = false;
                var pace = com.mans[play.nowManKey].x + ""
                    + com.mans[play.nowManKey].y
                // z(bill.createMove(play.map,man.x,man.y,x,y))
                delete play.map[com.mans[play.nowManKey].y][com.mans[play.nowManKey].x];
                play.map[y][x] = play.nowManKey;
                com.showPane(com.mans[play.nowManKey].x,
                    com.mans[play.nowManKey].y, x, y)
                com.mans[play.nowManKey].x = x;
                com.mans[play.nowManKey].y = y;
                com.mans[play.nowManKey].alpha = 1

                play.pace.push(pace + x + y);
                play.nowManKey = false;
                com.pane.isShow = false;
                com.dot.dots = [];
                com.show()
                com.get("clickAudio").play();
                play.Pcommit();
                // play.P2Play();
                if (key == "j0")
                    play.showWin2(-1);
                if (key == "J0")
                    play.showWin2(1);
            }

            // 选中棋子
        } else {
            if (man.my === com.oppo) {
                if (com.mans[play.nowManKey])
                    com.mans[play.nowManKey].alpha = 1;
                man.alpha = 0.8;
                com.pane.isShow = false;
                play.nowManKey = key;
                com.mans[key].ps = com.mans[key].bl(); // 获得所有能着点
                com.dot.dots = com.mans[key].ps
                com.show();
                // com.get("selectAudio").start(0);
                com.get("selectAudio").play();

            }
        }

    }
}

play.P1clickPoint = function (x, y) {
    if (play.my == com.oppo) {
        var key = play.nowManKey;
        var man = com.mans[key];
        if (play.nowManKey) {
            if (play.indexOfPs(com.mans[key].ps, [x, y])) {
                var pace = man.x + "" + man.y
                // z(bill.createMove(play.map,man.x,man.y,x,y))
                delete play.map[man.y][man.x];
                play.map[y][x] = key;
                com.showPane(man.x, man.y, x, y)
                man.x = x;
                man.y = y;
                man.alpha = 1;
                play.pace.push(pace + x + y);
                play.nowManKey = false;
                com.dot.dots = [];
                com.show();
                com.get("clickAudio").play();
                play.Pcommit();
                // play.init3(3);
            } else {
                // alert("不能这么走哦！")
            }
        }
    }
}

// 检查是否长将
play.checkFoul = function () {
    var p = play.pace;
    var len = parseInt(p.length, 10);
    if (len > 11 && p[len - 1] == p[len - 5] && p[len - 5] == p[len - 9]) {
        return p[len - 4].split("");
    }
    return false;
}

play.indexOfPs = function (ps, xy) {
    for (var i = 0; i < ps.length; i++) {
        if (ps[i][0] == xy[0] && ps[i][1] == xy[1])
            return true;
    }
    return false;

}

// 获得点击的着点
play.getClickPoint = function (e) {
    var domXY = com.getDomXY(com.canvas);
    var x = Math.round((e.pageX - domXY.x - com.pointStartX - 20) / com.spaceX)
    var y = Math.round((e.pageY - domXY.y - com.pointStartY - 20) / com.spaceY)
    return {
        "x": x,
        "y": y
    }
}

// 获得棋子
play.getClickMan = function (e) {
    var clickXY = play.getClickPoint(e);
    var x = clickXY.x;
    var y = clickXY.y;
    if (x < 0 || x > 8 || y < 0 || y > 9)
        return false;
    return (play.map[y][x] && play.map[y][x] != "0") ? play.map[y][x] : false;
}

play.showWin2 = function (my) {
    play.isPlay = false;
    if (my === com.oppo) {
        myajax = $.post({
            url: "win",
            data: {
                id: $("#userid").val()
            },//
            success: function (data) {
            }
        })
        alert("你赢了！");
        play.my = 12;
    } else {
        alert("你输了！");
        play.my = 12;
    }
}