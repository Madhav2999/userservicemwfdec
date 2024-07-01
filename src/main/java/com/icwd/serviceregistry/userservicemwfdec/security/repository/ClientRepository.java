package com.icwd.serviceregistry.userservicemwfdec.security.repository;



import java.util.Optional;


import com.icwd.serviceregistry.userservicemwfdec.security.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
