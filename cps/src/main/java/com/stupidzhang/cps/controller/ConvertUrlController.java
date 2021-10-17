package com.stupidzhang.cps.controller;

import com.stupidzhang.cps.model.TaobaoCps;
import com.stupidzhang.cps.qrCode.QRCodeUtil;
import com.stupidzhang.cps.service.CpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/cps")
public class ConvertUrlController {

    @Autowired
    private CpsService cpsService;



    @GetMapping("query")
    public TaobaoCps query(Integer id) {
        return cpsService.query(id);
    }

    @GetMapping("generate")
    public void query(@RequestParam("content") String content, HttpServletResponse response) {
        log.warn("接收原始链接content：{}", content);
        BufferedImage image;
        // 禁止图像缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        image = QRCodeUtil.createImage(content);
        // 创建二进制的输出流
        try (ServletOutputStream sos = response.getOutputStream()) {
            // 将图像输出到Servlet输出流中。
            ImageIO.write(image, "jpeg", sos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
