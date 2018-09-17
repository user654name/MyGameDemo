package com.buywhat.demo.controller;

import com.aliyun.oss.OSSClient;
import com.buywhat.demo.bean.vo.CommentVo;
import com.buywhat.demo.bean.News;
import com.buywhat.demo.bean.User;
import com.buywhat.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class NewsController {

    @Autowired
    private MyService service;


    @RequestMapping("news/{newsId}")
    public String showNews(@PathVariable Integer newsId, Model model,HttpSession session) {


        News news = service.findNewsById(newsId);
        User owner = service.findAuthorById(news.getUserId());
        java.util.List<CommentVo> comments = service.findCommentVoByNewsId(newsId);

        model.addAttribute("news", news);
        model.addAttribute("owner", owner);
        model.addAttribute("comments", comments);

        return "detail";//返回消息细节页面
    }


    @RequestMapping("user/addNews")
    @ResponseBody
    public String addNews(String image, String title, String link, HttpSession session) {

        String ret;//返回值

        //读取session信息
        User author = (User) session.getAttribute("user");

        if (author != null) {//说明发这条消息的人已经正常登录
            //新建一个News类保存新消息
            News newNews = new News();

            newNews.setTitle(title);//标题
            newNews.setImage(image);//图
            newNews.setLink(link);//链接
            newNews.setUserId(author.getId());//设置作者信息
            newNews.setCreatedDate(new Date());//当前时间

            Integer num = service.addNews(newNews);

            ret = num == 1 ? "true" : "fail-to-insert";


        } else {
            //还未登录 或者session过期
            ret = "not-login-error";
        }

        return ret;
    }


    @RequestMapping("uploadImage")
    @ResponseBody
    public Map uploadImage(MultipartFile file) {

        Map map = new HashMap();

        if (file != null && !file.isEmpty()) {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";

            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = "LTAIXdEGh523452PLjsbso";
            String accessKeySecret = "S5kLD36DxAsAD3213w512si23547QeWCNXw0QO";
            String bucketName = "ztqimage";
            String objectName = UUID.randomUUID().randomUUID().toString()
                    + file.getOriginalFilename();

            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);


            try {
                //上传文件流
                ossClient.putObject(bucketName, objectName,
                        new ByteArrayInputStream(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 关闭OSSClient。
            ossClient.shutdown();

            //获取文件上传后的路径
            String imgPath = "http://" + bucketName + ".oss-cn-shenzhen.aliyuncs.com/"
                    + objectName + "?x-oss-process=image/resize,m_fixed,h_100,w_100";

            //放入待返回map中
            map.put("msg", imgPath);
            map.put("code", 0);
        } else {

            map.put("code", 1);


        }

        return map;
    }


}
