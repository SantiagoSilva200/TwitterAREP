package co.edu.arep.controller;

import co.edu.arep.model.Post;
import co.edu.arep.model.User;
import co.edu.arep.repository.PostRepository;
import co.edu.arep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByFechaCreacionDesc();
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        System.out.println("Recibiendo solicitud - Contenido: " + request.getContenido() + ", Usuario ID: " + request.getUsuarioId());

        Optional<User> user = userRepository.findById(request.getUsuarioId());
        if (user.isPresent()) {
            Post post = new Post(request.getContenido(), user.get());
            Post savedPost = postRepository.save(post);
            System.out.println("Post creado exitosamente con ID: " + savedPost.getId());
            return ResponseEntity.ok(savedPost);
        }
        System.out.println("Usuario no encontrado con ID: " + request.getUsuarioId());
        return ResponseEntity.badRequest().build();
    }

    public static class PostRequest {
        private String contenido;
        private Long usuarioId;

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public Long getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Long usuarioId) {
            this.usuarioId = usuarioId;
        }
    }
}