package com.yiking.webchinachess.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel("玩家实体类")
public class ChessFight implements Serializable {
    @ApiModelProperty("玩家id")
    private Integer id;
    @ApiModelProperty("玩家用户名")
    private String username;
    @ApiModelProperty("玩家用户id")
    private Integer firstId;
    @ApiModelProperty("玩家对手id")
    private Integer secondId;
    @ApiModelProperty("玩家下棋步数")
    private Integer step;
    @ApiModelProperty("玩家下棋步骤")
    private String chess;
    @ApiModelProperty("玩家下棋方（1为红色，0黑色）")
    private Integer oppotunity;
    @ApiModelProperty("玩家胜场数")
    private Integer winNum;
    @ApiModelProperty("玩家总场数")
    private Integer totalNum;
    @ApiModelProperty("当前玩家是否还在下棋")
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }


    public void setFlag(Integer flag) {
        this.flag = flag;
    }


    public ChessFight() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Integer getWinNum() {
        return winNum;
    }


    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
    }


    public Integer getTotalNum() {
        return totalNum;
    }


    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }


    public ChessFight(Integer id, String username, Integer firstId, Integer secondId, Integer step, String chess,
                      Integer oppotunity, Integer winNum, Integer totalNum, Integer flag) {
        super();
        this.id = id;
        this.username = username;
        this.firstId = firstId;
        this.secondId = secondId;
        this.step = step;
        this.chess = chess;
        this.oppotunity = oppotunity;
        this.winNum = winNum;
        this.totalNum = totalNum;
        this.flag = flag;
    }


    public Integer getOppotunity() {
        return oppotunity;
    }


    public void setOppotunity(Integer oppotunity) {
        this.oppotunity = oppotunity;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFirstId() {
        return firstId;
    }

    public void setFirstId(Integer firstId) {
        this.firstId = firstId;
    }

    public Integer getSecondId() {
        return secondId;
    }

    public void setSecondId(Integer secondId) {
        this.secondId = secondId;
    }

    public String getChess() {
        return chess;
    }

    public void setChess(String chess) {
        this.chess = chess;
    }


    public Integer getStep() {
        return step;
    }


    public void setStep(Integer step) {
        this.step = step;
    }


}