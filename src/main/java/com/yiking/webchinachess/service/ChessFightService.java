package com.yiking.webchinachess.service;

import com.yiking.webchinachess.bean.ChessFight;
import com.yiking.webchinachess.mapper.ChessFightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChessFightService {
    @Autowired
    public ChessFightDao chessFightDao;

    @CachePut(value = "chess", key = "#result.firstId")
    public ChessFight updateF(String chess, Integer id) {
        chessFightDao.updateFight(chess, id);
        return chessFightDao.selectMyself(id);
    }

    @Cacheable(value = "chess", key = "#id")
    public ChessFight selectRival(Integer id) {
        return chessFightDao.selectMyself(id);
    }

    @Cacheable(value = "chess", key = "#id")
    public ChessFight selectMyself(Integer id) {
        return chessFightDao.selectMyself(id);
    }

    @CachePut(value = "chess", key = "#firstid")
    public ChessFight addFighter(Integer firstid, String username, Integer secondid, Integer step, Integer oppotunity) {
        chessFightDao.addFighter(firstid, username, secondid, step, oppotunity);
        return chessFightDao.selectMyself(firstid);
    }

    @CachePut(value = "chess", key = "#firstid")
    public ChessFight updateFighter(Integer secondid, Integer oppotunity, Integer flag, Integer firstid) {
        //System.out.println("---step3----");
        chessFightDao.updateFighter(secondid, oppotunity, flag, firstid);
        return chessFightDao.selectMyself(firstid);
    }

    public List<ChessFight> selectWaitting() {
        return chessFightDao.selectWaitting();
    }

    @CachePut(value = "chess", key = "#id")
    public ChessFight updateWinNum(Integer id) {
        chessFightDao.updateWinNum(id);
        return chessFightDao.selectMyself(id);
    }

    @CachePut(value = "chess", key = "#id")
    public ChessFight updateLossNum(Integer id) {
        chessFightDao.updateLossNum(id);
        return chessFightDao.selectMyself(id);
    }
}
