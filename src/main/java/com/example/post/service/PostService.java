package com.example.post.service;

import com.example.post.entity.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post);

    Post getPostById(String id);

    List<Post> getAllPosts();

    Post updatePost(String id, Post post);

    void deletePost(String id);

    List<Post> findNearbyPosts(
            double latitude,
            double longitude,
            double radius
    );
}