package com.yiking.webchinachess.mapper;

import com.yiking.webchinachess.bean.ChessUser;

public interface ChessUserDao {
    public void saveUser(ChessUser user);

    public ChessUser checkUser(String username, String password);

}
