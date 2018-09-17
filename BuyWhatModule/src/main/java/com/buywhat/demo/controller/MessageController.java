package com.buywhat.demo.controller;

import com.buywhat.demo.bean.Message;
import com.buywhat.demo.bean.User;
import com.buywhat.demo.bean.vo.ConversationVo;
import com.buywhat.demo.bean.vo.MessageVo;
import com.buywhat.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {


    @Autowired
    private MyService service;


    //    /msg/detail?conversationId=1_34

    @RequestMapping("/msg/detail")
    public String showLetterDetail(String conversationId, Model model) {

        List<MessageVo> messageVos = service.findMessageByConversationId(conversationId);

        model.addAttribute("messages", messageVos);
        return "letterDetail";
    }


    /**
     * 点击【站内信】或者【发送站内信成功】
     * 跳转到查看站内信页面
     *
     * @return
     */
    @RequestMapping("/msg/list")
    public String toMsgList(Model model, HttpSession session) {


        User user = (User) session.getAttribute("user");

        if (user != null) {

            List<ConversationVo> conversationVoList = service.getAllConversationVoByUserId(user.getId());

            model.addAttribute("conversations", conversationVoList);
        } else {

            return "redirect:/";
        }

        return "letter";
    }


    /**
     * 点击【发私信】
     * 跳转到发私信页面
     *
     * @return
     */
    @RequestMapping("user/tosendmsg")
    public String toSendMsgPage() {
        return "sendmsg";
    }

    /**
     * 真的把私信发出去
     *
     * @param session
     * @return
     */
    @RequestMapping("user/msg/addMessage")
    @ResponseBody
    public Map sendMsg(HttpSession session, String toName, String content) {

        Map map = new HashMap();//用于返回信息

        //获取当前用户信息
        User sender = (User) session.getAttribute("user");

        //用Name查到用户
        User receiver = service.findUserByName(toName);

        if (receiver != null) {//发送的目的用户是存在的
            //获取目的用户的ID
            Integer toId = receiver.getId();

            //获取发送者的ID
            Integer fromId = sender.getId();


            if (toId == fromId) {//自己给自己发站内信
                map.put("code", 1);
                map.put("msg", "在我们这里，你不能发信给自己");

            } else {//不是给自己发站内信 继续进行
                //构造conversationId 小的用户id在前 大的用户id在后 组成会话id
                String conversationId = fromId < toId ? fromId + "_" + toId : toId + "_" + fromId;

                //配置待插入的信息（from_id to_id  content created_date created_date conversation）
                Message message = new Message();
                message.setContent(content);
                message.setToId(toId);
                message.setFromId(fromId);
                message.setCreatedDate(new Date());
                message.setConversationId(conversationId);
                message.setHasRead("0");//0代表这条消息是未读的,1代表已读

                boolean result = service.sendMessage(message);

                if (result) {//发送站内信成功
                    map.put("code", 0);
                    map.put("msg", "发送站内信成功");

                } else {//数据库炸了-发送站内信失败
                    map.put("code", 1);
                    map.put("msg", "数据库炸了，发送站内信失败");

                }
            }
        } else {//发送的目的用户 不存在
            map.put("code", 1);
            map.put("msg", "指定的用户不存在！！请检查后重新填写");


        }
        return map;
    }


}
