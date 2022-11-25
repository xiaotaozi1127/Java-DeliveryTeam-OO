package com.tg.team.member;

import com.tg.team.Team;

public interface MemberRule {
    boolean match(Team team, Member newMember);
}
