package com.study.board.web.dto.boarddto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PaginationDto {

    private int startPage;
    private int endPage;
    private int curPage;
    private boolean hasPrev;
    private boolean hasNext;

    @Builder
    public PaginationDto(int startPage, int endPage, int curPage, boolean hasPrev, boolean hasNext) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.curPage = curPage;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
    }
}
