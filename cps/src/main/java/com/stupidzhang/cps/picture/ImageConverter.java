package com.stupidzhang.cps.picture;

import com.stupidzhang.cps.constants.Constants;
import com.stupidzhang.cps.model.ImageWaterMarkContent;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;


@Component
public class ImageConverter {


    public void addWaterMark(String imgPath, java.util.List<ImageWaterMarkContent> imageWaterMarkContents) {
        addWaterMark(imgPath, imgPath, imageWaterMarkContents);
    }

    /**
     * 在图片添加水印
     *
     * @param srcImgPath
     * @param tarImgPath
     * @param imageWaterMarkContents
     */
    public void addWaterMark(String srcImgPath, String tarImgPath, java.util.List<ImageWaterMarkContent> imageWaterMarkContents) {

        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);
            // 文件转化为图片
            Image srcImg = ImageIO.read(srcImgFile);

            // 获取原图片的宽和高
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 临时图片，Graphics2D是图片合成的主要工具
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

            // 循环需要写到图片上的入参列表，进行内容的书写和覆盖
            if (imageWaterMarkContents != null && !imageWaterMarkContents.isEmpty()) {
                for (ImageWaterMarkContent imageWaterMarkContent : imageWaterMarkContents) {
                    // 如果是图片，那么内容就是图片的路径，读取图片之后，绘画之
                    if (imageWaterMarkContent.getType() == 1) {
                        String imgPath = imageWaterMarkContent.getContent();
                        // 文件转化为图片格式
                        if (Boolean.TRUE.equals(imageWaterMarkContent.getCompress())) {
                            imgPath = Constants.ROOT_PATH + "/temporary/qr_code_compress.jpeg";
                            Thumbnails.of(imageWaterMarkContent.getContent())
                                    .scale(imageWaterMarkContent.getScale())
                                    .outputQuality(imageWaterMarkContent.getOutputQuality())
                                    .toFile(imgPath);
                        }
                        Image coverImg = ImageIO.read(new File(imgPath));
                        g.drawImage(coverImg, imageWaterMarkContent.getX(), imageWaterMarkContent.getY(), null);
                    } else if (imageWaterMarkContent.getType() == 2) {
                        // 如果是文字，那么需要设置一下文字的格式，消除锯齿之后进行写入
                        String content = imageWaterMarkContent.getContent();
                        // 设置字体
                        if (imageWaterMarkContent.getFont() != null) {
                            g.setFont(imageWaterMarkContent.getFont());
                        }
                        if (imageWaterMarkContent.getColor() != null) {
                            g.setColor(imageWaterMarkContent.getColor());
                        }
                        // 消除锯齿
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        // 获取字体长度
                        int tempX = imageWaterMarkContent.getX();
                        int tempY = imageWaterMarkContent.getY();
                        int tempCharLen = 0;
                        int tempLineLen = 0;
                        StringBuilder stringBuffer = new StringBuilder();
                        for (int i = 0; i < content.length(); i++) {
                            char tempChar = content.charAt(i);
                            tempCharLen = getCharLen(tempChar, g);
                            if (tempLineLen >= 550) {
                                // 绘制前一行
                                AttributedString ats = new AttributedString(stringBuffer.toString());
                                ats.addAttribute(TextAttribute.FONT, imageWaterMarkContent.getFont(), 0, stringBuffer.length());
                                g.drawString(ats.getIterator(), tempX, tempY);
                                // 清空内容,重新追加
                                stringBuffer.delete(0, stringBuffer.length());
                                // 文字长度已经满一行,Y的位置加1字符高度
                                tempY = tempY + imageWaterMarkContent.getFont().getSize() + 6;
                                tempLineLen = 0;
                            }
                            // 追加字符
                            stringBuffer.append(tempChar);
                            tempLineLen += tempCharLen;
                        }
                        // 最后叠加余下的文字
                        AttributedString ats = new AttributedString(stringBuffer.toString());
                        ats.addAttribute(TextAttribute.FONT, imageWaterMarkContent.getFont(), 0, stringBuffer.length());
                        g.drawString(ats.getIterator(), tempX, tempY);
                    }
                }
                g.dispose();
                String formatName = tarImgPath.substring(tarImgPath.lastIndexOf(".") + 1);
                ImageIO.write(bufImg, formatName, new File(srcImgPath));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCharLen(char c, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charWidth(c);
    }


}
