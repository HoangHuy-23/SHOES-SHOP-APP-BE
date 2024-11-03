package iuh.fit.dhktpm117ctt.group06.controller;


import iuh.fit.dhktpm117ctt.group06.entities.RefreshToken;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.entities.UserRole;
import iuh.fit.dhktpm117ctt.group06.exception.UserException;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtProvider;
import iuh.fit.dhktpm117ctt.group06.repository.UserRepository;
import iuh.fit.dhktpm117ctt.group06.request.LoginRequest;
import iuh.fit.dhktpm117ctt.group06.request.RefreshTokenRequest;
import iuh.fit.dhktpm117ctt.group06.response.AuthResponse;
import iuh.fit.dhktpm117ctt.group06.service.impl.AuthServiceImpl;
import iuh.fit.dhktpm117ctt.group06.service.impl.RefreshTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private AuthServiceImpl authService;
    private RefreshTokenServiceImpl refreshTokenService;


    @Autowired
    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, AuthServiceImpl authService, RefreshTokenServiceImpl refreshTokenService) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signUp")
    public String createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String username = user.getUsername();
        if (userRepository.existsByEmail(email)) {
            throw new UserException("Email is already used with another account");
        }
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setUsername(username);
        createUser.setRole(UserRole.CUSTOMER);
        User savedUser = userRepository.save(createUser);

        return "sign up successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByEmail(loginRequest.getEmail()).get();
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwtProvider.generateToken(loginRequest.getEmail(),user.getRole().toString()), refreshToken.getToken()));
        } else {
            throw new RuntimeException("invalid user request !");
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtProvider.generateToken(user.getEmail(),user.getRole().toString());
                    return ResponseEntity.ok(new AuthResponse(accessToken, refreshTokenRequest.getRefreshToken()));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteByToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok("Logout successfully");
    }

    @GetMapping("/test")
    public ResponseEntity<User> test(@RequestHeader("Authorization") String token) {
        String email = jwtProvider.getEmailFromToken(token);
        return ResponseEntity.ok(userRepository.findByEmail(email).get());
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = authService.loadUserByUsername(username);
        if (userDetails==null) {
            throw new BadCredentialsException("Invalid Username...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }



}
