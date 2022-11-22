package com.tg.team;

import com.tg.team.member.BA;
import com.tg.team.member.Dev;
import com.tg.team.member.MemberRoleExceedException;
import com.tg.team.member.QA;
import com.tg.team.story.Story;
import com.tg.team.story.StoryStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QAFunctionTest {
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
}
