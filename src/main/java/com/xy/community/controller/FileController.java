package com.xy.community.controller;

import com.xy.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        File dirFile = new File("e:\\communityImg");
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String fileName = file.getOriginalFilename();
        File newFile = new File(dirFile, fileName);
        file.transferTo(newFile);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/upload/" + fileName);
        return fileDTO;
    }

}
