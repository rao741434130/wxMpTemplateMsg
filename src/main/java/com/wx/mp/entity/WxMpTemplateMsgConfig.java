package com.wx.mp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Builder
public class WxMpTemplateMsgConfig implements Serializable {

    private static final long serialVersionUID = 5063374783759519418L;

    /**
     * 接收者openid.
     */
    private String toUser;

    /**
     * 模板ID.
     */
    private String templateId;

    /**
     * 模板跳转链接.
     * <pre>
     * url和miniprogram都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。
     * 开发者可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
     * </pre>
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据.
     *
     * @see #url
     */
    private me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram miniProgram;
}
