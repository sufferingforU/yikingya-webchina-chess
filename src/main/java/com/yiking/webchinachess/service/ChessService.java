package com.yiking.webchinachess.service;

import com.yiking.webchinachess.bean.ChessUser;
import com.yiking.webchinachess.mapper.ChessUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChessService {
    @Autowired
    public ChessUserDao chessUserDao;

    public void save(ChessUser user) {
        chessUserDao.saveUser(user);
    }

    public ChessUser check(String username, String password) {
        return (ChessUser) chessUserDao.checkUser(username, password);
    }
}
