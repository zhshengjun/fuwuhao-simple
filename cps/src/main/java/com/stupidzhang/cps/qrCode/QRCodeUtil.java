package com.stupidzhang.cps.qrCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";

    // 二维码尺寸
    private static final int QRCODE_SIZE = 196;

    public static BufferedImage createImage(String content, Integer size) {
        Map<EncodeHintType, Object> hints = new HashMap<>(8);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size,
                    hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }


    public static BufferedImage createImage(String content) {
        return createImage(content, QRCODE_SIZE);
    }

    public static String readQrCode(String qrCodePath) {
        MultiFormatReader formatReader = new MultiFormatReader();
        //读取指定的二维码文件
        File file = new File(qrCodePath);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            //定义二维码参数
            Map hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            Result result = formatReader.decode(binaryBitmap, hints);

            //输出相关的二维码信息
            log.info("解析结果:{}", result.toString());
            log.info("二维码格式类型:{}", result.getBarcodeFormat());
            log.info("二维码文本内容:{}", result.getText());
            bufferedImage.flush();
            return result.toString();
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
