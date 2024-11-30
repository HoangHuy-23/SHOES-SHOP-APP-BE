package iuh.fit.dhktpm117ctt.group06.config;


import iuh.fit.dhktpm117ctt.group06.jwt.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] PUBLIC_URL = { "/api/user/register", "/api/user/login", "/api/user/logout", "/api/auth/refreshToken",
    "/api/user/verify", "/api/user/forgotPassword", "/api/user/resetPassword", "/api/user/changePassword",
    "/api/user/changeEmail", "/api/user/changeUsername", "/api/user/changeAvatar", "/api/user/changeRole",
    "/api/user/changeStatus", "/api/user/changePhone", "/api/user/changeAddress", "/api/user/changeName",
            "/api/product-items/**", "/api/cart/**", "/api/order/**", "/api/product/**", "/api/category/**", "/api/address/**",
            "/api/order-detail/**", "/api/post/**", "/api/comment/**", "/api/like/**", "/api/rating/**", "/api/feedback/**",
            "/api/brands/**", "/api/size/**", "/api/color/**", "/api/role/**", "/api/permission/**", "/api/role-permission/**",
            "/api/categories/**", "/api/products/**", "/api/users/**", "/api/posts/**", "/api/collections/**", "/api/orders/**",


    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN"))
//                        .requestMatchers("/api/user/**").authenticated().permitAll()

                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)

                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cfg = new CorsConfiguration();
                        cfg.setAllowedOrigins(Arrays.asList(
                                "http://localhost:2003",
                                "http://localhost:5173",
                                "http://localhost:8080/**"

                        ));
                        cfg.setAllowedMethods(Collections.singletonList("*"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
                        cfg.setExposedHeaders(Collections.singletonList("Authorization"));
                        cfg.setMaxAge(3600L);
                        return cfg;
                    }
                }))
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
