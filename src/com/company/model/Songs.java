package com.company.model;

public class Songs {
    private int _id;

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public int get_id() {

        return _id;
    }

    public int getTrack() {
        return track;
    }

    public String getTitle() {
        return title;
    }

    public int getAlbum() {
        return album;
    }

    private int track;
    private String title;
    private int album;

}
