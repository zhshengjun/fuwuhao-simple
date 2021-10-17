package com.stupidzhang.cps.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.awt.*;


@Data
@SuperBuilder()
@NoArgsConstructor
public class ImageWaterMarkContent {
    /**
     * 水印内容 如果是图片则是图片地址
     */
    private String content;
    /**
     * 水印类型 1图片 2文字
     */
    private Integer type;
    /**
     * 水印位置
     */
    private Integer x;
    private Integer y;
    /**
     * 文字的颜色
     */
    private Color color;
    /**
     * 文字的字体
     */
    private Font font;

    private Boolean compress;

    private Float scale;

    private Float outputQuality;

}
