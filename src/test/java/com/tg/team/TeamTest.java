package com.tg.team;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    @Test
    public void shouldCreateTeamSuccess() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        String name = team.getName();
        assertEquals("tiangong", name);

        team.assignMember(new BA("xixi"));
        List<Person> members = team.getAllMembers();
        assertEquals(1, members.size());

        team.assignStory(new Story(1, "drd"));
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

    @Test
    public void devCanFinishStoryCard() throws MemberRoleExceedException {
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
        assertEquals(StoryStatus.ReadyForQA, status);
    }

    @Test
    public void baCanAssignStoryToDevAgainWhenTheyFinishWork() throws MemberRoleExceedException {
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

        assertEquals(StoryStatus.ReadyForQA, drd.getStatus());

        xixi.work();
        assertEquals(blackduck, yanmin.getAssignedStory());
        yanmin.work();
        assertEquals(StoryStatus.ReadyForQA, blackduck.getStatus());
    }

    @Disabled
    public void shouldThrowExceptionWhenAssignTaskToAWorkingDev() throws MemberRoleExceedException {
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
    public void qaCanTestStoryCard() throws MemberRoleExceedException {
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
    public void qaCanTestAtMost2CardsOneTime() throws MemberRoleExceedException {
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

    @Test
    public void shouldFilterMembersAccordingToCondition() throws MemberRoleExceedException {
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

        List<Person> ba = team.getMembers(member -> member instanceof BA);
        assertEquals(1, ba.size());
        assertEquals(xixi, ba.get(0));

        List<Person> devs = team.getMembers(member -> member instanceof Dev);
        assertEquals(3, devs.size());
        assertAll("filter devs",
                () -> assertTrue(devs.contains(yanmin)),
                () -> assertTrue(devs.contains(taohui)),
                () -> assertTrue(devs.contains(yunlong)));

        List<Person> qa = team.getMembers(member -> member instanceof QA);
        assertEquals(1, qa.size());
        assertEquals(shanshan, qa.get(0));

        List<Person> byName = team.getMembers(member -> member.getName().contains("a"));
        assertEquals(3, byName.size());
        assertAll("filter by name",
                () -> assertTrue(byName.contains(yanmin)),
                () -> assertTrue(byName.contains(taohui)),
                () -> assertTrue(byName.contains(shanshan)));
    }

    @Test
    public void shouldApplyBaRuleForMaxNumber() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        team.addMemberRule(new MemberRule() {
            @Override
            public boolean apply(List<Person> memberList, Person newMember) {
                int ba = newMember instanceof BA ? 1 : 0;
                for (Person person : memberList) {
                    if (person instanceof BA) {
                        ba ++;
                    }
                }
                return ba <= 2;
            }
        });
        team.addMemberRule(new MemberRule() {
            @Override
            public boolean apply(List<Person> memberList, Person newMember) {
                int qa = newMember instanceof QA ? 1 : 0;
                for (Person person : memberList) {
                    if (person instanceof QA) {
                        qa ++;
                    }
                }
                return qa <= 1;
            }
        });
        team.addMemberRule(new MemberRule() {
            @Override
            public boolean apply(List<Person> memberList, Person newMember) {
                int dev = newMember instanceof Dev ? 1 : 0;
                for (Person person : memberList) {
                    if (person instanceof Dev) {
                        dev ++;
                    }
                }
                return dev <= 3;
            }
        });
        BA xixi1 = new BA("xixi1");
        BA xixi2 = new BA("xixi2");
        BA xixi3 = new BA("xixi3");
        team.assignMember(xixi1);
        team.assignMember(xixi2);
        assertThrows(MemberRoleExceedException.class, () -> team.assignMember(xixi3));

        QA shanshan1 = new QA("shanshan1");
        QA shanshan2 = new QA("shanshan2");
        team.assignMember(shanshan1);
        assertThrows(MemberRoleExceedException.class, () -> team.assignMember(shanshan2));

        Dev yanmin = new Dev("yanmin");
        Dev taohui = new Dev("taohui");
        Dev yunlong = new Dev("yunlong");
        Dev haotian = new Dev("haotian");
        team.assignMember(yanmin);
        team.assignMember(taohui);
        team.assignMember(yunlong);
        assertThrows(MemberRoleExceedException.class, () -> team.assignMember(haotian));
    }
}
