package com.company.model;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if (!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
        }
        List<Artist> artists = datasource.queryArtists(3);
        if (artists == null) {
            System.out.println("No Artist!");
            return;
        }
        for (Artist artist : artists) {
                System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
                }

                List<String> albumsForArtist = datasource.queryAlbumsFromArtist("Carole King", Datasource.ORDER_BY_ASC);

        for(String album: albumsForArtist){

        System.out.println(album);
        }


        List<SongArtist> songArtists = datasource.queryArtistForSongString("Go Your Own Way", Datasource.ORDER_BY_ASC);
        if(songArtists == null) {
            System.out.println("couldn't find");
            return;
        }

        for(SongArtist artist : songArtists) {
            System.out.println("art name = " + artist.getArtistName() +
            "album name = " + artist.getAlbumName() + " track = " + artist.getTrack());
        }

        datasource.close();
        }
        }
