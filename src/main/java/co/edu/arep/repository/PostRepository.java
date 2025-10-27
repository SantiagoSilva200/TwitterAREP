package co.edu.arep.repository;

import co.edu.arep.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByFechaCreacionDesc();
}