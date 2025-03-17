package com.animo.animorise.service;

import com.animo.animorise.dto.LoginUserDto;
import com.animo.animorise.dto.RegisterUserDto;
import com.animo.animorise.entity.User;

public interface AuthenticationService {
    User signup(RegisterUserDto registerUserDto);
    User authenticate(LoginUserDto loginUserDto);
}
