package com.tg.team.member;

import com.tg.team.story.Story;
import com.tg.team.story.StoryStatus;
import com.tg.team.Team;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Story> readyForQA = team.getStories().stream()
                .filter(story -> story.getStatus() == StoryStatus.ReadyForQA)
                .limit(2)
                .collect(Collectors.toList());
        readyForQA.forEach(story -> story.setStatus(StoryStatus.TestDone));
    }
}
