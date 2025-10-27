package co.edu.arep.service;

import co.edu.arep.model.Post;
import co.edu.arep.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByFechaCreacionDesc();
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }
}