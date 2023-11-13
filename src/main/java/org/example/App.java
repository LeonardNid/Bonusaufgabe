package org.example;

import java.sql.*;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    private static Connection conn;

    public static void main( String[] args ) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:sqlite-test.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dropTables();
        createTables();
        inserts();

        testMovie();
        testGenre();
        testHasGenre();


//        Movie m1 = new Movie("Spider-Man", 2020, "A"); // testet insert
//        m1.insertIntomovie(conn);
//        Movie m2 = findById(1);
//        m2.setType("Z");
//        m2.updatemovie(conn);

//        Movie[] arr = findBytitle("Spider");
//
//        for (Movie movie : arr) {
//            movie.setTitle("Iron-Man");
//            movie.updatemovie(conn);
//        }

    }

    public static void testMovie() {
        Movie m1 = new Movie("Spider-Man", 2020, "A"); // testet insert
        m1.insertIntomovie(conn);
//        m1.insertIntomovie(conn); // exception

        Movie m2 = new Movie("Spider-Man2", 2020, "A"); // testet update
        m2.insertIntomovie(conn);
        m2.setTitle("Spider2");
        m2.setYear(2010);
        m2.setType("B");
        m2.updatemovie(conn);

        Movie m3 = new Movie("Spider-Man3", 2020, "A"); // testet delete
        m3.insertIntomovie(conn);
        m3.deletemovie(conn);

        Movie m4 = new Movie("Spider-Man4", 2020, "A"); // testet delete/update ohne datensatz
//        m4.deletemovie(conn);
//        m4.updatemovie(conn);
        m4.insertIntomovie(conn);
    }

    public static void testGenre() {
        Genre m1 = new Genre("Horror"); // testet insert
        m1.insertIntoGenre(conn);
//        m1.insertIntoGenre(conn); // exception

        Genre m2 = new Genre("Action"); // testet update
        m2.insertIntoGenre(conn);
        m2.setGenre("AKTION");
        m2.updateGenre(conn);

        Genre m3 = new Genre("Drama"); // testet delete
        m3.insertIntoGenre(conn);
        m3.deleteGenre(conn);

        Genre m4 = new Genre("Comedy"); // testet delete/update ohne datensatz
//        m4.deleteGenre(conn);
//        m4.updateGenre(conn);
        m4.insertIntoGenre(conn);
    }

    public static void testHasGenre() {
        HasGenre m1 = new HasGenre(1,1); // testet insert
        m1.insertIntoHasGenre(conn);
//        m1.insertIntoHasGenre(conn); // exception

        HasGenre m2 = new HasGenre(1,2); // testet update
        m2.insertIntoHasGenre(conn);
        m2.setGenreid(2);
        m2.setMovieid(3);
        m2.updateHasGenre(conn);

        HasGenre m3 = new HasGenre(2,2); // testet delete
        m3.insertIntoHasGenre(conn);
        m3.deleteHasGenre(conn);

        HasGenre m4 = new HasGenre(3,1); // testet delete/update ohne datensatz
//        m4.deleteHasGenre(conn);
//        m4.updateHasGenre(conn);
        m4.insertIntoHasGenre(conn);
    }

    public static void inserts() {
//        insertIntogenre(1, "Horror");
//        insertIntogenre(2, "Action");

//        insertIntomovie(1, "Spider-Man", 2020, "A");
//        insertIntomovie(2, "Iron-Man", 2010, "B");

//        insertIntohasgenre(2,1);
//        insertIntohasgenre(2,2);

        insertIntoperson(1, "Peter", "M");
        insertIntoperson(2, "RBJ", "M");

        insertIntomoviecharacter(1, "Peter Parker", "Spider-man", 1, 1,1);
        insertIntomoviecharacter(2, "Tony Stark", "Iron-Man", 1, 1,1);
    }
    public static Movie findById(long id) {
        String sql = "SELECT * FROM movie " +
                "WHERE movieid = ? ;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            String title = rs.getString("title");
            int year = rs.getInt("year");
            String type = rs.getString("type");

            return new Movie(id,title,year,type);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Movie[] findBytitle(String title) { // in eigene factoryclass und exception werfen nicht catchen
        String sql = "SELECT * FROM movie " +
                "WHERE title LIKE ? ;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1,"%" + title + "%");

            ResultSet rs = statement.executeQuery();

            ArrayList<Movie> list = new ArrayList<>();

            while (rs.next()) {
                long id = rs.getLong("movieid");
                int year = rs.getInt("year");
                String type = rs.getString("type");

                list.add(new Movie(id, title, year, type));
            }

            return list.toArray(new Movie[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertIntogenre(long genreid, String genre) {
        String sql = "INSERT INTO genre VALUES (?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            statement.setString(2, genre);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertIntomovie(long movieid, String title, int year, String type ) {
        String sql = "INSERT INTO movie VALUES (?, ?, ?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, movieid);
            statement.setString(2, title);
            statement.setInt(3, year);
            statement.setString(4, type);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertIntohasgenre(long genreid, long movieid) {
        String sql = "INSERT INTO hasgenre VALUES (?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            statement.setLong(1, movieid);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertIntoperson(long personid, String name, String sex) {
        String sql = "INSERT INTO person VALUES (?, ?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, personid);
            statement.setString(2, name);
            statement.setString(3, sex);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertIntomoviecharacter(long movcharid, String character, String alias, int position, int movieid, int personid) {
        String sql = "INSERT INTO moviecharacter VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, movcharid);
            statement.setString(2, character);
            statement.setString(3, alias);
            statement.setInt(4, position);
            statement.setInt(5, movieid);
            statement.setInt(6, personid);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dropTables() {
        dropTable("genre");
        dropTable("movie");
        dropTable("hasgenre");
        dropTable("person");
        dropTable("moviecharacter");
    }

    public static void dropTable(String table) {
        String sql = "DROP TABLE " + table + " ;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTables() {
        createTable("create table genre (" +
                "genreid integer primary key," +
                " genre varchar(100))");

        createTable("create table movie ( movieid integer primary key," +
                " title varchar(100)," +
                " year integer," +
                " type varchar(1));");

        createTable( "create table hasgenre ( genreid integer," +
                " movieid integer," +
                " primary key(genreid, movieid), " +
                "foreign key (genreid) references genre(genreid), " +
                "foreign key (movieid) references movie(movieid));");

        createTable(   "create table person ( personid integer primary key, " +
                "name varchar(100), " +
                "sex varchar(1));");

        createTable( "create table moviecharacter (movcharid integer primary key, " +
                "character varchar(100), " +
                "alias varchar(100), " +
                "position integer, " +
                "movieid integer, " +
                "personid integer, " +
                "foreign key(movieid) references movie(movieid), " +
                "foreign key(personid) references person(personid));");
    }
    public static void createTable(String sql) {
        try(PreparedStatement statement = conn.prepareStatement(sql)) {
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
