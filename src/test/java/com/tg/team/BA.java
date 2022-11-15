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
        if (team == null) {
            return;
        }
        List<Story> availableStories = team.getStories().stream()
                .filter(story -> story.getStatus() == StoryStatus.InAnalysis)
                .limit(3)
                .collect(Collectors.toList());

        availableStories.forEach(story -> story.setStatus(StoryStatus.ReadForDev));
        List<Dev> availableDevs = team.getMembers().stream().filter(person -> person instanceof Dev && ((Dev) person).getAssignedStory() == null)
                .map(person -> (Dev)person)
                .collect(Collectors.toList());
        if (!availableDevs.isEmpty() && !availableStories.isEmpty()) {
            int canAssignNum = Math.min(availableDevs.size(), availableStories.size());
            assignTask(availableDevs, availableStories, canAssignNum);
        }
    }

    private void assignTask(List<Dev> availableDevs, List<Story> availableStories, int size) {
        for (int i = 0; i < size; i++) {
            availableDevs.get(i).setAssignedStory(availableStories.get(i));
        }
    }
}
