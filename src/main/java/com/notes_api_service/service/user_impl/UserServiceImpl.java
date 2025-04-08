package com.notes_api_service.service.user_impl;

import com.notes_api_service.dto.LoginRequest;
import com.notes_api_service.dto.LoginResponse;
import com.notes_api_service.dto.UserDto;
import com.notes_api_service.entity.AccountStatus;
import com.notes_api_service.entity.EmailRequest;
import com.notes_api_service.entity.Role;
import com.notes_api_service.entity.User;
import com.notes_api_service.repository.RoleRepository;
import com.notes_api_service.repository.UserRepository;
import com.notes_api_service.security.CustomUserDetails;
import com.notes_api_service.service.JwtService;
import com.notes_api_service.service.UserService;
import com.notes_api_service.service.EmailService;
import com.notes_api_service.service.jwt_impl.JwtServiceImpl;
import com.notes_api_service.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Validation validation ;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtService jwtService;



    @Override
    public Boolean registerUser(UserDto userDto,String url) throws Exception {
        //user validation
        validation.userValidation(userDto);
        User user = modelMapper.map(userDto, User.class);

        setRole(userDto,user);

        AccountStatus accountStatus = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(accountStatus);
       // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        if (!ObjectUtils.isEmpty(savedUser)){
            emailSend(savedUser,url);
            return true;
        }

        return false;
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),loginRequest.getPassword()));
        if (authentication.isAuthenticated()){
           CustomUserDetails credentials = (CustomUserDetails) authentication.getPrincipal();
           String token = jwtService.generateJwtToken(credentials.getUser());
           LoginResponse loginResponse = LoginResponse.builder()
                   .user(modelMapper.map(credentials.getUser(), UserDto.class))
                   .token(token)
                   .build();
           return loginResponse;
        }

        return null;
    }



    private void emailSend(User savedUser ,String url) throws Exception {
        String verificationCode = (savedUser.getStatus() != null) ?
                savedUser.getStatus().getVerificationCode() : "null";
        String verificationLink = String.format("%s/api/v1/home/verify?uid=%d&code=%s",url,
                savedUser.getId(), verificationCode);

        String message = String.format(
                "<html>"
                        + "<body>"
                        + "<p>Hi, <b>%s %s</b>,</p>"
                        + "<p>Your account has been successfully registered.</p>"
                        + "<p>Click the link below to verify your account:</p>"
                        + "<p><a href='%s' style='background-color:blue;color:white;padding:10px 15px;text-decoration:none;'>Verify Account</a></p>"
                        + "<br>"
                        + "<p>Thanks,</p>"
                        + "<p><b>Notesapi.com</b></p>"
                        + "</body>"
                        + "</html>",
                savedUser.getFirstName(),
                savedUser.getLastName(),
                verificationLink
        );
        EmailRequest emailRequest = EmailRequest.builder()
                .to(savedUser.getEmail())
                .subject("Account created successfully")
                .title("Account Creating Confirmation")
                .message(message)
                .build();
        emailService.sendEmail(emailRequest);

    }

    private void setRole(UserDto userDto,User user) {
       List<Integer> reqRoleId=  userDto.getRoles().stream().map(r->r.getId()).toList();
       List<Role> roleList = roleRepository.findAllById(reqRoleId);
       user.setRoles(roleList);

    }
}
