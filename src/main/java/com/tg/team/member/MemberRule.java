package com.tg.team.member;

import java.util.List;

public interface MemberRule {
    boolean match(List<Member> memberList, Member newMember);
}
