package com.btw.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by gplzh on 2016/5/29.
 */
@RestResource(exported = false)
interface UserAuthorizeRepository extends JpaRepository<UserAuthorize, Long> {

    UserAuthorize findByIdentityTypeAndIdentifierAndCredential(int identityType, String identifier, String credential);

    UserAuthorize findByIdentifier(String identifier);

    UserAuthorize findByUserId(long userId);

}
