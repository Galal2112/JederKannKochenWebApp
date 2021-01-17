package com.example.application.data.repository;

import com.example.application.data.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    @Query("select v from Video v where v.rezept.id=:rezeptId")
    List<Video> getRezeptVideos(Integer rezeptId);
}
