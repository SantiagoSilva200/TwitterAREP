package co.edu.arep.controller;

import co.edu.arep.model.User;
import co.edu.arep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository usuarioRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User usuario) {
        return usuarioRepository.save(usuario);
    }
}
