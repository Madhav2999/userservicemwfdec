package com.icwd.serviceregistry.userservicemwfdec.controller;

import com.icwd.serviceregistry.userservicemwfdec.Dtos.LoginRequestDto;
import com.icwd.serviceregistry.userservicemwfdec.Dtos.LogoutRequestDto;
import com.icwd.serviceregistry.userservicemwfdec.Dtos.SignUpRequestDto;
import com.icwd.serviceregistry.userservicemwfdec.Dtos.UserDto;
import com.icwd.serviceregistry.userservicemwfdec.models.Token;
import com.icwd.serviceregistry.userservicemwfdec.models.User;
import com.icwd.serviceregistry.userservicemwfdec.service.UserService;
import io.micrometer.common.lang.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    UserService userService;
    @GetMapping("/login")
     public Token login(@RequestBody LoginRequestDto loginRequestDto)
     {
         return userService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
     }
     @PostMapping("/signup")
     public User signup(@RequestBody SignUpRequestDto signUpRequestDto)
     {
         return userService.signup(signUpRequestDto.getName(), signUpRequestDto.getEmail(),signUpRequestDto.getPassword());
     }
     @PostMapping("/logout")
     public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws Exception {
          userService.logout(logoutRequestDto.getToken());
          return new ResponseEntity<>(HttpStatus.OK);
     }
     @PostMapping("/validating/{token}")
     public UserDto validateToken(@PathVariable("token")@NonNull String token)
     {
         return UserDto.from(userService.validateToken(token));
     }
}
