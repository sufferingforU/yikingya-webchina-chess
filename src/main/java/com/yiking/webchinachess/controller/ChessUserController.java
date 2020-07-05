package com.yiking.webchinachess.controller;

import com.yiking.webchinachess.bean.ChessUser;
import com.yiking.webchinachess.service.ChessFightService;
import com.yiking.webchinachess.service.ChessService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChessUserController {
    @Autowired
    public ChessService chessService;
    @Autowired
    public ChessFightService chessFightService;

    /*
     * @RequestMapping(value="/emp",method = RequestMethod.POST) public String
     * saveEmployee(@Valid Employee employee,BindingResult binder,Model model) {
     * Map<String,Object>errors=new HashMap<>(); List<FieldError>
     * list=binder.getFieldErrors();
     *
     * boolean flag=binder.hasErrors(); if(flag) { for(FieldError ss:list) {
     * //System.out.println(ss.getField()+"=="+ ss.getDefaultMessage());
     * errors.put(ss.getField(), ss.getDefaultMessage()); }
     * model.addAttribute("errorInfo", errors); return "add"; }else {
     * System.out.println(employee); employeeDao.save(employee);
     *
     * return "redirect:/emps";}
     *
     * }
     */
    @ApiOperation("注册用户")
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String tologin(@Valid ChessUser user, BindingResult binder, Model model) {
        Map<String, Object> errors = new HashMap<>();
        List<FieldError> list = binder.getFieldErrors();
        boolean flag = binder.hasErrors();
        if (flag) {
            for (FieldError ss : list) {
                model.addAttribute(ss.getField(), ss.getDefaultMessage());
                errors.put(ss.getField(), ss.getDefaultMessage());
                System.out.println(ss.getDefaultMessage());
            }

            return "register";
        } else {
            chessService.save(user);
            return "login";
        }
    }

    @ApiOperation("用户登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String toIndex(String username, String password, Model model) {

        ChessUser temp = (ChessUser) chessService.check(username, password);

        if (temp != null) {
            model.addAttribute("username", temp.getUsername());
            model.addAttribute("userid", temp.getId());
            // model.addAttribute("oppo",
            // chessFightService.selectMyself(temp.getId()).getOppotunity());
            return "onlineindex.html";
        } else {
            model.addAttribute("error", "用户名密码错误 ！ ！ ！");
            return "login";
        }

    }

    @ApiOperation("携带用户信息进行页面跳转")
    @RequestMapping(value = "/hello02", method = RequestMethod.POST)
    public String toIndex2(String username, String userid, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("userid", userid);

        return "onlineindex.html";
    }

    @ApiOperation("跳转到注册页面")
    @RequestMapping(value = "/toregister1", method = RequestMethod.GET)
    public String toregister1(ChessUser user, Model model) {
        return "register";
    }

    @ApiOperation("跳转到登陆页面")
    @RequestMapping(value = "/tologin1", method = RequestMethod.GET)
    public String tologin1(ChessUser user, Model model) {
        return "login";
    }

    @ApiOperation("跳转到index.html")
    @RequestMapping(value = "/toindex", method = RequestMethod.GET)
    public String tologin2(ChessUser user, Model model) {
        return "index";
    }

    @GetMapping("hh1")
    public ChessUser nothing() {
        return new ChessUser();
    }
}
