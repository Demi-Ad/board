package com.study.board.web.util;

import com.study.board.web.dto.boarddto.PaginationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaginationUtil {

    public PaginationDto of(Long totalCount, int showOnePagePagingBlock, int showOnePagePost, final int currPage) {

        int tempEndPage = ((int) Math.ceil(totalCount.intValue() / ((double) showOnePagePost)));
        int endPage = ((int) Math.ceil(currPage / ((double) showOnePagePagingBlock)) * showOnePagePagingBlock);
        endPage = Math.min(endPage, tempEndPage);
        int startPage = (endPage - showOnePagePagingBlock) + 1;
        startPage = startPage < 0 ? 1 : startPage;

        if (tempEndPage == currPage) {
            startPage = tempEndPage;
            endPage = tempEndPage;
        }

        boolean isPrev = startPage != 1;
        boolean isNext = currPage < tempEndPage;


        return PaginationDto.builder()
                .startPage(startPage)
                .endPage(endPage)
                .curPage(currPage)
                .hasPrev(isPrev)
                .hasNext(isNext)
                .build();
    }
}
