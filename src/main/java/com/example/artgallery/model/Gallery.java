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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.List;
import com.example.artgallery.model.*;

@Entity
@Table(name = "gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int galleryId;

    @Column(name = "name")
    private String galleryName;

    @Column(name = "location")
    private String location;

    @ManyToMany
    @JoinTable(name = "artist_gallery", joinColumns = @JoinColumn(name = "galleryid"), inverseJoinColumns = @JoinColumn(name = "artistid"))
    @JsonIgnoreProperties("galleries")
    private List<Artist> artists;

    public Gallery() {

    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
