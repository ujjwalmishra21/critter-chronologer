package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO){
        throw new UnsupportedOperationException();
    }
    @PostMapping("/{ownerId}")
    public PetDTO save(@RequestBody PetDTO petDTO, @PathVariable Long ownerId) {

        petDTO.setOwnerId(ownerId);
        Pet pet = convertPetDTOToPet(petDTO);
        Customer customer = customerService.getCustomerById(ownerId);
        pet.setCustomer(customer);
        pet = petService.savePet(pet);
        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        Iterable<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOS = new ArrayList<>();
        for(Pet pet:pets){
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.findAllByOwner(ownerId);
        List<PetDTO> petDTOS = new ArrayList<>();
        for(Pet pet:pets){
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    public static PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    public static Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
        return pet;
    }
}
