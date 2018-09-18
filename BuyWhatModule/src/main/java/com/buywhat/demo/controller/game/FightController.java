package com.buywhat.demo.controller.game;

import com.buywhat.demo.bean.Game.Pokemon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
public class FightController {


    Pokemon mypoke = new Pokemon();
    Pokemon hispoke = new Pokemon();

    /**
     * 进入界面 设置hp100
     *
     * @return
     */
    @RequestMapping("fight")
    public String toFight(Model model, HttpSession session) {

        Object dontInit = session.getAttribute("dontInit");
        if (dontInit != null) {
            //要求不能初始化
        } else {//第一次访问 可以初始化

            mypoke.setHp(100);
            hispoke.setHp(100);
            model.addAttribute("mypoke", mypoke);
            model.addAttribute("hispoke", hispoke);
        }
        return "fight";
    }





    @RequestMapping("aaabbbccc")
    public String addComment(HttpSession session, Model model,
                             Integer leftHit, Integer rightHit) {

        if (mypoke.getHp() > 0 && hispoke.getHp() > 0) {//胜负未分 继续干

            mypoke.setHp(mypoke.getHp() - (int) (1 + Math.random() * (rightHit)));
            hispoke.setHp(hispoke.getHp() - (int) (1 + Math.random() * (leftHit)));


            model.addAttribute("mypoke", mypoke);
            model.addAttribute("hispoke", hispoke);
            System.out.println("皮卡丘HP="+mypoke.getHp());
            System.out.println("喵喵HP="+hispoke.getHp());

            session.setAttribute("dontInit", "不要初始化");
            return "fight";
        } else if (mypoke.getHp() <= 0) {
            model.addAttribute("resultmsg", "输了,充钱立即复活");
            System.out.println("输了,充钱立即复活");

        } else if (hispoke.getHp() <= 0) {
            model.addAttribute("resultmsg", "赢啦嘻嘻");
            System.out.println("赢啦嘻嘻");
        } else {

            System.out.println("怎么会这样");
        }

        return "gameResult";
    }


    @RequestMapping("chushihua")
    public String chushihua(Model model, HttpSession session) {


        mypoke.setHp(100);
        hispoke.setHp(100);
        model.addAttribute("mypoke", mypoke);
        model.addAttribute("hispoke", hispoke);

        return "fight";
    }


}
