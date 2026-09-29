package com.example.post.service.impl;

import com.example.post.entity.Post;
import com.example.post.enumeration.PostStatus;
import com.example.post.exception.PostNotFoundException;
import com.example.post.repository.PostRepository;
import com.example.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        post.setStatus(PostStatus.ACTIVE);
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(String id) {

        return postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException(
                                "Post not found with id: " + id
                        )
                );
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(String id, Post post) {

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException(
                                "Post not found with id: " + id
                        )
                );

        existingPost.setTitle(post.getTitle());
        existingPost.setDescription(post.getDescription());
        existingPost.setCategory(post.getCategory());
        existingPost.setLocation(post.getLocation());
        existingPost.setStatus(post.getStatus());

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> findNearbyPosts(
            double latitude,
            double longitude,
            double radius
    ) {

        GeoJsonPoint location =
                new GeoJsonPoint(longitude, latitude);

        Distance distance =
                new Distance(radius, Metrics.KILOMETERS);

        return postRepository.findByLocationNear(
                location,
                distance
        );
    }
}