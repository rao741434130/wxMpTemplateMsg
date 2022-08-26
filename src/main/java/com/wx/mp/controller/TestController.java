package com.wx.mp.controller;

import com.wx.mp.entity.WxMpTemplateMsgConfig;
import com.wx.mp.service.WxDeviceMsgPushService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName TestController
 * @Description
 * @Author DJ
 * @Date 2022/8/26 10:33
 * @Version 1.0
 */
@RequestMapping("/test")
@CrossOrigin
@RestController
public class TestController {

    @Resource
    private WxDeviceMsgPushService wxDeviceMsgPushService;

    @PostMapping("/push")
    public String push(@RequestBody WxMpTemplateMsgConfig wxMpTemplateMessage) {
        return wxDeviceMsgPushService.sendMsg(wxMpTemplateMessage, null);
    }
}
