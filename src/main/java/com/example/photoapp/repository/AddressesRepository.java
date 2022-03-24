package com.example.photoapp.repository;

import com.example.photoapp.entity.AddressEntity;
import com.example.photoapp.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressesRepository extends CrudRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userDetails);

    AddressEntity findByAddressId(String addressId);
}