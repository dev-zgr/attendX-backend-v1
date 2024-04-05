package com.example.attendxbackendv2.datalayer.repositories;

import com.example.attendxbackendv2.datalayer.entities.UserBaseEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserBaseEntity, Long> {
    Optional<UserBaseEntity> findUserBaseEntityByEmailIgnoreCase(String email);
    Optional<UserBaseEntity> findUserBaseEntityBySessionToken(UUID token);
}
