package com.tg.team;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    @Test
    public void shouldCreateTeamSuccess() {
        Team team = new Team("tiangong");
        String name = team.getName();
        assertEquals("tiangong", name);

        team.assignMember(new BA("xixi"));
        List<Person> members = team.getMembers();
        assertEquals(1, members.size());

        team.assignStory(new Story(1, "drd"));
        List<Story> stories = team.getStories();
        assertEquals(1, stories.size());
        assertEquals(StoryStatus.InAnalysis, stories.get(0).getStatus());
    }

    @Test
    public void peopleCanWorkNoMatterGetAssignedOrNot() {
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

    @Test
    public void baShouldBeAbleToPrepareStory() {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        team.assignMember(xixi);

        Story drd = new Story(1, "drd");
        team.assignStory(drd);

        xixi.work();
        assertEquals(StoryStatus.ReadForDev, drd.getStatus());
    }

    @Test
    public void baShouldPrepareAtMost3StoriesOneTime() {
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
    public void baShouldBeAbleToAssignStoryToDev() {
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
    public void baShouldWorkWhenStoriesMoreThanDevs() {
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
    public void baShouldWorkWhenStoriesLessThanDevs() {
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

    @Test
    public void devCanFinishStoryCard() {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        team.assignMember(xixi);
        team.assignMember(yanmin);

        Story drd = new Story(1, "drd");
        team.assignStory(drd);

        xixi.work();
        yanmin.work();

        StoryStatus status = drd.getStatus();
        assertEquals(StoryStatus.DevDone, status);
    }

    @Test
    public void baCanAssignStoryToDevAgainWhenTheyFinishWork() {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        team.assignMember(xixi);
        team.assignMember(yanmin);

        Story drd = new Story(1, "drd");
        Story blackduck = new Story(2, "blackduck");
        team.assignStory(drd);
        team.assignStory(blackduck);

        xixi.work();
        assertEquals(drd, yanmin.getAssignedStory());
        yanmin.work();
        assertNull(yanmin.getAssignedStory());

        assertEquals(StoryStatus.DevDone, drd.getStatus());

        xixi.work();
        assertEquals(blackduck, yanmin.getAssignedStory());
        yanmin.work();
        assertEquals(StoryStatus.DevDone, blackduck.getStatus());
    }

    @Disabled
    public void shouldThrowExceptionWhenAssignTaskToAWorkingDev() {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        team.assignMember(xixi);
        team.assignMember(yanmin);

        Story drd = new Story(1, "drd");
        team.assignStory(drd);

        xixi.work();
        Story blackduck = new Story(2, "blackduck");
        team.assignStory(blackduck);

        Exception exception = assertThrows(Exception.class, xixi::work);
        assertEquals("insufficient bandwidth", exception.getMessage());
    }

    @Test
    public void qaCanTestStoryCard() {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        QA shanshan = new QA("shanshan");
        team.assignMember(xixi);
        team.assignMember(yanmin);
        team.assignMember(shanshan);

        Story drd = new Story(1, "drd");
        team.assignStory(drd);

        xixi.work();
        yanmin.work();
        shanshan.work();

        StoryStatus status = drd.getStatus();
        assertEquals(StoryStatus.TestDone, status);
    }

    @Test
    public void qaCanTestAtMost2CardsOneTime() {
        Team team = new Team("tiangong");
        BA xixi = new BA("xixi");
        Dev yanmin = new Dev("yanmin");
        Dev taohui = new Dev("taohui");
        Dev yunlong = new Dev("yunlong");
        QA shanshan = new QA("shanshan");
        team.assignMember(xixi);
        team.assignMember(yanmin);
        team.assignMember(taohui);
        team.assignMember(yunlong);
        team.assignMember(shanshan);

        Story drd = new Story(1, "drd");
        Story blackduck = new Story(2, "blackduck");
        Story bug = new Story(3, "bug");
        team.assignStory(drd);
        team.assignStory(blackduck);
        team.assignStory(bug);

        xixi.work();
        yanmin.work();
        taohui.work();
        yunlong.work();
        shanshan.work();

        List<Story> testFinished = team.getStories().stream()
                .filter(story -> story.getStatus() == StoryStatus.TestDone)
                .collect(Collectors.toList());
        assertEquals(2, testFinished.size());
    }
}
