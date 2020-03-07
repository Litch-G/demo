package com.example.demo.DTO;

import java.util.ArrayList;
import java.util.List;
//分页中间信息传递
public class PaginationDTO {
    private List<DataPublishDTO> dataPublishDTOList;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataPublishDTO> getDataPublishDTOList() {
        return dataPublishDTOList;
    }

    public void setDataPublishDTOList(List<DataPublishDTO> dataPublishDTOList) {
        this.dataPublishDTOList = dataPublishDTOList;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    private List<Integer>pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        //初始化总页数

        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else {
            totalPage = (totalCount/size)+1;
        }

        this.page = page;
        //页面层级显示
        pages.add(page);
        for(int i = 1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //上下页显示
        if(page==1){
            showPrevious = false;
        }else showPrevious = true;
        if (page == totalCount){
            showNext = false;
        }else showNext = true;

        //第一最后一页显示
        if(pages.contains(1)){
            showFirstPage=false;
        }else showFirstPage=true;
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else showEndPage=true;
    }
}
