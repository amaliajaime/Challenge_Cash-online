package com.cashonline.dto;


import java.util.List;

public class PageDTO {

    private List<LoanDTO> items;
    private Integer page;
    private Integer size;
    private Long total;

    public PageDTO(List<LoanDTO> items, Integer page, Integer size, Long total) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public List<LoanDTO> getItems() {
        return items;
    }

    public void setItems(List<LoanDTO> items) {
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}


