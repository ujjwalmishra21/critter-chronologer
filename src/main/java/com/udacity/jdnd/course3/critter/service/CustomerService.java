package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Iterable<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
          return optionalCustomer.get();
        }
        return null;
    }
    public Customer getCustomerByPet(Pet pet){
        Optional<Customer> customer = customerRepository.findByPets(pet);
        if(customer.isPresent()){
            return customer.get();
        }
        return null;
    }

    public void addPetToCustomer(Pet pet, Customer customer){
        List<Pet> pets = customer.getPets();
        if(pets != null){
            pets.add(pet);
        }else{
            pets = new ArrayList<>();
            pets.add(pet);
        }
        customer.setPets(pets);
        customerRepository.save(customer);
    }
}
