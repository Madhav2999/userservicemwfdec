package com.icwd.serviceregistry.userservicemwfdec.service;

import com.fasterxml.jackson.databind.ser.std.ToEmptyObjectSerializer;
import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import com.icwd.serviceregistry.userservicemwfdec.models.Token;
import com.icwd.serviceregistry.userservicemwfdec.models.User;
import com.icwd.serviceregistry.userservicemwfdec.repository.TokenRepository;
import com.icwd.serviceregistry.userservicemwfdec.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenRepository tokenRepository;
    public User signup(String fullName,String email,String password)
    {
        User u =  new User();
        u.setEmail(email);
        u.setName(fullName);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));
        User user = userRepository.save(u);
        return user;
    }
    public Token login(String email, String password)
    {
        Optional<User>userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty())
        {
            return null;
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword()))
        {
            //password not matching exception
            return null;
        }
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);
        Date dateThirtyDaysLater = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(dateThirtyDaysLater);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }
    public void logout(String token) throws Exception
    {
        Optional<Token>tokenOptional = tokenRepository.findByValueAndDeletedEquals(token,false);
        if(tokenOptional.isEmpty())
        {
            throw new Exception("token doesn't exist");
        }
        Token token1 = tokenOptional.get();
        token1.setDeleted(true);
        tokenRepository.save(token1);
        return;
    }
    public User validateToken(String token)
    {
       Optional<Token>tkn = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token,false,new Date());
       if(tkn.isEmpty())
       {
           return null;
       }
       return tkn.get().getUser();
    }
}
