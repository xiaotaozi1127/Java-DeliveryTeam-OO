package com.tg.team;

import com.tg.team.member.BA;
import com.tg.team.member.Dev;
import com.tg.team.member.MemberRoleExceedException;
import com.tg.team.story.Story;
import com.tg.team.story.StoryStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DevFunctionTest {

    @Test
    public void devCanFinishStoryCard() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        team.assignMember(xixi);
        team.assignMember(yanmin);

        Story drd = new Story("drd");
        team.assignStory(drd);

        xixi.work();
        yanmin.work();

        StoryStatus status = drd.getStatus();
        assertEquals(StoryStatus.ReadyForQA, status);
    }

    @Test
    public void baCanAssignStoryToDevAgainWhenTheyFinishWork() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        team.assignMember(xixi);
        team.assignMember(yanmin);

        Story drd = new Story("drd");
        Story blackduck = new Story("blackduck");
        team.assignStory(drd);
        team.assignStory(blackduck);

        xixi.work();
        assertEquals(drd, yanmin.getAssignedStory());
        yanmin.work();
        assertNull(yanmin.getAssignedStory());

        assertEquals(StoryStatus.ReadyForQA, drd.getStatus());

        xixi.work();
        assertEquals(blackduck, yanmin.getAssignedStory());
        yanmin.work();
        assertEquals(StoryStatus.ReadyForQA, blackduck.getStatus());
    }
}
