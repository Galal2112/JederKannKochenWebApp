package com.example.application.data.service;

import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.Video;
import com.example.application.data.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class VideoService extends CrudService<Video, Integer> {

    private VideoRepository repository;

    public VideoService(@Autowired VideoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Video, Integer> getRepository() {
        return repository;
    }

    public List<Video> getRezeptVideos(int rezeptId) {
        return repository.getRezeptVideos(rezeptId);
    }

    public Video addVideoToRezept(Rezept rezept, String url) {
        Video video = new Video(url);
        video.setRezept(rezept);
        repository.save(video);
        return video;
    }
}
