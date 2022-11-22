package com.tg.team.member;

import com.tg.team.story.Story;
import com.tg.team.story.StoryStatus;

public class Dev extends Member {
    private Story assignedStory;

    public Dev(String name) {
        super(name);
    }

    @Override
    public void work() {
        if (assignedStory != null) {
            assignedStory.setStatus(StoryStatus.ReadyForQA);
            assignedStory = null;
        }
    }

    public Story getAssignedStory() {
        return assignedStory;
    }

    public void setAssignedStory(Story assignedStory) {
        this.assignedStory = assignedStory;
    }

}
