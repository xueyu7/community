package com.xy.community.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer page;
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer size, Integer page) {
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }

        this.page = page;

        //当前页左3右3
        for (int i = page - 3; i <= page + 3; i++) {
            if (i > 0 && i <= totalPage) {
                pages.add(i);
            }
        }

        //是否展示上一页
        if (page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }

        //是否展示下一页
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }

        //pages中没有第一页则“显示第一页”=> "<<"
        if (pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        //pages中没有最后一页则“显示最后一页”=> ">>"
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }

    }
}
