package iuh.fit.dhktpm117ctt.group06.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.entities.UserRole;
import iuh.fit.dhktpm117ctt.group06.exception.UserException;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtConstants;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtProvider;
import iuh.fit.dhktpm117ctt.group06.repository.UserRepository;
import iuh.fit.dhktpm117ctt.group06.request.LoginRequest;
import iuh.fit.dhktpm117ctt.group06.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthServiceImpl authService;



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
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByEmail(loginRequest.getEmail()).get();
            String accessToken = jwtProvider.generateToken(loginRequest.getEmail(),user.getRole().toString());
            String refreshToken = jwtProvider.generateRefreshToken(loginRequest.getEmail(),user.getRole().toString());
            session.setMaxInactiveInterval(60*60*24*7);
            session.setAttribute("REFRESH_TOKEN", refreshToken);
            return ResponseEntity.ok(accessToken);
        } else {
            throw new RuntimeException("invalid user request !");
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<String> refreshToken(HttpSession session) {
        if (((String) session.getAttribute("REFRESH_TOKEN"))==null) {
           return ResponseEntity.ok("Refresh token is not in database!");
        }
        String refreshToken = (String) session.getAttribute("REFRESH_TOKEN");
        User user = userRepository.findByEmail(jwtProvider.getEmailFromToken(refreshToken)).get();
        String accessToken = jwtProvider.generateToken(user.getEmail(),user.getRole().name());
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.removeAttribute("REFRESH_TOKEN");
        return ResponseEntity.ok("Logout successfully");
    }

    @GetMapping("/test")
    public ResponseEntity<User> test(@RequestHeader String Authorization) {
        String jwt = Authorization.substring(7);
        SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
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
