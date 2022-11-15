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
        List<Story> availableStories = team.getStories().stream().filter(story -> story.getStatus() == StoryStatus.InAnalysis).collect(Collectors.toList());
        availableStories.forEach(story -> story.setStatus(StoryStatus.ReadForDev));
        List<Person> availableDevs = team.getMembers().stream().filter(person -> person instanceof Dev && ((Dev) person).getAssignedStory() == null)
                .collect(Collectors.toList());
        if (!availableDevs.isEmpty() && !availableStories.isEmpty()) {
            assignTask((Dev) availableDevs.get(0), availableStories.get(0));
        }

    }

    private void assignTask(Dev dev, Story story) {
        dev.setAssignedStory(story);
    }
}
