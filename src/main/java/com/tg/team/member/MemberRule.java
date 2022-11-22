package com.tg.team.member;

import com.tg.team.member.Member;

import java.util.List;

public interface MemberRule {
    boolean apply(List<Member> memberList, Member newMember);
}
