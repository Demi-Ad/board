package com.study.board.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final String imagePath = "D:\\java\\images\\";

    public String uploadImage(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String saveFileName = UUID.randomUUID() + extension;

        File file = new File(imagePath + saveFileName);

        try {
            multipartFile.transferTo(file);
            return "/images/" + saveFileName;
        } catch (IOException e) {
            file.delete();
            return null;
        }
    }
}
