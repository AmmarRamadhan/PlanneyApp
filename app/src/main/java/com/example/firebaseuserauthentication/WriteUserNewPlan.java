package com.example.firebaseuserauthentication;

public class WriteUserNewPlan {
    private String textEvent;
    private String textAmount;
    private String textDescription;

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

    public void setTextDescription(String description) {
        this.textDescription = description;
    }

    public String getTextDescription(){
        return textDescription;
    }
}
