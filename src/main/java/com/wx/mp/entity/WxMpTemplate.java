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
public class WxMpTemplate implements Serializable {

    private static final long serialVersionUID = 337800069603349L;

    private Long id;

    /**
     * key
     */
    private String keyName;

    /**
     * 描述
     */
    private String description;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 跳转url
     */
    private String url;

    /**
     * 公众号模板
     */
    private String wxMpTemplateDataJson;

    private Integer status;
}


