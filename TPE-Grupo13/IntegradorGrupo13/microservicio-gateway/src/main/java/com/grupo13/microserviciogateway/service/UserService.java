package com.grupo13.microserviciogateway.service;

import com.grupo13.microserviciogateway.feignClients.UsuarioFeign;
import com.grupo13.microserviciogateway.feignModel.UserResponse;
import com.grupo13.microserviciogateway.service.dto.user.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioFeign usuarioFeign;

    public long saveUser(UserDTO request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserResponse userGuardado = usuarioFeign.createUser(request);
        return userGuardado.getId();
    }
}
