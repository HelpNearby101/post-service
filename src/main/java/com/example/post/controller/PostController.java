package com.example.post.controller;

import com.example.post.entity.Post;
import com.example.post.enumeration.PostCategory;
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
    public Post createPost(@RequestBody Map<String, Object> request) {

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

        return postService.createPost(post);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return "Post deleted successfully";
    }

    @GetMapping("/nearby")
    public List<Post> findNearbyPosts(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius
    ) {
        return postService.findNearbyPosts(
                latitude,
                longitude,
                radius
        );
    }
}