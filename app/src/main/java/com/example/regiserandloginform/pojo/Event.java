package com.example.regiserandloginform.pojo;

public class Event {
    private String date;
    private String message;

    public Event(String date,String message) {
        this.date = date;
        this.message=message;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Leírás: "+message+
                "Ezen a napon: "+date;
    }
}
