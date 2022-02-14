package com.study.board.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Async
    public void deleteImage(List<String> imagePaths) {
        log.info("delete Images = {}",imagePaths);
        for (String path : imagePaths) {
            File file = new File(imagePath + path);
            file.delete();
        }
    }
}
