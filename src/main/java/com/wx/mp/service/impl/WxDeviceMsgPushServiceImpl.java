package com.wx.mp.service.impl;


import com.wx.mp.entity.Ana;
import com.wx.mp.entity.WxMpTemplateMsgConfig;
import com.wx.mp.service.WxDeviceMsgPushService;
import com.wx.mp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class WxDeviceMsgPushServiceImpl implements WxDeviceMsgPushService {

    @Resource
    private WxMpService wxMpService;
    private static List<Ana> anaList;
    private static List<String> colorList;
    private static Map<String, String> cityMap;

    private static List<String> tempList = new ArrayList<>();


    //    @Resource
//    private WxMpTemplateConfig wxMpTemplateConfig;
    static {
        try {
            anaList = FileUtil.readAna("D:\\wxMpTemplateMsg\\wxMpTemplateMsg\\src\\main\\resources\\ana.txt");
            colorList = FileUtil.readColor("D:\\wxMpTemplateMsg\\wxMpTemplateMsg\\src\\main\\resources\\color3.txt");
            cityMap = FileUtil.readCity("D:\\wxMpTemplateMsg\\wxMpTemplateMsg\\src\\main\\resources\\city.txt");
            tempList.add("_xLqtgGZ9H78yD2q4RIiFMQwWw9IxvQnwUr8moJc52A");//早上好
            tempList.add("nRu5LRWaUGjRl0HJ7Vc2MEAMSb2Hj3lF9WAstofvwLY");//中午好
            tempList.add("h8fMpy5wHp8MXtXH0dbzRFaMdJ72GMuNkAHvaoLdqKY");//晚上好
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公众号消息推送
     *
     * @param wxMpTemplateMessage
     * @param templateKey         模板key
     * @return msgJson不为null则发送成功, 否则抛出异常, 详情可看源码
     */
    @Override
    public String sendMsg(WxMpTemplateMsgConfig wxMpTemplateMessage, String templateKey) {
//        log.info(anaList.toString());
//        log.info(colorList.toString());
//        String color =  getColor();
//        log.info("颜色：{}", color);
//        // 发送模板消息接口
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                // 接收者openid
                .toUser(wxMpTemplateMessage.getToUser())
                // 模板id
                .templateId(getTemp())
                // 模板跳转链接
                .url(wxMpTemplateMessage.getUrl())
                .build();

        URL url = null;
        String city = null;
        String temperature = null;
        String weather = null;
        String desc = null;
        try {
            String code = cityMap.get(wxMpTemplateMessage.getCityName());
            url = new URL("http://t.weather.itboy.net/api/weather/city/" + code);
            InputStreamReader isReader = new InputStreamReader(url.openStream(), "UTF-8");//“UTF- 8”万国码，可以显示中文，这是为了防止乱码
            BufferedReader br = new BufferedReader(isReader);//采用缓冲式读入
            String str;
            while ((str = br.readLine()) != null) {
                String regex = "\\p{Punct}+";
                String digit[] = str.split(regex);
                city = digit[22] + digit[18];
                log.info('\n' + "城市:" + digit[22] + digit[18]);
                log.info('\n' + "时间:" + digit[49] + "年" + digit[50] + "月" + digit[51] + "日" + digit[53]);
                log.info('\n' + "温度:" + digit[47] + "~" + digit[45]);
                temperature = digit[47] + "~" + digit[45];
                log.info('\n' + "天气:" + digit[67] + " " + digit[63] + digit[65]);
                weather = digit[67] + " " + digit[63] + digit[65];
                log.info('\n' + digit[69]);
                desc = digit[69];
            }
            br.close();//网上资源使用结束后，数据流及时关闭
            isReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Ana ana = getAna();
        // 添加模板数据
        templateMessage.addData(new WxMpTemplateData("date", wxMpTemplateMessage.getUserName(), getColor()))
                .addData(new WxMpTemplateData("region", city, getColor()))
                .addData(new WxMpTemplateData("weather", weather, getColor()))
                .addData(new WxMpTemplateData("temp", temperature, getColor()))
                .addData(new WxMpTemplateData("tips", desc, getColor()))
                .addData(new WxMpTemplateData("note_en", ana.getEnglish(), getColor()))
                .addData(new WxMpTemplateData("note_ch", ana.getChinese(), getColor()));
//        templateMessage.addData(new WxMpTemplateData("date", "名字:", getColor()))
//                .addData(new WxMpTemplateData("region", "地区", getColor()))
//                .addData(new WxMpTemplateData("weather", weather, getColor()))
//                .addData(new WxMpTemplateData("temp", temperature, getColor()))
//                .addData(new WxMpTemplateData("tips", desc, getColor()))
//                .addData(new WxMpTemplateData("note_en", ana.getEnglish(), getColor()))
//                .addData(new WxMpTemplateData("note_ch", ana.getChinese(), getColor()));
//        WxMpTemplateMessage templateMessage = WxMpTemplateConfig.getTemplate(templateKey, wxMpTemplateMessage.getToUser());
        String msgJson = null;
        try {
            // 发送模板消息
            msgJson = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.warn("·==++--·推送微信模板信息：{}·--++==·", msgJson != null ? "成功" : "失败");
        return String.valueOf(msgJson != null);
    }


    public Ana getAna() {
        Random random = new Random();
        int i = random.nextInt(anaList.size());
        return anaList.get(i);
    }

    public String getColor() {
        Random random = new Random();
        String color;
        do {
            color = colorList.get(random.nextInt(colorList.size()));
        } while (color.indexOf("-") > 0);
//        log.info("color:{}", color);
        return color;
    }

    public String getTemp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(sdf.format(new Date()));
        System.out.println(hour);
        if (hour > 1 && hour < 11) {
            return tempList.get(0);
        } else if (hour >= 11 && hour < 18) {
            return tempList.get(1);
        } else {
            return tempList.get(2);
        }
    }

}

