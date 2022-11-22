package com.tg.team;

import java.util.ArrayList;
import java.util.List;

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

    public void assignMember(Member person) throws MemberRoleExceedException {
        for (MemberRule rule : ruleList) {
            if (!rule.apply(memberList, person)) {
                throw new MemberRoleExceedException();
            }
        }
        memberList.add(person);
        person.setTeam(this);
    }

    public void assignStory(Story story) {
        storyList.add(story);
    }

    public List<Member> getMembers(MemberFilter filter) {
       List<Member> filteredResult = new ArrayList<>();

       memberList.forEach(member -> {
           if (filter.match(member)) {
               filteredResult.add(member);
           }
       });
       return filteredResult;
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
