package com.wx.mp.service.impl;


import com.wx.mp.entity.WxMpTemplateMsgConfig;
import com.wx.mp.service.WxDeviceMsgPushService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
@Slf4j
public class WxDeviceMsgPushServiceImpl implements WxDeviceMsgPushService {

    @Resource
    private WxMpService wxMpService;
//    @Resource
//    private WxMpTemplateConfig wxMpTemplateConfig;

    /**
     * 公众号消息推送
     *
     * @param wxMpTemplateMessage
     * @param templateKey         模板key
     * @return msgJson不为null则发送成功, 否则抛出异常, 详情可看源码
     */
    @Override
    public String sendMsg(WxMpTemplateMsgConfig wxMpTemplateMessage, String templateKey) {
//        // 发送模板消息接口
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                // 接收者openid
                .toUser(wxMpTemplateMessage.getToUser())
                // 模板id
                .templateId(wxMpTemplateMessage.getTemplateId())
                // 模板跳转链接
                .url(wxMpTemplateMessage.getUrl())
                .build();
        URL url = null;
        String city = null;
        String temperature = null;
        String weather = null;
        String desc = null;
        try {
            url = new URL("http://t.weather.itboy.net/api/weather/city/101210101");
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

        // 添加模板数据
        templateMessage.addData(new WxMpTemplateData("date", "小卢同学:", "#FF00FF"))
                .addData(new WxMpTemplateData("region", "广州市番禺区", "#FF6699"))
                .addData(new WxMpTemplateData("weather", weather, "#99FFFF"))
                .addData(new WxMpTemplateData("temp", temperature, "#CC33FF"))
                .addData(new WxMpTemplateData("zwxzs", desc, "#FF0000"));
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
}

