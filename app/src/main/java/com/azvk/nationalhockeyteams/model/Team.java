package com.azvk.nationalhockeyteams.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Team extends RealmObject{

    @PrimaryKey
    private String name;
    private String flag;
    private String head_coach;
    private String captain;
    private String url;
    private String header_pic;

    public Team(String name, String flag, String head_coach, String captain, String url, String header_pic) {
        this.name = name;
        this.head_coach = head_coach;
        this.captain = captain;
        this.url = url;
        this.header_pic = header_pic;
    }

    public Team(){}

    public String getImgRes() {

        return flag;
    }

    public String getImgResHeader() {

        return header_pic;
    }
    public String getName() {
        return name;
    }


    public String getHead_coach() {
        return head_coach;
    }

    public String getCaptain() {
        return captain;
    }

    public void setName(String name) {
        this.name = name;
    }
}
