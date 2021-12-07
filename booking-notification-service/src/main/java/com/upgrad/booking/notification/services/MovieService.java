package com.upgrad.booking.notification.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.booking.notification.dto.MovieDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MovieService {

    private OkHttpClient client = new OkHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    public MovieDTO GetMovieByID(String movieId) throws IOException {
        Request request = new Request.Builder()
                .url("https://tfyssk0do0.execute-api.us-east-1.amazonaws.com/prd/movies/" + movieId)
                .build();

        Response response = client.newCall(request).execute();

        if(response.code() != 200) {
            return null;
        }

        String movieJSON = response.body().string();

        return mapper.readValue(movieJSON, MovieDTO.class);
    }
}
