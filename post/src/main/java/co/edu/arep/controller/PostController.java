package co.edu.arep.controller;

import co.edu.arep.model.Post;
import co.edu.arep.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        System.out.println("Recibiendo solicitud - Contenido: " + request.getContenido() + ", Usuario ID: " + request.getUsuarioId());
        return ResponseEntity.ok(postService.createPost());

    }

    public static class PostRequest {
        private String contenido;
        private Long usuarioId;
        private Long hilo;


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

        public Long getHilo() {
            return hilo;
        }

        public void setHilo(Long hilo) {
            this.hilo = hilo;
        }
    }
}