/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here

package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.service.ArtistJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArtistController {
    @Autowired
    private ArtistJpaService artistJpaService;

    @GetMapping("/galleries/artists")
    public ArrayList<Artist> getArtists() {
        return artistJpaService.getArtists();
    }

    @GetMapping("/galleries/artist/{artistId}")
    public Artist getArtistById(@PathVariable("artistId") int artistId) {
        return artistJpaService.getArtistById(artistId);
    }

    @PostMapping("/galleries/artist")
    public Artist addArtist(@RequestBody Artist artist) {
        return artistJpaService.addArtist(artist);
    }

    @PutMapping("/galleries/artist/{artistId}")
    public Artist updateArtist(@PathVariable("artistId") int artistId, @RequestBody Artist artist) {
        return artistJpaService.updateArtist(artistId, artist);
    }

    @DeleteMapping("/galleries/artist/{artistId}")
    public void deleteArtist(@PathVariable("artistId") int artistId) {
        artistJpaService.deleteArtist(artistId);
    }

    @GetMapping("/artist/{artistId}/art")
    public List<Art> getArtistArts(@PathVariable("artistId") int artistId) {
        return artistJpaService.getArtistArts(artistId);
    }

    @GetMapping("/artist/{artistId}/gallery")
    public List<Gallery> getArtistGalleries(@PathVariable("artistId") int artistId) {
        return artistJpaService.getArtistGalleries(artistId);
    }
}