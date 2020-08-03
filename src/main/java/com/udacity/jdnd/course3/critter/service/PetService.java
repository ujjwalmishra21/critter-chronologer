package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;

    public Pet savePet(Pet pet){
        pet = petRepository.save(pet);
        customerService.addPetToCustomer(pet,pet.getCustomer());
        return pet;
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

    public List<Pet> findAllByOwner(Customer customer){
        return petRepository.findAllByCustomer(customer);
    }

    public List<Pet> getAllPetById(List<Long> ids){
        Iterable<Pet> pets = petRepository.findAllById(ids);
        List<Pet> petList = new ArrayList<>();
        pets.forEach(petList::add);
        return petList;
    }

    public void addScheduleForPet(List<Pet> petList, Schedule schedule){
        petList = new CopyOnWriteArrayList<>(petList);
        if(petList != null){
            for(Pet pet:petList){
                List<Schedule> schedules = pet.getSchedules();
                if(schedules != null){
                    schedules.add(schedule);
                }else{
                    schedules = new ArrayList<>();
                    schedules.add(schedule);
                }
                pet.setSchedules(schedules);
                petRepository.save(pet);
            }
        }
    }

}
