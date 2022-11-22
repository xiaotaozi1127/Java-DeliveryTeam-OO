package com.tg.team;

import com.tg.team.member.*;
import com.tg.team.story.Story;
import com.tg.team.story.StoryStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamTest {

    @Test
    public void shouldCreateTeamSuccess() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        String name = team.getName();
        assertEquals("tiangong", name);

        team.assignMember(new BA("xixi"));
        List<Member> members = team.getAllMembers();
        assertEquals(1, members.size());

        team.assignStory(new Story("drd"));
        List<Story> stories = team.getStories();
        assertEquals(1, stories.size());
        assertEquals(StoryStatus.InAnalysis, stories.get(0).getStatus());
    }

    @Test
    public void peopleCanWorkNoMatterGetAssignedOrNot() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        QA shanshan = new QA("shanshan");

        assertDoesNotThrow(xixi::work);
        assertDoesNotThrow(yanmin::work);
        assertDoesNotThrow(shanshan::work);

        team.assignMember(xixi);
        team.assignMember(xixi);
        team.assignMember(xixi);

        assertDoesNotThrow(xixi::work);
        assertDoesNotThrow(yanmin::work);
        assertDoesNotThrow(shanshan::work);
    }
}
