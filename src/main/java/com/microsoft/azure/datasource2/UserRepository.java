package com.microsoft.azure.datasource2;

import com.azure.data.cosmos.internal.directconnectivity.Address;
import com.microsoft.azure.spring.data.cosmosdb.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<User, String> {

    Flux<User> findByName(String firstName);

    Flux<User> findByEmailAndAddress(String email, Address address);

    Flux<User> findByEmailOrName(String email, String name);

}