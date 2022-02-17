package com.study.board;

import com.study.board.domain.entity.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class ContentParseTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void paresTest() {
        List<String> list = postRepository.findAllContents();
        Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        List<String> result = new ArrayList<>();

        for (String content : list) {
            Matcher matcher = nonValidPattern.matcher(content);
            while (matcher.find()) {
                String src = matcher.group(1);
                String[] split = src.split("/images/");
                Arrays.stream(split).filter(s -> !s.isBlank()).forEach(result::add);
            }
        }
        System.out.println("result = " + result);
        System.out.println("result.size() = " + result.size());
    }

    @Test
    void fileTest() {
        String imagePath = "D:\\java\\images";

        File file = new File(imagePath);

        Arrays.stream(file.listFiles()).map(File::getName).forEach(System.out::println);
    }
}
