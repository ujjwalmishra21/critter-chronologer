package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
//    @Query("SELECT c from Customer AS c JOIN customer_pets AS cp ON c.id=cp.customer_id WHERE cp.pets_id=:id")
    public Customer findByPets(long id);
}
