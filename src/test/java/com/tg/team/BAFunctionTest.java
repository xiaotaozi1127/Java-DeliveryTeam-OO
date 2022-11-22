package com.tg.team;

import com.tg.team.member.BA;
import com.tg.team.member.Dev;
import com.tg.team.member.MemberRoleExceedException;
import com.tg.team.story.Story;
import com.tg.team.story.StoryStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BAFunctionTest {
    @Test
    public void baShouldBeAbleToPrepareStory() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        team.assignMember(xixi);

        Story drd = new Story(1, "drd");
        team.assignStory(drd);

        xixi.work();
        assertEquals(StoryStatus.ReadForDev, drd.getStatus());
    }

    @Test
    public void baShouldBeAbleToCreateStory() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        team.assignMember(xixi);

        Story drd = new Story(1, "drd");
        xixi.createStory(drd);
        assertEquals(StoryStatus.ReadForDev, drd.getStatus());

        List<Story> stories = team.getStories();
        assertEquals(1, stories.size());
        assertEquals(drd, stories.get(0));
    }

    @Test
    public void baShouldPrepareAtMost3StoriesOneTime() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        team.assignMember(xixi);

        for(int i = 1; i < 5; i++ ) {
            Story story = new Story(i, "story" + i);
            team.assignStory(story);
        }
        xixi.work();

        List<Story> availableStories =
                team.getStories().stream().filter(story -> story.getStatus() == StoryStatus.ReadForDev)
                        .collect(Collectors.toList());
        assertEquals(3, availableStories.size());
    }

    @Test
    public void baShouldBeAbleToAssignStoryToDev() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        team.assignMember(xixi);
        team.assignMember(yanmin);
        Story drd = new Story(1, "drd");
        team.assignStory(drd);

        xixi.work();

        assertEquals(StoryStatus.ReadForDev, drd.getStatus());
        assertEquals(drd, yanmin.getAssignedStory());
    }

    @Test
    public void baShouldAssignStoriesToAvailableDevs() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        Dev taohui = new Dev("taohui");
        team.assignMember(xixi);
        team.assignMember(yanmin);
        team.assignMember(taohui);
        Story drd = new Story(1, "drd");
        Story blackduck = new Story(2, "blackduck");
        Story bug = new Story(3, "bug");
        team.assignStory(drd);
        team.assignStory(blackduck);
        team.assignStory(bug);

        xixi.work();

        assertEquals(StoryStatus.ReadForDev, drd.getStatus());
        assertEquals(StoryStatus.ReadForDev, blackduck.getStatus());
        assertEquals(StoryStatus.ReadForDev, bug.getStatus());
        assertEquals(drd, yanmin.getAssignedStory());
        assertEquals(blackduck, taohui.getAssignedStory());
    }

    @Test
    public void baShouldWorkWhenStoriesLessThanDevs() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        Dev taohui = new Dev("taohui");
        Dev yunlong = new Dev("yunlong");
        team.assignMember(xixi);
        team.assignMember(yanmin);
        team.assignMember(taohui);
        team.assignMember(yunlong);

        Story drd = new Story(1, "drd");
        Story blackduck = new Story(2, "blackduck");
        team.assignStory(drd);
        team.assignStory(blackduck);

        xixi.work();

        assertEquals(StoryStatus.ReadForDev, drd.getStatus());
        assertEquals(StoryStatus.ReadForDev, blackduck.getStatus());
        assertEquals(drd, yanmin.getAssignedStory());
        assertEquals(blackduck, taohui.getAssignedStory());
        assertNull(yunlong.getAssignedStory());
    }
}
