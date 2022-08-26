//package com.wx.mp.config;
//import com.alibaba.fastjson.JSON;
//import com.jhlj.aiot.common.core.ApplicationContextProvider;
//import com.jhlj.aiot.device.domain.dto.WxMpTemplateDto;
//import com.jhlj.aiot.device.service.WxMpTemplateService;
//import com.wx.mp.entity.WxMpTemplate;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@Slf4j
//public class WxMpTemplateConfig {
//
//    private static Map<String, WxMpTemplateMessage> wxMpTemplateMap = new HashMap<>(8);
//
//    @Autowired
//    private WxMpTemplateService wxMpTemplateService = ApplicationContextProvider.getBean(WxMpTemplateService.class);
//
//
//    @PostConstruct
//    private void init() {
//        log.info("初始化公众号消息模板map============");
//        List<WxMpTemplate> list = wxMpTemplateService.getList();
//        if (CollectionUtils.isEmpty(list)) {
//            return;
//        }
//        list.forEach(dto -> {
//            String wxMpTemplateDataJson = dto.getWxMpTemplateDataJson();
//            List<WxMpTemplateData> wxMpTemplateData = JSON.parseArray(wxMpTemplateDataJson, WxMpTemplateData.class);
//            WxMpTemplateMessage config =
//                    WxMpTemplateMessage.builder()
//                            .templateId(dto.getTemplateId())
//                            .url(dto.getUrl())
//                            .data(wxMpTemplateData)
//                            .build();
//            wxMpTemplateMap.put(dto.getKeyName(), config);
//        });
//    }
//
//    public static WxMpTemplateMessage getTemplate(String key, String openId) {
//        WxMpTemplateMessage wxMpTemplateMessage = wxMpTemplateMap.get(key);
//        if (null == wxMpTemplateMessage) {
//            return null;
//        }
//        if (StringUtils.isNotEmpty(openId)) {
//            wxMpTemplateMessage.setToUser(openId);
//        }
//        return wxMpTemplateMessage;
//    }
//}
//
