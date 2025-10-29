package co.edu.arep.controller;

import co.edu.arep.model.Thread;
import co.edu.arep.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hilos")
@CrossOrigin(origins = "*")
public class ThreadController {

    @Autowired
    private ThreadRepository hiloRepository;

    @GetMapping
    public List<Thread> getAllHilos() {
        return hiloRepository.findAll();
    }

    @PostMapping
    public Thread createHilo(@RequestBody Thread hilo) {
        return hiloRepository.save(hilo);
    }
}
