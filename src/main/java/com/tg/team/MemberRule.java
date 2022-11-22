package com.tg.team;

import java.util.List;

public interface MemberRule {
    boolean apply(List<Member> memberList, Member newMember);
}
