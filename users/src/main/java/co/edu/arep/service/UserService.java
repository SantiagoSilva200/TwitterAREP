package co.edu.arep.service;

import co.edu.arep.model.User;
import co.edu.arep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    public List<User> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<User> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public User createUsuario(User usuario) {
        return usuarioRepository.save(usuario);
    }
}