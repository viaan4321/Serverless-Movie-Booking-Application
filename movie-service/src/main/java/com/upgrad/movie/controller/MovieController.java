package com.upgrad.movie.controller;

import com.upgrad.movie.dto.MovieDTO;
import com.upgrad.movie.entities.Movie;
import com.upgrad.movie.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(path = "/movies", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMovie(@RequestBody MovieDTO newMovie) {
        Movie movie = modelMapper.map(newMovie, Movie.class);

        Movie savedMovie = movieRepo.save(movie);

        MovieDTO savedMovieDto = modelMapper.map(savedMovie,MovieDTO.class);

        return new ResponseEntity(savedMovieDto, HttpStatus.CREATED);
    }

    @RequestMapping(path="/movies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllMovie() {
        Iterable<Movie> allMovies = movieRepo.findAll();

        List<MovieDTO> movieDTOList = new ArrayList<>();

        allMovies.forEach(mov -> movieDTOList.add(modelMapper.map(mov, MovieDTO.class)));

        return new ResponseEntity(movieDTOList, HttpStatus.OK);
    }

    @RequestMapping(path="/movies/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMovieById(@PathVariable(name = "id")  String id) {
        Optional<Movie> movie = movieRepo.findById(id);
        if(movie.isPresent()) {
            MovieDTO movieDTO = modelMapper.map(movie.get(), MovieDTO.class);
            return new ResponseEntity(movieDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path="/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMovieByID(@PathVariable(name = "id") String id) {
        movieRepo.deleteById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
