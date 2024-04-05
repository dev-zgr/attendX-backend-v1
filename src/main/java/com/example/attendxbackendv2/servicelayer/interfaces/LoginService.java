package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.UserBaseDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.InvalidCredentialsException;

import java.util.UUID;

public interface LoginService {
    UUID login(String email, String password) throws InvalidCredentialsException;
    boolean logout(String token);
    String validateToken(String token);
    UserBaseDTO getUserByToken(UUID token) throws InvalidCredentialsException;
}
