package com.stupidzhang.cps.picture;

import com.stupidzhang.cps.constants.Constants;
import com.stupidzhang.cps.model.ImageWaterMarkContent;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PictureProcessor {


    private static final Color DEFAULT_ITEM_COLOR = new Color(51, 51, 51, 255);
    private static final Font DEFAULT_ITEM_FONT = new Font("KaiTi", Font.BOLD, 28);

    private static final Font DEFAULT_COUPONS_FONT = new Font("KaiTi", Font.BOLD, 24);
    private static final Color DEFAULT_COUPONS_COLOR = new Color(220, 167, 35, 255);

    private static final Font DEFAULT_REMARK_FONT = new Font("KaiTi", Font.BOLD, 20);
    private static final Color DEFAULT_REMARK_COLOR = new Color(153, 153, 153, 255);

    @Autowired
    private ImageConverter imageConverter;

    public void generateCpsPictureUrl(String srcImgPath, String... content) {
        List<ImageWaterMarkContent> imageWaterMarkContents = new ArrayList<>();
        // 读取原图片信息
        File srcImgFile = new File(srcImgPath);
        //文件转化为图片
        try {
            Image srcImg = ImageIO.read(srcImgFile);
            //获取图片的宽和高
            int srcImgWidth = srcImg.getWidth(null);
            BigDecimal bigDecimal = new BigDecimal(srcImgWidth);
            BigDecimal defaultDecimal = new BigDecimal(800);
            float scale = defaultDecimal.divide(bigDecimal, 2, BigDecimal.ROUND_HALF_DOWN).floatValue();
            Thumbnails.of(srcImgPath).scale(scale).outputQuality(1f).toFile(srcImgPath);
            int srcImgHeight = srcImg.getHeight(null);
            srcImgHeight = (int) (srcImgHeight * scale);
            ImageWaterMarkContent qrcode = ImageWaterMarkContent.builder().content(Constants.ROOT_PATH + Constants.QR_CODE_PATH)
                    .type(1).x(590).y(srcImgHeight - 227).build();
            imageWaterMarkContents.add(qrcode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageConverter.addWaterMark(srcImgPath, imageWaterMarkContents);
    }

    /**
     * 拼装CPS 的图
     *
     * @param itemPic
     * @param qrCodePic
     * @return
     */
    public static List<ImageWaterMarkContent> buildAnniversaryImageWaterMarkContents(String itemPic, String qrCodePic,
                                                                                     String content, String coupons, String remark) {
        List<ImageWaterMarkContent> imageWaterMarkContents = new ArrayList<>();
        //设置 商品图
        if (!StringUtils.isEmpty(itemPic)) {
            ImageWaterMarkContent itemPicContent = new ImageWaterMarkContent();
            itemPicContent.setContent(itemPic);
            itemPicContent.setCompress(false);
            itemPicContent.setType(1);
            itemPicContent.setX(1);
            itemPicContent.setY(0);
            imageWaterMarkContents.add(itemPicContent);
        }


        if (!StringUtils.isEmpty(qrCodePic)) {
            //设置 二维码
            ImageWaterMarkContent qrCodePicContent = new ImageWaterMarkContent();
            qrCodePicContent.setContent(qrCodePic);
            qrCodePicContent.setCompress(true);
            qrCodePicContent.setOutputQuality(1f);
            qrCodePicContent.setScale(0.63f);
            qrCodePicContent.setType(1);
            qrCodePicContent.setX(590);
            qrCodePicContent.setY(815);
            imageWaterMarkContents.add(qrCodePicContent);
        }


        if (!StringUtils.isEmpty(content)) {
            // 设置宣传语
            ImageWaterMarkContent copywriter = new ImageWaterMarkContent();
            copywriter.setContent(content);
            copywriter.setType(2);
            copywriter.setX(15);
            copywriter.setY(846);
            copywriter.setFont(DEFAULT_ITEM_FONT);
            copywriter.setColor(DEFAULT_ITEM_COLOR);
            imageWaterMarkContents.add(copywriter);
        }

        // 设置宣传语
        if (!StringUtils.isEmpty(coupons)) {
            ImageWaterMarkContent couponsPicContent = new ImageWaterMarkContent();
            couponsPicContent.setContent(coupons);
            couponsPicContent.setType(2);
            couponsPicContent.setX(15);
            couponsPicContent.setY(935);
            couponsPicContent.setFont(DEFAULT_COUPONS_FONT);
            couponsPicContent.setColor(DEFAULT_COUPONS_COLOR);
            imageWaterMarkContents.add(couponsPicContent);
        }

        // 设置宣传语
        if (!StringUtils.isEmpty(remark)) {
            ImageWaterMarkContent remarkPicContent = new ImageWaterMarkContent();
            remarkPicContent.setContent(remark);
            remarkPicContent.setType(2);
            remarkPicContent.setX(15);
            remarkPicContent.setY(996);
            remarkPicContent.setFont(DEFAULT_REMARK_FONT);
            remarkPicContent.setColor(DEFAULT_REMARK_COLOR);
            imageWaterMarkContents.add(remarkPicContent);
        }


        return imageWaterMarkContents;
    }

}
