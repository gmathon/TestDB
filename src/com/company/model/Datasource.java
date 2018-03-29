package com.company.model;

import sun.security.provider.PolicySpiFile;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Student\\Downloads\\Databases-The-Music-SQLite-Database-Source-code\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COlUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;


    public static final String TABLE_SONGS = "songs";
    public static final String COlUMN_SONGS_ID = "_id";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";
    public static final int INDEX_SONGS_ID = 1;
    public static final int INDEX_SONGS_TRACK = 2;
    public static final int INDEX_SONGS_TITLE = 3;
    public static final int INDEX_SONGS_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String QUERY_ALBUMS_BY_ARTIST_START = "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS +
            " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " WHERE " + TABLE_ARTISTS +
            "." + COLUMN_ARTIST_NAME + " = \"";

    public static final String QUERY_ALBUMS_BY_ARTIST_SORT = " ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ARTIST_FOR_SONG_START =
            "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + "." + COLUMN_SONGS_TRACK + " FROM " + TABLE_SONGS +
                    " INNER JOIN " + TABLE_ALBUMS + " ON " +
                    TABLE_SONGS + "." + COLUMN_SONGS_ALBUM + " = " + TABLE_ALBUMS + '.' + COlUMN_ALBUM_ID +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
                    " WHERE " + TABLE_SONGS + "." + COLUMN_SONGS_TITLE + " = \"";

    public static final String QUERY_ARTIST_FOR_SONG_SORT =
            " ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

//    public static final String QUERY_VIEW_SONG_INFO_PREP = "SELECT " + COLUMN_ARTIST_NAME + ", " + COLUMN_SONGS_ALBUM +
//            ", " + COLUMN_SONGS_TRACK + " FROM "  + TABLE_ARTISTS_SONG_VIEW
    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS + "(" + COLUMN_ARTIST_NAME + ") VALUES(?)";
    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS + "(" +COLUMN_ALBUM_NAME + "," + COLUMN_ALBUM_ARTIST + ") VALUES(?, ?)";
    public static final String INSER_SONG = "INSERT INTO " + TABLE_SONGS + "(" + COLUMN_SONGS_TRACK + "," + COLUMN_SONGS_TITLE + "," +COLUMN_SONGS_ALBUM + ") VALUES(?, ? ,?)";


    public Connection conn;

    private PreparedStatement insertintoArtists;
    private PreparedStatement insertinoalbums;
    private PreparedStatement insertintosongs;
    public boolean open() {

        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
            insertintoArtists = conn.prepareStatement(INSERT_ARTIST,Statement.RETURN_GENERATED_KEYS);
            insertinoalbums = conn.prepareStatement(INSERT_ALBUMS,Statement.RETURN_GENERATED_KEYS);
            insertintosongs = conn.prepareStatement(INSER_SONG);
            insertintoArtists = conn.prepareStatement(I)

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }catch (Exception e2){
            System.out.println(e2.getMessage());
        }


        return false;
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("couldn't close connection: " + e.getMessage());
        }

    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE
                ) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE");
            if (sortOrder == ORDER_BY_ASC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        Statement statement = null;
        ResultSet results = null;
        try {
            statement = conn.createStatement();

            results = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS);


            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new Artist();
                artist.setId(results.getInt(COLUMN_ARTIST_ID));
                artist.setName(results.getString(COLUMN_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;


        } catch (SQLException e) {
            System.out.println("Query failed; " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
            } catch (SQLException e) {
                System.out.println("error closing resultset; " + e.getMessage());
            }

            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                System.out.println("Error closing statement " + e.getMessage());

            }
        }

    }

    public List<String> queryAlbumsFromArtist(String artistName, int sortOrder) {
        StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);
        sb.append(artistName);
        sb.append("\"");
//        sb.append(TABLE_ALBUMS);
//        sb.append('.');
//        sb.append(COLUMN_ALBUM_NAME);
//        sb.append(" FROM ");
//        sb.append(TABLE_ALBUMS);
//        sb.append(" INNER JOIN ");
//        sb.append(TABLE_ARTIST);
//        sb.append(" on ");
//        sb.append(TABLE_ALBUMS);
//        sb.append('.');
//        sb.append(COLUMN_ALBUM_ARTIST);
//        sb.append(" = ");
//        sb.append(TABLE_ARTIST);
//        sb.append('.');
//        sb.append(COLUMN_ARTIST_ID);
//        sb.append("WHERE");
//        sb.append(TABLE_ARTIST);
//        sb.append(COLUMN_ARTIST_NAME);
//        sb.append(" = \"");
//        sb.append(artistName);
//        sb.append("\"");

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
//            sb.append(" ORDER BY ");
//            sb.append(TABLE_ALBUMS);
//            sb.append('.');
//            sb.append(TABLE_ALBUMS);
//            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }


        }
        System.out.println(" SQL STATEMENT = " + sb.toString());

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<String> albums = new ArrayList<>();
            while (results.next()) {
                albums.add(results.getString(1));
            }
            return albums;

        } catch (SQLException e) {
            System.out.println("QUERY FAILED " + e.getMessage());
            return null;
        }


    }

        public List<SongArtist> queryArtistForSongString (String songName, int sortOrder) {

        StringBuilder sb = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
        sb.append(songName);
        sb.append("\"");

        if(sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ARTIST_FOR_SONG_SORT);
            if(sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            }
            else {
                sb.append("ASC");
            }
        }
        System.out.println("SQL Statement: " + sb.toString());
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<SongArtist> songArtists = new ArrayList<>();

            while (results.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack(results.getInt(3));
                songArtists.add(songArtist);

            }

        }
        catch (SQLException e) {
            System.out.println("Query fails " + e.getMessage());
            e.printStackTrace();

        }
        return null;
    }
    private int insertArtist(String name) throws SQLException{
        insertintoArtists.setString(1,name);
        ResultSet resultSet = qu
    }
}



