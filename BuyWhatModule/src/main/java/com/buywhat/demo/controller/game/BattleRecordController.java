package com.buywhat.demo.controller.game;

import com.buywhat.demo.bean.game.TeamGameRecord;
import com.buywhat.demo.dao.TeamGameRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BattleRecordController {

    @Autowired
    TeamGameRecordMapper mapper;


    @RequestMapping("/showTopPlayer")
    public String showTopPlayer(Model model) {

        //1 根据胜率排名
        List<TeamGameRecord> top10WinRateList = mapper.selectTop10ByWinRate();



        //2 根据胜场排名
        List<TeamGameRecord> top10WinList = mapper.selectTop10ByWin();



        //3 根据场次排名(玩家活跃度榜单)
        List<TeamGameRecord> top10TotalList = mapper.selectTop10ByTotal();



        model.addAttribute("top10WinRate", top10WinRateList);
        model.addAttribute("top10Win", top10WinList);
        model.addAttribute("top10Total", top10TotalList);


        return "test/showTop10Record";
    }


}
