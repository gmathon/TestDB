package com.company.model;

public class Albums {
    private int _id;

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(int artist) {
        this.artist = artist;
    }

    public int get_id() {

        return _id;
    }

    public String getName() {
        return name;
    }

    public int getArtist() {
        return artist;
    }

    private String name;
    private int artist;
}
