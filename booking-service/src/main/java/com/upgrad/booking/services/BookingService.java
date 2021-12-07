package com.upgrad.booking.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.booking.dto.BookingDTO;
import com.upgrad.booking.entities.Booking;
import com.upgrad.booking.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private ModelMapper modelMapper;

    private void SendToSQS(BookingDTO bookingDTO) {
        String queueUrl = System.getenv("SQS_URL");

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJSON = "";
        try {
            bookJSON = objectMapper.writeValueAsString(bookingDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(bookJSON);

        sqs.sendMessage(sendMessageRequest);
    }

    public BookingDTO CreateBooking(BookingDTO bookingDTO) {
        Booking newBooking = modelMapper.map(bookingDTO, Booking.class);

        newBooking = bookingRepo.save(newBooking);

        BookingDTO savedBooking = modelMapper.map(newBooking, BookingDTO.class);

        SendToSQS(savedBooking);

        return savedBooking;
    }
}
