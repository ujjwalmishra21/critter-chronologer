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

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    public Schedule saveSchedule(Schedule schedule){
        schedule = scheduleRepository.save(schedule);
        if(schedule.getEmployee() != null)
            employeeService.addScheduleForEmployee(schedule.getEmployee(),schedule);
        if(schedule.getPet() != null)
            petService.addScheduleForPet(schedule.getPet(),schedule);
        return schedule;
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

    public List<Schedule> getScheduleForEmployee(Employee employee){
        return scheduleRepository.findAllByEmployees(employee);
    }

    public void addEmployeeToSchedule(List<Schedule> schedules,Employee employee){
        schedules = new CopyOnWriteArrayList<>(schedules);
        if(schedules != null){
            for (Schedule schedule:schedules){
                List<Employee> employees = schedule.getEmployee();
                if(employees != null){
                    employees.add(employee);
                }else{
                    employees = new ArrayList<>();
                    employees.add(employee);
                }
                schedule.setEmployee(employees);
                scheduleRepository.save(schedule);
            }
        }



    }
}
