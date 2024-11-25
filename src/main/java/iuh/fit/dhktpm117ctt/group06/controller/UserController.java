package iuh.fit.dhktpm117ctt.group06.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iuh.fit.dhktpm117ctt.group06.dto.request.UserRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.UserResponse;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.service.MailSenderService;
import iuh.fit.dhktpm117ctt.group06.service.UserService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSenderService;

    @GetMapping("/send-mail")
    public ResponseEntity<?> sendMail() {
        mailSenderService.sendMail("ng.hoang.huy23@gmail.com", "Hello", "Test");
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse user = userService.updateInfo(id,userRequest).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/avatar")
    @Operation(
            summary = "Update user avatar",
            description = "Upload a new avatar for the user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(
                                    type = "object",
                                    description = "Request body for avatar upload",
                                    requiredProperties = {"avatar"},
                                    implementation = MultipartFile.class
                            )
                    )
            ),
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    public ResponseEntity<?> updateAvatar(@PathVariable String id, @RequestParam("avatar") MultipartFile avatar) {
        if (avatar.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }
        UserResponse user = userService.updateAvatar(id, avatar).orElse(null);
        return ResponseEntity.ok(user);
    }

}
