package com.tg.team;

public class Story {
    private final int id;
    private final String name;
    private StoryStatus status = StoryStatus.InAnalysis;

    public Story(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }
}
