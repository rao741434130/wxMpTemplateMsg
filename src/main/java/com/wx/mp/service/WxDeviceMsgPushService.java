package com.wx.mp.service;

import com.wx.mp.entity.WxMpTemplateMsgConfig;

/**
 * @ClassName WxDeviceMsgPushService
 * @Description
 * @Author DJ
 * @Date 2022/8/26 10:11
 * @Version 1.0
 */
public interface WxDeviceMsgPushService {

    String sendMsg(WxMpTemplateMsgConfig wxMpTemplateMessage, String templateKey);
}
