package com.imooc.comtroller;

import com.imooc.dto.FileInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping
    public FileInformation upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        String folder = "D:\\devCode\\renren\\imooc-security\\imooc-security-demo\\src\\main\\java\\com\\imooc\\comtroller";
        File localFile = new File(folder,new Date().getTime() +".txt");
        file.transferTo(localFile);
        return new FileInformation(localFile.getAbsolutePath());
    }
}
