package com.cashonline.dto;

public class LoanDTO {

   private long id;
   private int total;
   private long userId;

    public LoanDTO(int total, long userId) {
        this.total = total;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
