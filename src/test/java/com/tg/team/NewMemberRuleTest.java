package com.tg.team;

import com.tg.team.member.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewMemberRuleTest {

    @Test
    public void shouldApplyNewMemberRuleForBA() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        team.addMemberRule((t, newMember) -> {
            int ba = newMember instanceof BA ? 1 : 0;
            return t.getMembers(m -> m instanceof BA).size() + ba <= 2;
        });

        BA xixi1 = new BA("xixi1");
        BA xixi2 = new BA("xixi2");
        BA xixi3 = new BA("xixi3");
        team.assignMember(xixi1);
        team.assignMember(xixi2);
        assertThrows(MemberRoleExceedException.class, () -> team.assignMember(xixi3));
    }

    @Test
    public void shouldApplyNewMemberRuleForQA() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        team.addMemberRule((t, newMember) -> {
            int qa = newMember instanceof QA ? 1 : 0;
            return t.getMembers(m -> m instanceof QA).size() + qa <= 1;
        });

        QA shanshan1 = new QA("shanshan1");
        QA shanshan2 = new QA("shanshan2");
        team.assignMember(shanshan1);
        assertThrows(MemberRoleExceedException.class, () -> team.assignMember(shanshan2));
    }

    @Test
    public void shouldApplyNewMemberRuleForDev() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        team.addMemberRule((t, newMember) -> {
            int dev = newMember instanceof Dev ? 1 : 0;
            return t.getMembers(m -> m instanceof Dev).size() + dev <= 3;
        });

        Dev yanmin = new Dev("yanmin");
        Dev taohui = new Dev("taohui");
        Dev yunlong = new Dev("yunlong");
        Dev haotian = new Dev("haotian");
        team.assignMember(yanmin);
        team.assignMember(taohui);
        team.assignMember(yunlong);

        assertThrows(MemberRoleExceedException.class, () -> team.assignMember(haotian));
    }

    @Test
    public void canAddAnyNumberMemberWhenThereIsNoRule() {
        Team team = new Team("tiangong");

        Dev yanmin = new Dev("yanmin");
        Dev taohui = new Dev("taohui");
        Dev yunlong = new Dev("yunlong");
        Dev haotian = new Dev("haotian");

        BA xixi1 = new BA("xixi1");
        BA xixi2 = new BA("xixi2");
        BA xixi3 = new BA("xixi3");

        QA shanshan1 = new QA("shanshan1");
        QA shanshan2 = new QA("shanshan2");

        assertDoesNotThrow(()->team.assignMember(yanmin));
        assertDoesNotThrow(()->team.assignMember(taohui));
        assertDoesNotThrow(()->team.assignMember(yunlong));
        assertDoesNotThrow(()->team.assignMember(haotian));

        assertDoesNotThrow(()->team.assignMember(xixi1));
        assertDoesNotThrow(()->team.assignMember(xixi2));
        assertDoesNotThrow(()->team.assignMember(xixi3));

        assertDoesNotThrow(()->team.assignMember(shanshan1));
        assertDoesNotThrow(()->team.assignMember(shanshan2));
    }
}
