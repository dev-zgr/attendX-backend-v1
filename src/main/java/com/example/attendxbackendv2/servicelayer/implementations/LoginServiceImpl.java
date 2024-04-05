package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.UserBaseEntity;
import com.example.attendxbackendv2.datalayer.repositories.UserRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.UserBaseDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.InvalidCredentialsException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.interfaces.LoginService;
import com.example.attendxbackendv2.servicelayer.mappers.UserGenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {


    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID login(String email, String password) throws InvalidCredentialsException {
        UserBaseEntity user = userRepository.findUserBaseEntityByEmailIgnoreCase(email).orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));
        if (user.getPassword().equals(password)) {
            UUID sessionToken = UUID.randomUUID();
            user.setSessionToken(sessionToken);
            userRepository.save(user);
            return sessionToken;
        } else {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

    }

    @Override
    public boolean logout(String token) {
       boolean isLoggedOut = false;
       try{
           UserBaseEntity userBaseEntity =  userRepository.findUserBaseEntityBySessionToken(UUID.fromString(token))
                   .orElseThrow(() -> new ResourceNotFoundException("User", "session_token", token));
           userBaseEntity.setSessionToken(null);
           userRepository.save(userBaseEntity);
       }catch (IllegalArgumentException e){
           return false;
       }
        isLoggedOut = true;
        return isLoggedOut;
    }

    @Override
    public String validateToken(String token) {
        try{
            return userRepository.findUserBaseEntityBySessionToken(UUID.fromString(token)).orElseThrow(
                    () -> new InvalidCredentialsException("Invalid Token")
            ).getUserType();
        } catch (IllegalArgumentException e){
            return "";
        }
    }

    @Override
    public UserBaseDTO getUserByToken(UUID token) throws InvalidCredentialsException {
        UserBaseEntity userBaseEntity =  userRepository.findUserBaseEntityBySessionToken(token).orElseThrow(() -> new InvalidCredentialsException("Invalid Token"));
        return UserGenericMapper.mapUserEntityToUserDTO(userBaseEntity, new UserBaseDTO(), new AddressDTO(), true);
    }
}
