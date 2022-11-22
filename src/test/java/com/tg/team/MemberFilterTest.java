package com.tg.team;

import com.tg.team.member.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberFilterTest {
    static Team team;
    static BA xixi;
    static Dev yanmin;
    static Dev taohui;
    static Dev yunlong;
    static QA shanshan;

    @BeforeAll
    public static void init() throws MemberRoleExceedException {
        team = new Team("tiangong");
        xixi = new BA("xixi");
        yanmin = new Dev("yanmin");
        taohui = new Dev("taohui");
        yunlong = new Dev("yunlong");
        shanshan = new QA("shanshan");
        team.assignMember(xixi);
        team.assignMember(yanmin);
        team.assignMember(taohui);
        team.assignMember(yunlong);
        team.assignMember(shanshan);
    }

    @Test
    public void shouldFilterBAMembers() {
        List<Member> ba = team.getMembers(member -> member instanceof BA);
        assertEquals(1, ba.size());
        assertEquals(xixi, ba.get(0));
    }

    @Test
    public void shouldFilterDevMembers(){
        List<Member> devs = team.getMembers(member -> member instanceof Dev);
        assertEquals(3, devs.size());
        assertAll("filter devs",
                () -> assertTrue(devs.contains(yanmin)),
                () -> assertTrue(devs.contains(taohui)),
                () -> assertTrue(devs.contains(yunlong)));
    }

    @Test
    public void shouldFilterQAMembers() {
        List<Member> qa = team.getMembers(member -> member instanceof QA);
        assertEquals(1, qa.size());
        assertEquals(shanshan, qa.get(0));
    }

    @Test
    public void shouldFilterMembersByName() {
        List<Member> byName = team.getMembers(member -> member.getName().contains("a"));
        assertEquals(3, byName.size());
        assertAll("filter by name",
                () -> assertTrue(byName.contains(yanmin)),
                () -> assertTrue(byName.contains(taohui)),
                () -> assertTrue(byName.contains(shanshan)));
    }
}
