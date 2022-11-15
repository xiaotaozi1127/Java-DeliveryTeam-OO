package com.tg.team;

public class Dev extends Person {
    private Story assignedStory;

    public Dev(String name) {
        super(name);
    }

    @Override
    public void work() {
        if (assignedStory != null) {
            assignedStory.setStatus(StoryStatus.DevDone);
        }
    }

    public Story getAssignedStory() {
        return assignedStory;
    }

    public void setAssignedStory(Story assignedStory) {
        this.assignedStory = assignedStory;
    }

}
