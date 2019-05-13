package com.example.regiserandloginform.pojo;

public class EventInfo {
    private User user;
    private Event event;
    private PointerLocation location;

    public EventInfo(User user, Event event, PointerLocation location) {
        this.user = user;
        this.event = event;
        this.location=location;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public PointerLocation getLocation(){
        return location;
    }

    @Override
    public String toString() {
        return "\nFelhasználónév:\n"+getUser().getUsername()+
                "\nLeírás:\n"+getEvent().getMessage()+
                "\nItt:\n"+getLocation().toString();
    }
}
