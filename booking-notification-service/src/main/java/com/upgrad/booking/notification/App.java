package com.upgrad.booking.notification;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.booking.notification.dto.BookingDTO;
import com.upgrad.booking.notification.dto.MovieDTO;
import com.upgrad.booking.notification.dto.UserDTO;
import com.upgrad.booking.notification.services.MovieService;
import com.upgrad.booking.notification.services.UserService;

public class App implements RequestHandler<SQSEvent, Void> {

    private Void processMessage(String msgBody) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserService userService = new UserService();
            MovieService movieService = new MovieService();

            BookingDTO bookingDTO = objectMapper.readValue(msgBody, BookingDTO.class);

            UserDTO userDTO = userService.GetUserByID(bookingDTO.getUserId());
            MovieDTO movieDTO = movieService.GetMovieByID(bookingDTO.getMovieId());

            String notification = "Thank you " + userDTO.getFirstName() + "! Your booking for movie "
                    + movieDTO.getMovieName() + " has been confirmed.";

            System.out.println(notification);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public Void handleRequest(SQSEvent event, Context context) {

        for(SQSEvent.SQSMessage msg: event.getRecords()) {
            processMessage(msg.getBody());
        }

        return null;
    }
}
