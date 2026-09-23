package com.example.post.repository;

import com.example.post.entity.Post;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByLocationNear(
            GeoJsonPoint location,
            Distance distance
    );
}