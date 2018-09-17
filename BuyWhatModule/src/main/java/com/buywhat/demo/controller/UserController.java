package com.buywhat.demo.controller;

import com.buywhat.demo.bean.User;
import com.buywhat.demo.service.MyServiceImpl;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    private MyServiceImpl service;


    /**
     * 个人信息页面（2018年9月16日 23:46:14这个页面还有问题——访问user/字符 页面的情况还未处理）
     *
     * @param thisUserId 准备查看的用户ID
     * @param session
     * @return
     */
    @RequestMapping("user/{thisUserId}")
    public String personalPage(@PathVariable String thisUserId,
                               HttpSession session, Model model) {


        //当前登录的用户
        User userLogined = (User) session.getAttribute("user");

        if (userLogined != null) {//用户已登录
            //当前用户ID
            Integer loginedId = userLogined.getId();
            if (loginedId.equals(Integer.parseInt(thisUserId))) {//访问的是自己主页
//                model.addAttribute("user", userLogined);
                return "mypersonal";

            } else {//访问的是别人的页面
                User thisUser = service.findAuthorById(Integer.parseInt(thisUserId));
                if (thisUser != null) {//该用户是存在的
                    model.addAttribute("otheruser", thisUser);
                    return "personal";

                } else {//用户不存在
                    return "noSuchUser";
                }


            }


        } else {//用户未登录
            model.addAttribute("user", null);
            System.out.println("没登录不能访问这里");
        }
        return "redirect:/";
    }


    @RequestMapping("changeHeadUrl")
    public String changeHeadImg(String newHeadUrl, Integer userId, HttpSession session) {
        System.out.println("newHeadUrl=" + newHeadUrl);

        //更新用户头像
        boolean flag = service.changeHeadUrl(newHeadUrl, userId);

        if (flag) {//更新成功

            //更新session
            User user = service.findAuthorById(userId);
            session.setAttribute("user", user);

        }
        return "redirect:user/" + userId;


    }


    /**
     * 登录操作
     *
     * @param username 提交的用户名
     * @param password 提交的密码
     * @param session  用于保存用户信息
     * @param rember   用于记住密码
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public Map loginModel(String username, String password, HttpSession session, String rember) {

        Map map = new HashMap();

        //封装登录信息
        User userLogin = new User(null, username, password, null);

        User userSelect = service.doLogin(userLogin);

        if (userSelect != null) {//代表登录信息匹配

            map.put("code", 0);//设置相应码 0表示成功
            //设置session
            session.setAttribute("user", userSelect);


        } else {
            map.put("code", 1);//代表登录失败
            map.put("msgname", "用户名不存在");
            map.put("msgpwd", "或者密码错误，请检查后重新登录");
        }


//        Cookie cookie = new Cookie("name", "");
//        Cookie cookie1 = new Cookie("PASSWORD", "");


        return map;

    }

    /**
     * 注册操作
     *
     * @param username 用户名
     * @param password 密码
     * @param session  用于保存注册成功的用户信息
     * @return 返回Json格式的信息
     */
    @RequestMapping("reg")
    @ResponseBody
    public Map register(String username, String password, HttpSession session) {
        Map map = new HashMap();

        //设置用户默认头像
        String defaultImg = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4147333392,3827187394&fm=27&gp=0.jpg";
        //封装注册信息
        User userReg = new User(null, username, password, defaultImg);
        /**
         * 信息给业务处理
         * 注册成功——返回插入后的User信息
         * 注册失败。或用户名已被占用——返回null
         */
        User userSelect = service.doRegister(userReg);

        if (userSelect != null) {
            map.put("code", 0);//代表注册成功

            //设置session
            session.setAttribute("user", userSelect);

        } else {
            map.put("code", 2);//代表注册失败
            map.put("msgname", "用户名已被占用，请重新注册");
        }

        return map;

    }


    /**
     * 注销操作
     *
     * @param session 用于销毁user
     * @return 重定向到首页
     */
    @RequestMapping("logout")
    public String logout(HttpSession session) {

        session.removeAttribute("user");

        return "redirect:/";
    }
}
