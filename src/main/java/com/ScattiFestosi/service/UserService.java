package com.ScattiFestosi.service;


import com.ScattiFestosi.model.User;
import com.ScattiFestosi.payload.request.UserUpdateRequest;
import com.ScattiFestosi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, UserUpdateRequest updateRequest) {
        // Trova utente
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));


        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }

        // Update immagine di profilo
        if (updateRequest.getProfileImage() != null) {
            user.setProfileImage(updateRequest.getProfileImage());
            return userRepository.save(user);
        }

        // Cambio Password
        if (updateRequest.getNewPassword() != null) {
            // Verifica password corrente
            if (!passwordEncoder.matches(updateRequest.getCurrentPassword(), user.getPassword())) {
                throw new RuntimeException("Password attuale non corretta");
            }

            // Salva password
            user.setPassword(passwordEncoder.encode(updateRequest.getNewPassword()));
        }

        // Salva e restituisce l'utente aggiornato
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}