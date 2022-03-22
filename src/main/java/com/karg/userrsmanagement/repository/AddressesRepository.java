package com.karg.userrsmanagement.repository;

import com.karg.userrsmanagement.entity.AddressEntity;
import com.karg.userrsmanagement.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressesRepository extends CrudRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userDetails);

    AddressEntity findByAddressId(String addressId);
}