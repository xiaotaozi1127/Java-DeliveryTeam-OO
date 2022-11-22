package com.tg.team;

import java.util.List;

public interface MemberRule {
    boolean apply(List<Person> memberList, Person newMember);
}
