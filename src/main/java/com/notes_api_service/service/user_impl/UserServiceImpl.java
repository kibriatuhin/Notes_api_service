package com.notes_api_service.service.user_impl;

import com.notes_api_service.dto.UserDto;
import com.notes_api_service.entity.Role;
import com.notes_api_service.entity.User;
import com.notes_api_service.repository.RoleRepository;
import com.notes_api_service.repository.UserRepository;
import com.notes_api_service.service.UserService;
import com.notes_api_service.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

    @Override
    public Boolean registerUser(UserDto userDto) {
        //user validation
        validation.userValidation(userDto);
        User user = modelMapper.map(userDto, User.class);
        setRole(userDto,user);
        User savedUser = userRepository.save(user);
        return !ObjectUtils.isEmpty(savedUser);
    }

    private void setRole(UserDto userDto,User user) {
       List<Integer> reqRoleId=  userDto.getRoles().stream().map(r->r.getId()).toList();
       List<Role> roleList = roleRepository.findAllById(reqRoleId);
       user.setRoles(roleList);

    }
}
