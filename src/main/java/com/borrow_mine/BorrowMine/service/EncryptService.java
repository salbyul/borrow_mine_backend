package com.borrow_mine.BorrowMine.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {

    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean isMatch(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
