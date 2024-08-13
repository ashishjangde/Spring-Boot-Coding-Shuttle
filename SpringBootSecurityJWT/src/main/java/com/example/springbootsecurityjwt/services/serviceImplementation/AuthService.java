package com.example.springbootsecurityjwt.services.serviceImplementation;
import com.example.springbootsecurityjwt.dto.LoginDto;
import com.example.springbootsecurityjwt.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.BadCredentialsException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public String login(LoginDto loginDto) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            UserEntity user = (UserEntity) authentication.getPrincipal();
            return jwtService.generateToken(user);

        } catch (BadCredentialsException e) {
            // Handle wrong password
            throw new BadCredentialsException("Wrong password");
        }
    }
}
