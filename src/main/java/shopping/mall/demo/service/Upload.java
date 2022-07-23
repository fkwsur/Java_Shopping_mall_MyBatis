package shopping.mall.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Upload {

    private String file_path = "/uploads";

    public String uploadFile(MultipartFile file) {

        Path targetPath = Paths.get(file_path + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));

        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return targetPath.toString();
    }
}