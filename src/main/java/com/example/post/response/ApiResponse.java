package com.example.post.response;

import com.example.post.entity.Post;
import com.example.post.enumeration.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private ResponseStatus status;
    private Object data;
    private int statusCode;

}