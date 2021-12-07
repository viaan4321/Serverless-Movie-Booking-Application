package com.upgrad.movie.repository;

import com.upgrad.movie.entities.Movie;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface MovieRepository extends CrudRepository<Movie, String> {

}