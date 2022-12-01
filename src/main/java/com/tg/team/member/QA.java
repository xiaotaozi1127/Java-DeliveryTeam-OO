package com.tg.team.member;

import com.tg.team.Team;
import com.tg.team.story.StoryStatus;

public class QA extends Member {
    public QA(String name) {
        super(name);
    }

    @Override
    public void work() {
        Team team = getTeam();
        if (team == null) {
            return;
        }
        team.getStories().stream()
                .filter(story -> story.getStatus() == StoryStatus.ReadyForQA)
                .limit(2)
                .forEach(story -> story.setStatus(StoryStatus.TestDone));
    }
}
