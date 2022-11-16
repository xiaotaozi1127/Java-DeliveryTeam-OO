package com.tg.team;

import java.util.List;
import java.util.stream.Collectors;

public class QA extends Person {
    public QA(String name) {
        super(name);
    }

    @Override
    public void work() {
        Team team = getTeam();
        if (team == null) {
            return;
        }
        List<Story> readyForQA = team.getStories().stream()
                .filter(story -> story.getStatus() == StoryStatus.ReadyForQA)
                .limit(2)
                .collect(Collectors.toList());
        readyForQA.forEach(story -> story.setStatus(StoryStatus.TestDone));
    }
}
