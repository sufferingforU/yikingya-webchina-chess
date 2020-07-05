package com.yiking.webchinachess.mapper;

import com.yiking.webchinachess.bean.ChessFight;

import java.util.List;

public interface ChessFightDao {
    public int updateFight(String chess, Integer id);

    public ChessFight selectRival(Integer id);

    public ChessFight selectMyself(Integer id);

    public int addFighter(Integer firstid, String username, Integer secondid, Integer step, Integer oppotunity);

    public int updateFighter(Integer secondid, Integer oppotunity, Integer flag, Integer firstid);

    public List<ChessFight> selectWaitting();

    public int updateWinNum(Integer id);

    public int updateLossNum(Integer id);
}
