package com.tg.team;

import com.tg.team.member.Member;
import com.tg.team.member.MemberFilter;
import com.tg.team.member.MemberRoleExceedException;
import com.tg.team.member.MemberRule;
import com.tg.team.story.Story;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Team {
    private final String name;
    private final List<Member> memberList = new ArrayList<>();
    private final List<Story> storyList = new ArrayList<>();
    private final List<MemberRule> ruleList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Team(String name) {
        this.name = name;
    }

    public void assignMember(Member member) throws MemberRoleExceedException {
        if (!ruleList.stream().allMatch(r -> r.match(this, member))) {
            throw new MemberRoleExceedException();
        }
        memberList.add(member);
        member.setTeam(this);
    }

    public void assignStory(Story story) {
        storyList.add(story);
    }

    public List<Member> getMembers(MemberFilter filter) {
       return memberList.stream().filter(filter::match).collect(Collectors.toList());
    }

    public List<Member> getAllMembers() {
        return memberList;
    }

    public List<Story> getStories() {
        return storyList;
    }

    public void addMemberRule(MemberRule rule) {
        ruleList.add(rule);
    }
}
