package com.tg.team;

public abstract class Member {
    private final String name;
    private Team team;

    public Member(String name) {
        this.name = name;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract void work();

    public String getName() {
        return name;
    }
}
