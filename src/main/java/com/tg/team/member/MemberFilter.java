package com.tg.team.member;

import com.tg.team.member.Member;

public interface MemberFilter {
    boolean match(Member member);
}
