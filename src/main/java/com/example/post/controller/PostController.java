package com.example.post.controller;

import com.example.post.entity.Post;
import com.example.post.enumeration.PostCategory;
import com.example.post.enumeration.ResponseStatus;
import com.example.post.response.ApiResponse;
import com.example.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse createPost(
            @RequestBody Map<String, Object> request) {

        Post post = new Post();

        post.setUserId((String) request.get("userId"));
        post.setTitle((String) request.get("title"));
        post.setDescription((String) request.get("description"));

        post.setCategory(
                PostCategory.valueOf(
                        (String) request.get("category")
                )
        );

        Map<String, Object> location =
                (Map<String, Object>) request.get("location");

        List<Number> coordinates =
                (List<Number>) location.get("coordinates");

        double longitude =
                coordinates.get(0).doubleValue();

        double latitude =
                coordinates.get(1).doubleValue();

        post.setLocation(
                new org.springframework.data.mongodb.core.geo.GeoJsonPoint(
                        longitude,
                        latitude
                )
        );

        Post createdPost = postService.createPost(post);

        return new ApiResponse(
                "Post created successfully",
                ResponseStatus.SUCCESS,
                createdPost,
                201
        );
    }

    @GetMapping("/{id}")
    public ApiResponse getPostById(
            @PathVariable String id) {

        Post post = postService.getPostById(id);

        return new ApiResponse(
                "Post fetched successfully",
                ResponseStatus.SUCCESS,
                post,
                200
        );
    }

    @GetMapping
    public ApiResponse getAllPosts() {

        List<Post> posts = postService.getAllPosts();

        return new ApiResponse(
                "Posts fetched successfully",
                ResponseStatus.SUCCESS,
                posts,
                200
        );
    }

    @PutMapping("/{id}")
    public ApiResponse updatePost(
            @PathVariable String id,
            @RequestBody Post post) {

        Post updatedPost =
                postService.updatePost(id, post);

        return new ApiResponse(
                "Post updated successfully",
                ResponseStatus.SUCCESS,
                updatedPost,
                200
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse deletePost(
            @PathVariable String id) {

        postService.deletePost(id);

        return new ApiResponse(
                "Post deleted successfully",
                ResponseStatus.SUCCESS,
                null,
                200
        );
    }

    @GetMapping("/nearby")
    public ApiResponse findNearbyPosts(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {

        List<Post> posts =
                postService.findNearbyPosts(
                        latitude,
                        longitude,
                        radius
                );

        return new ApiResponse(
                "Nearby posts fetched successfully",
                ResponseStatus.SUCCESS,
                posts,
                200
        );
    }
}