package com.tg.team.story;

public class Story {
    private final String name;
    private StoryStatus status = StoryStatus.InAnalysis;

    public Story(String name) {
        this.name = name;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
}
