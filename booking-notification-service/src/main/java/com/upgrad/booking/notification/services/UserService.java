package com.upgrad.booking.notification.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.booking.notification.dto.UserDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UserService {

    private OkHttpClient client = new OkHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    public UserDTO GetUserByID(String userId) throws IOException {
        Request request = new Request.Builder()
                .url("https://cxz7p2q3e5.execute-api.us-east-1.amazonaws.com/prd/users/" + userId)
                .build();

        Response response = client.newCall(request).execute();

        if(response.code() != 200) {
            return null;
        }

        String userJSON = response.body().string();

        return mapper.readValue(userJSON, UserDTO.class);
    }
}