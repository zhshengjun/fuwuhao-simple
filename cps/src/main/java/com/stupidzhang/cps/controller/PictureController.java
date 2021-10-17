package com.stupidzhang.cps.controller;


import com.stupidzhang.cps.service.CpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private CpsService cpsService;

    @PostMapping("/upload")
    @ResponseBody
    public List<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("content") String content) {
        return cpsService.convertPicture(file, content);
    }

}
