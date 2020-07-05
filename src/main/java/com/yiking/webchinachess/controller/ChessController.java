package com.yiking.webchinachess.controller;

import com.yiking.webchinachess.bean.ChessFight;
import com.yiking.webchinachess.service.ChessFightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChessController {
    // private List<ChessFight> waitFight;
    @Autowired
    public ChessFightService chessFightService;

    /**
     * @param userid
     * @return
     * @throws InterruptedException 在黑色方载入棋局时等待红色方下棋，
     *                              此时黑色方:step=0,红色方：step=0,
     *                              在红色方下棋后,红色方：step=1
     *                              此时红色方棋局已上传，
     *                              黑色方可从数据库载入棋局
     *                              <p>
     *                              此方法只在刚开局时黑色方等待红色方下棋时使用
     */
    @RequestMapping(value = "sblack",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("黑棋刚开始进入棋局等待红色方下棋后返回数据")
    public List<Object> handle04(Integer userid) throws InterruptedException {
        //初始化步数
        Integer step = 0;
        //棋局 以string保存类似：C0,M0,X0,S0,J0,S1,X1,M1,C1,,,,,,,,,,,P0,,,,,,P1,,Z0,,Z1,,Z2,,Z3,,Z4,,,,,,,,,,,,,,,,,,,z0,,z1,,z2,,z3,,z4,,p0,,,p1,,,,,,,,,,,,,,c0,m0,x0,s0,j0,s1,x1,m1,c1
        String chess = null;
        for (int i = 0; i < 240; i++) {
            //查询自己的信息包括（自己的 step步数，chessstep棋局）
            ChessFight myself = chessFightService.selectMyself(userid);
            ChessFight rival = chessFightService.selectRival(myself.getSecondId());
            if (myself.getStep() == rival.getStep() && myself.getOppotunity() == 1) {
                chess = rival.getChess();
                step = rival.getStep();
                break;
            } else if (myself.getStep() + 1 == rival.getStep() && myself.getOppotunity() == -1) {
                chess = rival.getChess();
                step = rival.getStep();
                break;
            }
            if (rival.getChess().equals("giveup")) {
                chess = rival.getChess();
                break;
            }
            if (myself.getFlag() == 0) {
                break;
            }
            if (i == 239) {
                chessFightService.updateWinNum(userid);
                chessFightService.updateLossNum(chessFightService.selectMyself(userid).getSecondId());
            }
            Thread.sleep(500);
        }
        List<Object> list = new ArrayList<>();
        list.add(chess);
        list.add(step);
        return list;
    }

    /**
     * 在玩家下棋后从数据库中搜索对方是否下完棋
     * 若玩家为红色方则当myself.step=rival.step时可将rival的chessStep响应给玩家棋局；
     * 若玩家为黑色方则当myself.step+1=rival.step时可将rival的chessStep响应给玩家棋局；
     */
    @RequestMapping(value = "sred",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("下棋后向服务器请求对方的下棋数据")
    public List<Object> handle05(String chess, Integer userid) throws InterruptedException {

        Integer step = 0;
        //棋局 以string保存类似：C0,M0,X0,S0,J0,S1,X1,M1,C1,,,,,,,,,,,P0,,,,,,P1,,Z0,,Z1,,Z2,,Z3,,Z4,,,,,,,,,,,,,,,,,,,z0,,z1,,z2,,z3,,z4,,p0,,,p1,,,,,,,,,,,,,,c0,m0,x0,s0,j0,s1,x1,m1,c1
        String chessStep = null;
        chessFightService.updateF(chess, userid);
        for (int i = 0; i < 240; i++) {
            ChessFight myself = chessFightService.selectMyself(userid);
            ChessFight rival = chessFightService.selectRival(myself.getSecondId());
            Thread.sleep(500);
            //若玩家为红色方则当myself.step=rival.step时可将rival的chessStep响应给玩家棋局；myself.getOppotunity() == 1则玩家为红色方
            if (myself.getStep() == rival.getStep() && myself.getOppotunity() == 1) {
                chessStep = rival.getChess();
                step = rival.getStep();
                break;
            }
            //若玩家为黑色方则当myself.step+1=rival.step时可将rival的chessStep响应给玩家棋局；myself.getOppotunity() == -1则玩家为黑色方
            if (myself.getStep() + 1 == rival.getStep() && myself.getOppotunity() == -1) {
                chessStep = rival.getChess();
                step = rival.getStep();
                break;
            }
            if (rival.getChess().equals("giveup")){
                chessStep = rival.getChess();
                break;
            }
            if (myself.getFlag() == 0) {
                break;
            }
            //此时响应超时，说明对手断线或者逃跑，将己方总场次、胜场+1， 将对方总场次+1
            if (i == 239) {
                chessFightService.updateWinNum(userid);
                chessFightService.updateLossNum(chessFightService.selectMyself(userid).getSecondId());
            }
        }
//将数据响应给页面
        List<Object> list = new ArrayList<>();
        list.add(chessStep);
        list.add(step);

        return list;
    }

    /**
     * @param firstid
     * @param username
     * @return
     * @throws InterruptedException 在玩家第一次进行在线对战时加入对战表，并为玩家寻找对手
     *                              若不是第一次进行在线对战时，则进行一定操作后为玩家寻找对手
     */
    @ResponseBody
    @ApiOperation("加入对战系统，在线寻找对手")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public List<Object> addFighter(Integer firstid, String username) throws InterruptedException {
        ChessFight fighter = chessFightService.selectMyself(firstid);
        //判断是不是第一次进行在线对战
        if (fighter == null) {
            //判断当前对战表中是否有人正在等待对战
            List<ChessFight> list = chessFightService.selectWaitting();
            if (list.isEmpty()) {
                //如果对战表中无人等待在将自己加入等待，即将自己的secondid设为21
                chessFightService.addFighter(firstid, username, 21, 0, 1);
            } else {
                //如果有人等待则与之对战，对方执红，己方执黑
                chessFightService.addFighter(firstid, username, list.get(0).getFirstId(), 0, -1);
                chessFightService.updateFighter(firstid, 1, 1, list.get(0).getFirstId());
            }

        } else if (fighter != null) {
            //若不是第一次进行在线对战将上次对战的双方的secondid设为22方便本次对战
            chessFightService.updateFighter(22, 1, 0, fighter.getSecondId());
            chessFightService.updateFighter(22, 1, 0, firstid);
            //判断当前对战表中是否有人正在等待对战
            List<ChessFight> list = chessFightService.selectWaitting();
            if (list.isEmpty()) {
                //如果对战表中无人等待在将自己加入等待，即将自己的secondid设为21
                chessFightService.updateFighter(21, 1, 0, firstid);

            } else {
                //如果有人等待则与之对战，对方执红，己方执黑
                chessFightService.updateFighter(list.get(0).getFirstId(), -1, 1, firstid);
                chessFightService.updateFighter(firstid, 1, 1, list.get(0).getFirstId());
            }

        }
        //判断是否有人加入对战，没有则自己继续等待
        for (int i = 0; i < 239; i++) {
            ChessFight fighter2 = chessFightService.selectMyself(firstid);
            //有人加入则与之对战，自己停止等待 对方执黑，己方执红
            if (fighter2.getSecondId() != 21 && fighter2.getSecondId() != 22 && fighter2.getFlag() == 1) {
                break;
            }
            Thread.sleep(500);
        }
        Integer winsumM = 0;//己方的获胜场次
        Integer totalM = 0;//己方的总场次
        Integer winsumR = 0;//对方的获胜场次
        Integer totalR = 0;//对方总场次
        String nameM = null;//己方用户名
        String nameR = null;//对方用户名
        ChessFight myself = chessFightService.selectMyself(firstid);
        ChessFight rival = chessFightService.selectRival(myself.getSecondId());
        winsumM = myself.getWinNum();
        totalM = myself.getTotalNum();
        winsumR = rival.getWinNum();
        totalR = rival.getTotalNum();
        nameM = myself.getUsername();
        nameR = rival.getUsername();

        List<Object> res = new ArrayList<Object>();
        res.add("ok");
        res.add(chessFightService.selectMyself(myself.getSecondId()).getUsername());
        res.add(myself.getOppotunity());
        res.add(winsumM);
        res.add(totalM);
        if (totalM == 0) {
            res.add("0.00");
        } else {
            res.add(String.format("%.2f", 1.0 * winsumM / totalM * 100));
        }
        res.add(winsumR);
        res.add(totalR);
        if (totalR == 0) {
            res.add("0.00");
        } else {
            res.add(String.format("%.2f", 1.0 * winsumR / totalR * 100));
        }
        res.add(nameM);
        res.add(nameR);
        return res;
    }

    /**
     * @param id
     * @return 己方在棋局上获胜，将己方总场次、胜场+1， 将对方总场次+1
     */
    @ApiOperation("更新胜负数据")
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/win",method = RequestMethod.POST)
    public String addwinner(Integer id) {
        chessFightService.updateWinNum(id);
        chessFightService.updateLossNum(chessFightService.selectMyself(id).getSecondId());
        return "ok";
    }
    @ApiOperation("返回首页")
    @RequestMapping(value = "/flee",method = RequestMethod.POST)
    public String flee(Integer firstid, String username, Model model) {
        chessFightService.updateFighter(22, 1, 0, firstid);
        model.addAttribute("username", username);
        model.addAttribute("userid", firstid);
        return "onlineindex.html";
    }
    @ApiOperation("认输")
    @Transactional
    @RequestMapping(value = "/rensu",method = RequestMethod.POST)
    @ResponseBody
    public String rensu(Integer firstid) {
        if (chessFightService.selectMyself(firstid).getFlag() != 0) {
            chessFightService.updateWinNum(chessFightService.selectMyself(firstid).getSecondId());
            chessFightService.updateLossNum(firstid);
            chessFightService.updateF("giveup", firstid);
        }
        return "ok";
    }
    @GetMapping("/hh")
    public ChessFight fight(){
        return  new ChessFight();
    }
}
