package com.example.firebaseuserauthentication;

public class WriteUserNewPlan {
    private String textEvent;
    private String textAmount;
    private String textDescription;
    private String textDate;
    private String textTransaction;

    public String getTextEvent() {
        return textEvent;
    }

    public void setTextEvent(String event) {
        this.textEvent = event;
    }

    public String getTextAmount() {
        return textAmount;
    }

    public void setTextAmount(String amount) {
        this.textAmount = amount;
    }

    public String getTextDescription(){
        return textDescription;
    }

    public void setTextDescription(String description) {
        this.textDescription = description;
    }

    public String getTextDate(){
        return textDate;
    }

    public void setTextDate(String pick_date) {
        this.textDate = pick_date;
    }

    public String getTextTransaction(){
        return textTransaction;
    }

    public void setTextTransaction(String transaction) {
        this.textTransaction = transaction;
    }
}
