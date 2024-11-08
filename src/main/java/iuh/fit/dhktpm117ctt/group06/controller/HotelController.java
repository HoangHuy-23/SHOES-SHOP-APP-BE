package iuh.fit.dhktpm117ctt.group06.controller;

import iuh.fit.dhktpm117ctt.group06.dto.request.CreateHotelRequest;
import iuh.fit.dhktpm117ctt.group06.entities.Hotel;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.exception.AppException;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.service.HotelServices;
import iuh.fit.dhktpm117ctt.group06.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelServices hotelServices;
    @Autowired
    private UserService userService;

    public ResponseEntity<?> addHotel(@RequestHeader String token, @RequestBody @Valid CreateHotelRequest createHotelRequest) {
        User user = userService.getUserByToken(token);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_AUTHORIZED);
        }
        Hotel hotel = createHotelRequest.toHotel();
        hotel.setAdmin(user);
        return ResponseEntity.ok(hotelServices.saveHotel(hotel));
    }

}
