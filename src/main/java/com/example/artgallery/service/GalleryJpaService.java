/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here

package com.example.artgallery.service;

import com.example.artgallery.model.Gallery;
import com.example.artgallery.model.Artist;
import com.example.artgallery.repository.GalleryJpaRepository;
import com.example.artgallery.repository.GalleryRepository;
import com.example.artgallery.repository.ArtistJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GalleryJpaService implements GalleryRepository {

    @Autowired
    private GalleryJpaRepository galleryJpaRepository;

    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Override
    public ArrayList<Gallery> getGalleries() {
        List<Gallery> galleryList = galleryJpaRepository.findAll();
        ArrayList<Gallery> gallerys = new ArrayList<>(galleryList);
        return gallerys;
    }

    @Override
    public Gallery getGalleryById(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            return gallery;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Gallery addGallery(Gallery gallery) {
        List<Integer> artistIds = new ArrayList<>();
        for (Artist artist : gallery.getArtists()) {
            artistIds.add(artist.getArtistId());
        }

        List<Artist> artists = artistJpaRepository.findAllById(artistIds);

        if (artists.size() != artistIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        gallery.setArtists(artists);
        return galleryJpaRepository.save(gallery);
    }

    @Override
    public Gallery updateGallery(int galleryId, Gallery gallery) {
        try {
            Gallery newGallery = galleryJpaRepository.findById(galleryId).get();
            if (gallery.getGalleryName() != null) {
                newGallery.setGalleryName(gallery.getGalleryName());
            }
            if (gallery.getLocation() != null) {
                newGallery.setLocation(gallery.getLocation());
            }
            if (gallery.getArtists() != null) {
                List<Integer> artistIds = new ArrayList<>();
                for (Artist artist : gallery.getArtists()) {
                    artistIds.add(artist.getArtistId());
                }
                List<Artist> artists = artistJpaRepository.findAllById(artistIds);
                if (artists.size() != artistIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                newGallery.setArtists(artists);
            }
            return galleryJpaRepository.save(newGallery);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteGallery(int galleryId) {
        try {
            galleryJpaRepository.deleteById(galleryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Artist> getGalleryArtists(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            return gallery.getArtists();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
