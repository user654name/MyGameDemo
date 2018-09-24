package com.buywhat.demo.controller;

import com.buywhat.demo.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    /**
     * 用户对消息点赞
     * @param newsId 消息id
     * @param session 用户身份
     * @return Json数据 （赞的数目）
     */
    @RequestMapping("like")
    @ResponseBody
    public Map addLike(String newsId, HttpSession session) {
        //将UserId 放入newsId的集合中
        User user = (User) session.getAttribute("user");

        Map map = new HashMap();

        if (user != null) {//用户处于正常登录状态
            Integer userId = user.getId();
            Jedis jedis = new Jedis();

            if (newsId != null && newsId != "") {//确认是否带有newsId信息,以防出错或恶意访问
                String newsSetKeyName = "newsLike" + newsId;
                //将用户id放入集合中
                jedis.sadd(newsSetKeyName, userId + "");
                //读取集合中元素个数（即点赞用户数）
                Long scard = jedis.scard(newsSetKeyName);
                map.put("msg", scard);//放入总赞数


            } else {
                map.put("msg", "异常");////在按钮上显示异常
                System.out.println("出现异常！没有获得到新文数,UserId=" + userId);
            }


        } else {//用户未登录
            map.put("msg", "不行");//在点赞数上显示文字 逗一下这个未登录用户


        }
        map.put("code", 0);//放入0 前端就不会弹出那个奇怪的页面

        return map;

    }

    @RequestMapping("dislike")
    @ResponseBody
    public Map removeLike(String newsId, HttpSession session) {
        //将UserId 从newsId的集合中移除
        User user = (User) session.getAttribute("user");

        Map map = new HashMap();

        if (user != null) {//用户处于正常登录状态
            Integer userId = user.getId();
            Jedis jedis = new Jedis();
            if (newsId != "null" && newsId != "") {
                String newsSetKeyName = "newsLike" + newsId;

                jedis.srem(newsSetKeyName, userId + "");
                Long scard = jedis.scard(newsSetKeyName);
                map.put("msg", scard);//放入总赞数


            } else {
                map.put("msg", "异常");//在按钮上显示异常

                System.out.println("出现异常！没有获得到新文数,UserId=" + userId);


            }

        } else {//用户未登录或者登录已失效

            map.put("msg", "请登");//在按钮上显示异常

        }

        map.put("code", 0);//放入总赞数

        return map;

    }
}
