package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedule(){
        Iterable<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> scheduleList = new ArrayList<>();
        schedules.forEach(scheduleList::add);
        return scheduleList;
    }

    public List<Schedule> getScheduleForPet(Pet pet){
        return scheduleRepository.findAllByPets(pet);
    }

    public List<Employee> getScheduleForEmployee(Employee employee){
        return scheduleRepository.findAllByEmployees(employee);
    }
}
