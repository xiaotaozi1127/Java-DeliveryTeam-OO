package com.tg.team;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private final List<Person> memberList = new ArrayList<>();
    private final List<Story> storyList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public Team(String name) {
        this.name = name;
    }

    public void assignMember(Person person) {
        memberList.add(person);
    }

    public void assignStory(Story story) {
        storyList.add(story);
    }

    public List<Person> getMembers() {
        return memberList;
    }

    public List<Story> getStories() {
        return storyList;
    }
}
