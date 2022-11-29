package com.example.firebaseuserauthentication;

public class WriteUserMyPlan {
    private String textEvent;
    private String textAmount;
    private String textDescription;
    private String textDateFrom;
    private String textDateUntil;

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

    public String getTextDateFrom(){
        return textDateFrom;
    }

    public void setTextDateFrom(String pick_date_from) {
        this.textDateFrom = pick_date_from;
    }

    public String getTextDateUntil(){
        return textDateUntil;
    }

    public void setTextDateUntil(String pick_date_until) {
        this.textDateUntil = pick_date_until;
    }
}
