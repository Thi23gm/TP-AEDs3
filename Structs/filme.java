package Structs;
import java.io.IOException;

public class filme { 
    static int Cont = 0;
    public filme(String type, String name, String director, String[] cast, String country, String dateAdded,
            int releaseYear, String rating, int duration, String[] genres, String description) throws IOException {
        this.lapide = false;
        this.id = Cont++;
        this.type = type;
        this.name = name;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.genres = genres;
        this.description = description;
    }

    public filme( boolean lapide ,int id) throws IOException{
        this.lapide = lapide;
        this.id = id;
        this.type = "";
        this.name = "";
        this.director = "";
        this.cast = null;
        this.country = null;
        this.dateAdded = "";
        this.releaseYear = -1;
        this.rating = "";
        this.duration = -1;
        this.genres = null;
        this.description = "";
    }

    public filme() throws IOException{
        this.lapide = false;
        this.id = Cont++;
        this.type = "";
        this.name = "";
        this.director = "";
        this.cast = null;
        this.country = null;
        this.dateAdded = "";
        this.releaseYear = -1;
        this.rating = "";
        this.duration = -1;
        this.genres = null;
        this.description = "";
    }
    
    //private int bytesTotal;
    private boolean lapide;
    private int id;
    private String type;
    private String name;
    private String director;
    private String[] cast;
    private String country;
    private String dateAdded;
    private int releaseYear;
    private String rating;
    private int duration;
    private String[] genres;
    private String description;

    public static int getCONT(){
        return Cont;
    }

    public boolean getLapide() {
        return lapide;
    }
    public void setLapide(Boolean lapide) {
        this.lapide = lapide;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getCast() {
        return cast;
    }
    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String[] getGenres() {
        return genres;
    }
    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}