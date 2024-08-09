package com.example.quotesapp;

public class ViewModel {
    private String quotes;

    public ViewModel(String quotes) {
        this.quotes = quotes;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
