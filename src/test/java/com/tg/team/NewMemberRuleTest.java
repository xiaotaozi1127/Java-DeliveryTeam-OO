package com.tg.team;

import com.tg.team.member.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewMemberRuleTest {

    @Test
    public void shouldApplyNewMemberRuleForBA() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        team.addMemberRule((memberList, newMember) -> {
            int ba = newMember instanceof BA ? 1 : 0;
            for (Member person : memberList) {
                if (person instanceof BA) {
                    ba ++;
                }
            }
            return ba <= 2;
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
        team.addMemberRule((memberList, newMember) -> {
            int qa = newMember instanceof QA ? 1 : 0;
            for (Member person : memberList) {
                if (person instanceof QA) {
                    qa ++;
                }
            }
            return qa <= 1;
        });

        QA shanshan1 = new QA("shanshan1");
        QA shanshan2 = new QA("shanshan2");
        team.assignMember(shanshan1);
        assertThrows(MemberRoleExceedException.class, () -> team.assignMember(shanshan2));
    }

    @Test
    public void shouldApplyNewMemberRuleForDev() throws MemberRoleExceedException {
        Team team = new Team("tiangong");
        team.addMemberRule((memberList, newMember) -> {
            int dev = newMember instanceof Dev ? 1 : 0;
            for (Member person : memberList) {
                if (person instanceof Dev) {
                    dev ++;
                }
            }
            return dev <= 3;
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
}
