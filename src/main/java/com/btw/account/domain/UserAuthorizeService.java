package com.btw.account.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gplzh on 2016/5/29.
 */

@Service
public class UserAuthorizeService {

    @Autowired
    private UserAuthorizeRepository userAuthorizeRepository;

    public UserAuthorize findByIdentityTypeAndIdentifierAndCredential(int identityType, String identifier, String credential) {
        return userAuthorizeRepository.findByIdentityTypeAndIdentifierAndCredential(identityType, identifier, credential);
    }

    public UserAuthorize findByIdentifier(String identifier) {
        return userAuthorizeRepository.findByIdentifier(identifier);
    }

    public UserAuthorize save(UserAuthorize userAuthorize) {
        return userAuthorizeRepository.save(userAuthorize);
    }

    public UserAuthorize findByUserId(long userId) {
        return userAuthorizeRepository.findByUserId(userId);
    }
}
