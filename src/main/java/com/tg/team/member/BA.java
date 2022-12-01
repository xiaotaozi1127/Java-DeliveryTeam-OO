package com.tg.team.member;

import com.tg.team.story.Story;
import com.tg.team.story.StoryStatus;
import com.tg.team.Team;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BA extends Member {
    public BA(String name) {
        super(name);
    }

    public void createStory(Story story) {
        Team team = getTeam();
        if (team == null) {
            return;
        }
        team.assignStory(story);
    }

    @Override
    public void work() {
        Team team = getTeam();
        if (team == null) {
            return;
        }
        team.getStories().stream()
                .filter(story -> story.getStatus() == StoryStatus.InAnalysis)
                .limit(3)
                .forEach(story -> story.setStatus(StoryStatus.ReadForDev));

        List<Story> readyForDevStories = team.getStories().stream()
                .filter(story -> story.getStatus() == StoryStatus.ReadForDev)
                .collect(Collectors.toList());
        List<Dev> availableDevs = team.getAllMembers().stream()
                .filter(member -> member instanceof Dev && ((Dev) member).getAssignedStory() == null)
                .map(member -> (Dev)member)
                .collect(Collectors.toList());
        if (!availableDevs.isEmpty() && !readyForDevStories.isEmpty()) {
            int canAssignNum = Math.min(availableDevs.size(), readyForDevStories.size());
            assignTask(availableDevs, readyForDevStories, canAssignNum);
        }
    }

    private void assignTask(List<Dev> availableDevs, List<Story> readyForDevStories, int size) {
        IntStream.range(0, size).forEach(i -> availableDevs.get(i).setAssignedStory(readyForDevStories.get(i)));
    }
}
