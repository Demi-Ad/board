package com.study.board;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTest {


    @Test
    void test() {
        int totalCount = 89;
        int showOnePage = 5;
        int showOnePagePost = 10;
        int currPage = 1;

        int tempEndPage = ((int) Math.ceil(totalCount / ((double) showOnePagePost)));
        int endPage = ((int) Math.ceil(currPage / ((double) showOnePage)) * showOnePage);
        endPage = Math.min(endPage, tempEndPage);
        int startPage = (endPage - showOnePage) + 1;

        boolean isPrev = startPage != 1;
        boolean isNext = endPage < tempEndPage;

        System.out.println("tempEndPage = " + tempEndPage);
        System.out.println("startPage = " + startPage);
        System.out.println("endPage = " + endPage);
        System.out.println("isPrev = " + isPrev);
        System.out.println("isNext = " + isNext);

    }

    @Test
    void rexTest() {
//        String data = "<p><img style=\"width: 278px;\" src=\"/images/78c4c9cb-b7dd-4708-81d6-77ca5d639f3d.png\"></p>\n<p><img style=\"width: 278px;\" src=\"/images/83b633b7-9e41-4ec7-8fcb-3881373fb620.png\"><br></p>";
        String data = "asdasdasd";
        Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

        List<String> result = new ArrayList<>();

        Matcher matcher = nonValidPattern.matcher(data);

        while (matcher.find()) {
            String s = Arrays.stream(matcher.group(1).split("/images/"))
                    .filter(s1 -> !s1.isEmpty())
                    .findFirst().orElse(null);
            result.add(s);
        }
        System.out.println("result = " + result);
    }
}
