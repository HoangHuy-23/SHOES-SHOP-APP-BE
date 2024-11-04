package iuh.fit.dhktpm117ctt.group06.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import iuh.fit.dhktpm117ctt.group06.dto.request.SignUpRequest;
import iuh.fit.dhktpm117ctt.group06.entities.Account;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.entities.enums.UserRole;
import iuh.fit.dhktpm117ctt.group06.exception.UserException;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtConstants;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtProvider;
import iuh.fit.dhktpm117ctt.group06.repository.AccountRepository;
import iuh.fit.dhktpm117ctt.group06.repository.UserRepository;
import iuh.fit.dhktpm117ctt.group06.dto.request.LoginRequest;
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
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private AccountRepository accountRepository;





    @PostMapping("/signUp")
//    @Transactional
    public String createUserHandler(@RequestBody SignUpRequest signUpRequest) throws UserException {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String firstName = signUpRequest.getFirstName();
        String lastName = signUpRequest.getLastName();
//        String username = user.getAccount().getUsername();
        if (accountRepository.existsByEmail(email)) {
            throw new UserException("Email is already used with another account");
        }
        User createUser = new User();
//        createUser.setEmail(email);
//        createUser.setPassword(passwordEncoder.encode(password));
//        createUser.setUsername(username);
        createUser.setFirstName(firstName);
        createUser.setLastName(lastName);
//        createUser.setAccount(account);
        createUser.setRole(UserRole.CUSTOMER);
        User savedUser = userRepository.save(createUser);
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        account.setUser(savedUser);

        Account savedAcc = accountRepository.save(account);

        return "sign up successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            User user = userRepository.findByAccount_Email(loginRequest.getEmail()).get();
            Account account = accountRepository.findByEmail(loginRequest.getEmail());
            User user = account.getUser();
            if(user==null) {
                throw new RuntimeException("User not found");
            }

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
        Account matchedAccount = accountRepository.findByEmail(jwtProvider.getEmailFromToken(refreshToken));
//        User user = userRepository.findByAccount_Email(jwtProvider.getEmailFromToken(refreshToken)).get();
        if(matchedAccount == null) {
            throw new RuntimeException("Invalid account");
        }

        User matchedUser = matchedAccount.getUser();

        String accessToken = jwtProvider.generateToken(matchedAccount.getEmail(),matchedUser.getRole().name());
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
//        return ResponseEntity.ok(userRepository.findByAccount_Email(email).get());
        return ResponseEntity.ok(accountRepository.findByEmail(email).getUser());
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
