package com.yiking.webchinachess.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
@ApiModel("用户实体类")
public class ChessUser implements Serializable {
    private Integer id;
    @NotEmpty
    @Length(max = 18, min = 4)
    @ApiModelProperty("用户名")
    private String username;
    @NotEmpty
    @Length(max = 18, min = 6)
    @ApiModelProperty("用户密码")
    private String password;
    @Email
    @NotEmpty
    @ApiModelProperty("用户邮箱")
    private String email;

    @Override
    public String toString() {
        return "ChessUser [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
    }

    public ChessUser(Integer id, String username, String password, String email) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public ChessUser() {
        super();
        // TODO Auto-generated constructor stub
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

