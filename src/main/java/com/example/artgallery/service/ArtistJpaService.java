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

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistJpaService implements ArtistRepository {
    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Autowired
    private GalleryJpaRepository galleryJpaRepository;

    @Override
    public ArrayList<Artist> getArtists() {
        List<Artist> artistList = artistJpaRepository.findAll();
        ArrayList<Artist> artists = new ArrayList<>(artistList);
        return artists;
    }

    @Override
    public Artist getArtistById(int artistId) {
        try {
            Artist Artist = artistJpaRepository.findById(artistId).get();
            return Artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist addArtist(Artist artist) {
        // artistJpaRepository.save(artist);
        // return artist;
        List<Integer> galleryIds = new ArrayList<>();
        for (Gallery gallery : artist.getGalleries()) {
            galleryIds.add(gallery.getGalleryId());
        }
        List<Gallery> galleries = galleryJpaRepository.findAllById(galleryIds);

        artist.setGalleries(galleries);
        for (Gallery gallery : galleries) {
            gallery.getArtists().add(artist);
        }

        Artist savedArtist = artistJpaRepository.save(artist);
        galleryJpaRepository.saveAll(galleries);
        return savedArtist;

        // if (galleries.size() != galleryIds.size()) {
        // throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // }
        // artist.setGalleries(galleries);
        // return artistJpaRepository.save(artist);
    }

    @Override
    public Artist updateArtist(int artistId, Artist artist) {
        try {
            Artist newArtist = artistJpaRepository.findById(artistId).get();
            if (artist.getArtistName() != null) {
                newArtist.setArtistName(artist.getArtistName());
            }
            if (artist.getGenre() != null) {
                newArtist.setGenre(artist.getGenre());
            }
            if (artist.getGalleries() != null) {
                // newArtist.setGalleries(artist.getGalleries());

                List<Gallery> galleries = newArtist.getGalleries();
                for (Gallery gallery : galleries) {
                    gallery.getArtists().remove(newArtist);
                }
                galleryJpaRepository.saveAll(galleries);
                List<Integer> newGalleryIds = new ArrayList<>();
                for (Gallery gallery : artist.getGalleries()) {
                    newGalleryIds.add(gallery.getGalleryId());
                }
                List<Gallery> newGalleries = galleryJpaRepository.findAllById(newGalleryIds);
                for (Gallery gallery : newGalleries) {
                    gallery.getArtists().add(newArtist);
                }
                galleryJpaRepository.saveAll(newGalleries);
                newArtist.setGalleries(newGalleries);
            }
            return artistJpaRepository.save(newArtist);

            // artistJpaRepository.save(newArtist);
            // return newArtist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArtist(int artistId) {

        try {
            Artist artist = artistJpaRepository.findById(artistId).get();

            List<Gallery> galleries = artist.getGalleries();
            for (Gallery gallery : galleries) {
                gallery.getArtists().remove(artist);
            }

            galleryJpaRepository.saveAll(galleries);

            artistJpaRepository.deleteById(artistId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        // try {
        // artistJpaRepository.deleteById(artistId);
        // } catch (Exception e) {
        // throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // }
        // throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Art> getArtistArts(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            // Art art = artist.getArts();
            // return art;
            return artist.getArts();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Gallery> getArtistGalleries(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            // Gallery gallery = artist.getGalleries();
            // return gallery;
            return artist.getGalleries();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
