package iuh.fit.dhktpm117ctt.group06.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    // User
    PASSWORD_INVALID(1002, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003, "Email invalid", HttpStatus.BAD_REQUEST),
    FIRSTNAME_INVALID(1004, "First Name contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    LASTNAME_INVALID(1005, "Last Name contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1111, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1099, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_AUTHORIZED(1401, "User not authorized", HttpStatus.UNAUTHORIZED),
    // Hotel
    HOTEL_NAME_INVALID(2001, "Hotel name contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(2002, "Phone number must be {min} characters long", HttpStatus.BAD_REQUEST),
    OPEN_TIME_INVALID(2003, "Opening time must be HH:mm", HttpStatus.BAD_REQUEST),
    CLOSE_TIME_INVALID(2004, "Closing time must be HH:mm and after open time", HttpStatus.BAD_REQUEST),
    AVATAR_INVALID(2005, "Avatar must be an image", HttpStatus.BAD_REQUEST),
    DETAIL_IMAGES_INVALID(2006, "Detail images must be images", HttpStatus.BAD_REQUEST),
    ADMIN_ID_INVALID(2007, "Admin ID must be a number", HttpStatus.BAD_REQUEST),
    HOTEL_NOT_FOUND(2099, "Hotel not found", HttpStatus.NOT_FOUND),
    HOTEL_EXISTED(2111, "Hotel existed", HttpStatus.BAD_REQUEST),
    // Address
    HOME_NUMBER_INVALID(2101, "Home number contains only letters and numbers, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    STREET_INVALID(2102, "Street contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    WARD_INVALID(2103, "Ward contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    DISTRICT_INVALID(2104, "District contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    CITY_INVALID(2105, "City contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),
    // Room
    ROOM_NAME_INVALID(3001, "Room name contains only letters and spaces, {min}-{max} characters long", HttpStatus.BAD_REQUEST),

    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
