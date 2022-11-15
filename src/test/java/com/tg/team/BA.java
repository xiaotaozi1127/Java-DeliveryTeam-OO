package com.tg.team;

import java.util.List;
import java.util.stream.Collectors;

public class BA extends Person {
    public BA(String name) {
        super(name);
    }

    @Override
    public void work() {
        Team team = getTeam();
        List<Person> members =
                team.getMembers();
        List<Story> stories = team.getStories();
        List<Story> notReady = stories.stream().filter(story -> story.getStatus() == StoryStatus.InAnalysis).collect(Collectors.toList());
        notReady.forEach(story -> story.setStatus(StoryStatus.ReadForDev));
    }
}
