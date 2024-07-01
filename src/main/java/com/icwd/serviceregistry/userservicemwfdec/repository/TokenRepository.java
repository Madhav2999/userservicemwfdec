package com.icwd.serviceregistry.userservicemwfdec.repository;

import com.icwd.serviceregistry.userservicemwfdec.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long>
{
    Token save(Token token);
    Optional<Token> findByValue(String value);

    Optional<Token> findByValueAndDeletedEquals(String token, boolean isdeleted);

    Optional<Token> findByValueAndDeletedEqualsAndExpiryAtGreaterThan(String token,boolean isDeleted, Date expiryGreaterThan);
}
