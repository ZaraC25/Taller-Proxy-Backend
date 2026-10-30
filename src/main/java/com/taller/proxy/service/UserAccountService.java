package com.taller.proxy.service;

import com.taller.proxy.model.UserAccount;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    private UserAccount user = new UserAccount();
    public UserAccount getUser() { return user; }
}