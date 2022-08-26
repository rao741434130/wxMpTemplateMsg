//package com.wx.mp.service.impl;
//
//
//import com.wx.mp.service.WxMphelperServer;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.mp.api.WxMpService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.HashMap;
//
//@Service
//@Slf4j
//public class WxMphelperServerImpl implements WxMphelperServer {
//
//    @Autowired
//    private WxMpService wxMpService;
//
//    @Override
//    public String getMphelperOpenId(String code) {
//
//        // 1 获取微信openId的Url
//        try {
//            String key = CommonConfigKeyEnum.MPHELPER_OPENID_URL.getKey();
//            CommonConfigDto configDto = commonConfigClient.findByKey(key);
//            if (configDto == null || StringUtils.isEmpty(configDto.getCommonValue())) {
//                return RestResult.fail(MemberErrCode.DATA_NOT_EXIST);
//            }
//            // 2 拼接后的url
//            HashMap<String, String> hashMap = new HashMap<>();
//            String appId = wxMpService.getWxMpConfigStorage().getAppId();
//            String secret = wxMpService.getWxMpConfigStorage().getSecret();
////            String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appId, secret, code);
//            String url = String.format(configDto.getCommonValue(), appId, secret, code);
//            // 3 远程调用微信获取openId
//            log.info("【请求url】:{}", url);
//            String str = OkHttpUtil.get(url);
//            JSONObject json = JSON.parseObject(str);
//            log.info("微信获取openid:原因=%s" + str);
//            String errcode = json.getString("errcode");
//            if (StringUtils.isEmpty(errcode)) {
//                String openid = json.getString("openid");
//                if (StringUtils.isEmpty(openid)) {
//                    log.error("【error:获取openId异常】");
//                    return RestResult.fail();
//                }
//                hashMap.put("openid", openid);
//                return RestResult.success(hashMap);
//            }
//            if (WxMpErrCode.INVALID_CODE.getErrCode().equals(errcode)) {
//                return RestResult.fail(WxMpErrCode.INVALID_CODE);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return RestResult.fail();
//    }
//}
//
