package com.btw.account.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gplzh on 2016/5/29.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(long id){
        return userRepository.findById(id);
    }

    public User save(User user){
        return userRepository.save(user);
    }

}
