package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.cloudinary.CloudinaryProvider;
import iuh.fit.dhktpm117ctt.group06.dto.request.UserRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.UserResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Account;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.exception.AppException;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.jwt.JwtProvider;
import iuh.fit.dhktpm117ctt.group06.repository.UserRepository;
import iuh.fit.dhktpm117ctt.group06.service.AccountService;
import iuh.fit.dhktpm117ctt.group06.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private AccountService accountService;
    private CloudinaryProvider cloudinaryProvider;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider, AccountService accountService, CloudinaryProvider cloudinaryProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.accountService = accountService;
        this.cloudinaryProvider = cloudinaryProvider;
    }

    private UserResponse mapToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    private User mapToUser(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }


    @Override
    public List<UserResponse> findAll() {
        var users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Optional<UserResponse> findById(String id) {
        User user = userRepository.findById(id).orElse(null);
        return Optional.of(mapToUserResponse(user));
    }


    @Override
    public Optional<UserResponse> findUserByToken(String token) {
        String email = jwtProvider.getEmailFromToken(token);
        return getUserResponseByEmail(email);
    }

    @Override
    public Optional<UserResponse> findByEmail(String email) {
        return getUserResponseByEmail(email);
    }

    private Optional<UserResponse> getUserResponseByEmail(String email) {
        Optional<Account> account = accountService.findByEmail(email);
        if (account.isEmpty()) {
            return Optional.empty();
        }
        User user = userRepository.findById(account.get().getUser().getId()).orElse(null);
        return Optional.of(mapToUserResponse(user));
    }

    @Override
    public Optional<UserResponse> findByPhone(String phone) {
        User user = userRepository.findByPhone(phone).orElse(null);
        return Optional.of(mapToUserResponse(user));
    }

    @Override
    public Optional<UserResponse> save(UserRequest userRequest) {
        User user = mapToUser(userRequest);
        return Optional.of(mapToUserResponse(userRepository.save(user)));
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Optional<UserResponse> updateInfo(String id, UserRequest userRequest) {
        User userUpdate = userRepository.findById(id).orElse(null);
        if (userUpdate == null) {
            return Optional.empty();
        }
        String avatar = userUpdate.getAvatar();
        if (userRequest.getAvatar() == null) {
            avatar = userUpdate.getAvatar();
        } else {
           //upload avatar
            try {
                Map uploadResult = cloudinaryProvider.upload(userRequest.getAvatar(),"User", userUpdate.getId());
                userUpdate.setAvatar(uploadResult.get("url").toString());
            } catch (Exception e) {
                throw new AppException(ErrorCode.AVATAR_INVALID);
            }
        }
        User user = mapToUser(userRequest);
        user.setId(id);
        user.setRole(userRepository.findById(id).get().getRole());
        user.setAvatar(avatar);
        return Optional.of(mapToUserResponse(userRepository.save(user)));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Optional<UserResponse> updateAvatar(String id, MultipartFile avatar) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (avatar == null) {
                return Optional.of(mapToUserResponse(user.get()));
            } else {
                try {
                Map uploadResult = cloudinaryProvider.upload(avatar,"User", user.get().getId());
                user.get().setAvatar(uploadResult.get("url").toString());
                return Optional.of(mapToUserResponse(userRepository.save(user.get())));
                } catch (Exception e) {
                    throw new AppException(ErrorCode.AVATAR_INVALID);
                }
            }
        }
        return Optional.empty();
    }
    
   
	@Override
	@Transactional
	public Optional<User> getUserById(String id) {
		return userRepository.findById(id);
	}


}
