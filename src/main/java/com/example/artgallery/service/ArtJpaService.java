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

import com.example.artgallery.model.Art;
import com.example.artgallery.model.Artist;
import com.example.artgallery.repository.ArtistJpaRepository;
import com.example.artgallery.repository.ArtJpaRepository;
import com.example.artgallery.repository.ArtRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtJpaService implements ArtRepository {
    @Autowired
    private ArtJpaRepository artJpaRepository;

    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    public ArrayList<Art> getArts() {
        List<Art> artsList = artJpaRepository.findAll();
        ArrayList<Art> arts = new ArrayList<>(artsList);
        return arts;
    }

    public Art getArtById(int artId) {
        try {
            Art art = artJpaRepository.findById(artId).get();
            return art;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Art addArt(Art art) {
        Artist artist = art.getArtist();
        int artistId = artist.getArtistId();

        try {
            artist = artistJpaRepository.findById(artistId).get();
            art.setArtist(artist);
            artJpaRepository.save(art);
            return art;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Art updateArt(int artId, Art art) {
        try {
            Art newArt = artJpaRepository.findById(artId).get();
            if (art.getArtTitle() != null) {
                newArt.setArtTitle(art.getArtTitle());
            }
            if (art.getTheme() != null) {
                newArt.setTheme(art.getTheme());
            }
            if (art.getArtist() != null) {
                int artistId = art.getArtist().getArtistId();
                Artist newArtist = artistJpaRepository.findById(artistId).get();
                newArt.setArtist(newArtist);
            }

            artJpaRepository.save(newArt);
            return newArt;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteArt(int artId) {
        try {
            artJpaRepository.deleteById(artId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public Artist getArtArtist(int artId) {
        try {
            Art art = artJpaRepository.findById(artId).get();
            Artist artist = art.getArtist();
            return artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
