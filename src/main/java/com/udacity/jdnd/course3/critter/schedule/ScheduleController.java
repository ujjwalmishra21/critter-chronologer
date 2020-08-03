package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Employee> employees = employeeService.getAllEmployeeById(scheduleDTO.getEmployeeIds());
        List<Pet> pets = petService.getAllPetById(scheduleDTO.getPetIds());
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        if(employees.size() > 0)
            schedule.setEmployee(employees);
        if(pets.size() > 0)
            schedule.setPet(pets);

        schedule = scheduleService.saveSchedule(schedule);

        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedule();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        if(pet == null){
            return null;
        }
        List<Schedule> schedules = scheduleService.getScheduleForPet(pet);

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if(employee == null){
            return null;
        }
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employee);

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if(customer == null){
            return null;
        }
        List<Pet> pets = petService.findAllByOwner(customer);
        if(pets.size() == 0){
            return null;
        }
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> scheduleList = new ArrayList<>();
        for(Pet pet:pets){
            scheduleList.addAll(scheduleService.getScheduleForPet(pet));
        }
        for(Schedule schedule:scheduleList){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    public static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        List<Employee> employees = schedule.getEmployee();
        if(employees != null){
            List<Long> employeeIds = new ArrayList<>();
            for(Employee employee:employees){
                employeeIds.add(employee.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        List<Pet> pets = schedule.getPet();
        if(pets != null) {

            List<Long> petIds = new ArrayList<>();
            for (Pet pet : pets) {
                petIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(petIds);
        }
        return scheduleDTO;
    }

    public static Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        return schedule;
    }

}
