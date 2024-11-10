package iuh.fit.dhktpm117ctt.group06.controller;

import iuh.fit.dhktpm117ctt.group06.dto.request.UserRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.UserResponse;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id,@Valid @RequestBody UserRequest userRequest) {
        UserResponse user = userService.updateInfo(id,userRequest).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}/avatar")
    public ResponseEntity<?> updateAvatar(@PathVariable String id, @RequestParam("avatar") MultipartFile avatar) {
        UserResponse user = userService.updateAvatar(id, avatar).orElse(null);
        return ResponseEntity.ok(user);
    }

}
