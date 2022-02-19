package com.study.board.web.config.Scheduler;

import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.web.util.Constants;
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
public class DatabaseToLocalImageFileSynchronizeScheduler {

    private final PostRepository postRepository;

    private final Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

    @Scheduled(fixedRate = 1000 * 60 * 60 * 12, initialDelay = 1000 * 60 * 5)
    public void synchronizeTask() {
        log.info("synchronizeTask Start");

        List<String> list = postRepository.findAllContents();

        if (list.size() == 0) {
            log.warn("synchronizeTask stop reason = No Content");
            return;
        }

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

        File imgDir = new File(Constants.IMAGE_PATH);
        if (imgDir.listFiles() == null) {
            log.warn("synchronizeTask stop reason = No LocalImage");
            return;
        }
        List<String> localImageList = Arrays.stream(Objects.requireNonNull(imgDir.listFiles()))
                .map(File::getName)
                .collect(Collectors.toList());

        log.info("localImg = {}",localImageList);

        localImageList.removeAll(databaseImgList);

        if (localImageList.size() == 0) {
            log.info("synchronizeTask stop reason = early synchronize");
            return;
        }

        localImageList.stream()
                .map(img -> new File(Constants.IMAGE_PATH + "//" + img))
                .forEach(File::delete);

        log.info("synchronizeTask End");
    }
}
