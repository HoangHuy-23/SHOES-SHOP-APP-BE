package iuh.fit.dhktpm117ctt.group06.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1002, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003, "Email invalid", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1111, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1099, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_AUTHORIZED(1401, "User not authorized", HttpStatus.UNAUTHORIZED),
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
