package com.study.board;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

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
}
