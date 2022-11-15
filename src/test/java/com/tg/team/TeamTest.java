package com.tg.team;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamTest {

    @Test
    public void shouldCreateTeamSuccess() {
        Team team = new Team("tiangong");
        String name = team.getName();
        assertEquals("tiangong", name);

        team.assignMember(new BA("xixi"));
        List<Person> members = team.getMembers();
        assertEquals(1, members.size());

        team.assignStory(new Story(123, "drd"));
        List<Story> stories = team.getStories();
        assertEquals(1, stories.size());
        assertEquals(StoryStatus.InAnalysis, stories.get(0).getStatus());
    }

    @Test
    public void baShouldBeAbleToPrepareStory() {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        team.assignMember(xixi);
        Story drd = new Story(123, "drd");
        team.assignStory(drd);
        xixi.work();
        assertEquals(StoryStatus.ReadForDev, drd.getStatus());
    }
}
