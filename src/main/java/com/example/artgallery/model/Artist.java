/*
 * You can use the following import statements
 *
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 * 
 * import javax.persistence.*;
 * import java.util.List;
 * 
 */

// Write your code here

package com.example.artgallery.model;

import com.example.artgallery.model.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int artistId;

    @Column(name = "name")
    private String artistName;

    @Column(name = "genre")
    private String genre;

    @ManyToMany(mappedBy = "artists")
    @JsonIgnoreProperties("artists")
    private List<Gallery> galleries;

    private List<Art> arts;

    public Artist() {
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Gallery> getGalleries() {
        return galleries;
    }

    public void setGalleries(List<Gallery> galleries) {
        this.galleries = galleries;
    }

    public List<Art> getArts() {
        return arts;
    }

    public void setArts(List<Art> arts) {
        this.arts = arts;
    }
}