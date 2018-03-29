package com.company.model;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if (!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
        }
        List<Artist> artists = datasource.queryArtists(0);
        if (artists == null) {
            System.out.println("No Artist!");
            return;
        }
        for (Artist artist : artists) {
                System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
                }
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter name");
        String title = scanner.nextLine();
                List<String> albumsForArtist = datasource.queryAlbumsFromArtist(title, Datasource.ORDER_BY_ASC);

        for(String album: albumsForArtist){

        System.out.println(album);

    }




        datasource.close();
        }
        }
