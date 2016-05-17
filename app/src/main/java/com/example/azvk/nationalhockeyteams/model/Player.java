package com.example.azvk.nationalhockeyteams.model;

public class Player {

    String name;
    String team;
    String position;
    String birthdate;
    String birthplace;
    int number;
    int weight;
    int height;
    String imageUrl;
    String national_team;

    public Player(String national_team, String name, String team, String position, String birthdate, String birthplace, int number, int weight, int height) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
        this.number = number;
        this.weight = weight;
        this.height = height;
        this.national_team = national_team;
    }

    public Player(String national_team) {
        this.national_team = national_team;
    }

    public Player(){}

    public String getImgRes() {

        return imageUrl;
    }

    public String getNational_team() {
        return national_team;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getPosition() {
        return position;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public int getNumber() {
        return number;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public void setImgRes(String imgRes) {
        this.imageUrl = imgRes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setNational_team(String national_team) {
        this.national_team = national_team;
    }
}
