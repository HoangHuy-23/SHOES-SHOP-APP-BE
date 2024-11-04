package iuh.fit.dhktpm117ctt.group06.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import iuh.fit.dhktpm117ctt.group06.dto.request.SignUpRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.ApiResponse;
import iuh.fit.dhktpm117ctt.group06.dto.response.AuthResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Account;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.entities.enums.UserRole;
import iuh.fit.dhktpm117ctt.group06.exception.AppException;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.exception.UserException;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtConstants;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtProvider;
import iuh.fit.dhktpm117ctt.group06.repository.AccountRepository;
import iuh.fit.dhktpm117ctt.group06.repository.UserRepository;
import iuh.fit.dhktpm117ctt.group06.dto.request.LoginRequest;
import iuh.fit.dhktpm117ctt.group06.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Optional;

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

    @Autowired
    private AccountRepository accountRepository;





    @PostMapping("/signUp")
    public ApiResponse<User> createUserHandler(@RequestBody @Valid SignUpRequest signUpRequest) throws UserException {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String firstName = signUpRequest.getFirstName();
        String lastName = signUpRequest.getLastName();
        if (accountRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User createUser = new User();
        createUser.setFirstName(firstName);
        createUser.setLastName(lastName);

        Account account = new Account();
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        account.setUser(createUser);

        createUser.setRole(UserRole.CUSTOMER);
        User savedUser = userRepository.save(createUser);
        accountRepository.save(account);

        ApiResponse<User> response = new ApiResponse<>();
        response.setMessage("Sign up successfully");
        response.setResult(savedUser);
        return response;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> authenticateAndGetToken(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //User user = userRepository.findByAccount_Email(loginRequest.getEmail()).get();
            Optional<Account> account = accountRepository.findByEmail(loginRequest.getEmail());
            User user = userRepository.getReferenceById(account.get().getUser().getId());
            String accessToken = jwtProvider.generateToken(loginRequest.getEmail(),user.getRole().toString());
            String refreshToken = jwtProvider.generateRefreshToken(loginRequest.getEmail(),user.getRole().toString());
            session.setMaxInactiveInterval(60*60*24*7);
            session.setAttribute("REFRESH_TOKEN", refreshToken);

            ApiResponse<AuthResponse> response = new ApiResponse<>();
            response.setResult(new AuthResponse(accessToken));
            return response;
        } else {
            throw new RuntimeException("invalid user request !");
        }
    }

    @PostMapping("/refreshToken")
    public ApiResponse<AuthResponse> refreshToken(HttpSession session) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        if (((String) session.getAttribute("REFRESH_TOKEN"))==null) {
            response.setCode(ErrorCode.USER_NOT_AUTHORIZED.getCode());
            response.setMessage("Refresh token is not in database!");
           return response;
        }
        String refreshToken = (String) session.getAttribute("REFRESH_TOKEN");
        //User user = userRepository.findByAccount_Email(jwtProvider.getEmailFromToken(refreshToken)).get();
        Optional<Account> account = accountRepository.findByEmail(jwtProvider.getEmailFromToken(refreshToken));
        User user = userRepository.getReferenceById(account.get().getUser().getId());
        String accessToken = jwtProvider.generateToken(account.get().getEmail(),user.getRole().name());

        response.setResult(new AuthResponse(accessToken));
        return response;
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
        return ResponseEntity.ok(userRepository.findById(accountRepository.findByEmail(email).get().getUser().getId()).get());
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
