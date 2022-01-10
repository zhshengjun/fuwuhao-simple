package com.stupidzhang.cps.service;

import com.stupidzhang.convert.service.UrlConvertContext;
import com.stupidzhang.cps.constants.Constants;
import com.stupidzhang.cps.mapper.TaobaoCpsMapper;
import com.stupidzhang.cps.model.TaobaoCps;
import com.stupidzhang.cps.picture.PictureProcessor;
import com.stupidzhang.cps.qrCode.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CpsService {

    @Autowired
    private TaobaoCpsMapper taobaoCpsMapper;

    @Autowired
    private PictureProcessor pictureProcessor;

    @Autowired
    private AliYunUploadService aliyunUploadService;

    @Autowired
    private UrlConvertContext convertContext;

    public TaobaoCps query(Integer id) {
        return taobaoCpsMapper.selectByPrimaryKey(id);
    }


    /**
     * 生成图片
     *
     * @param file
     * @param content
     * @return
     */
    public List<String> convertPicture(MultipartFile file, String content) {
        String path = Constants.ROOT_PATH + Constants.CPS_RESULT_PNG;
        String qrcode = Constants.ROOT_PATH + Constants.QR_CODE_PATH;
        List<String> imageList = new ArrayList<>();
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path))) {

            out.write(file.getBytes());
            String url = convertContext.convertUrl("tb", content);
            BufferedImage image = QRCodeUtil.createImage(url, 196);
            String formatName = qrcode.substring(qrcode.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName, new File(qrcode));
            pictureProcessor.generateCpsPictureUrl(path, qrcode);
            String uploadItem = aliyunUploadService.upload(path);
            log.info(uploadItem);
            imageList.add(uploadItem);
            String uploadQrcode = aliyunUploadService.upload(qrcode);
            imageList.add(uploadQrcode);
            log.info(uploadQrcode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageList;
    }
}
