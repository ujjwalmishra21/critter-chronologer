package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    public Pet getPetById(Long id){
        Optional<Pet> petOptional = petRepository.findById(id);

        if(petOptional.isPresent()){
            return petOptional.get();
        }
        return null;
    }

    public Iterable<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> findAllByOwner(Long id){
        return petRepository.findAllByCustomer(id);
    }
}
