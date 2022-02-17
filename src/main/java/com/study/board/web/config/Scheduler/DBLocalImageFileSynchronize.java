package com.study.board.web.config.Scheduler;

import com.study.board.domain.entity.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DBLocalImageFileSynchronize {

    private final PostRepository postRepository;

    private final Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

    private final String imagePath = "D:\\java\\images";



    @Scheduled(fixedRate = 1000 * 60 * 5, initialDelay = 1000 * 60 * 5)
    public void synchronizeTask() {
        List<String> list = postRepository.findAllContents();

        if (list.size() == 0)
            return;

        List<String> databaseImgList = new ArrayList<>();

        for (String content : list) {
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String src = matcher.group(1);
                String[] split = src.split("/images/");
                Arrays.stream(split)
                        .filter(s -> !s.isBlank())
                        .forEach(databaseImgList::add);
            }
        }

        log.info("databaseImg = {}",databaseImgList);

        File imgDir = new File(imagePath);
        if (imgDir.listFiles() == null) {
            return;
        }
        List<String> localImageList = Arrays.stream(Objects.requireNonNull(imgDir.listFiles()))
                .map(File::getName)
                .collect(Collectors.toList());

        log.info("localImg = {}",localImageList);

        localImageList.removeAll(databaseImgList);

        if (localImageList.size() == 0)
            return;

        localImageList.stream()
                .map(img -> new File(imagePath + "//" + img))
                .forEach(File::delete);
    }
}
