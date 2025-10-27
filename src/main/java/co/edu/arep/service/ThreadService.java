package co.edu.arep.service;

import co.edu.arep.model.Thread;
import co.edu.arep.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository hiloRepository;

    public List<Thread> getAllHilos() {
        return hiloRepository.findAll();
    }

    public Thread createHilo(Thread hilo) {
        return hiloRepository.save(hilo);
    }
}