package com.tg.team;

public abstract class Person {
    private final String name;
    private Team team;

    public Person(String name) {
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
