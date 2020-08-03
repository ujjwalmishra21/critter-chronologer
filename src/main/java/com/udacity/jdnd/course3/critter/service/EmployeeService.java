package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long id){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isPresent()){
            return employeeOptional.get();
        }
        return null;
    }
    public List<Employee> getAllEmployeeById(List<Long> ids){
        Iterable<Employee> employees = employeeRepository.findAllById(ids);
        List<Employee> employeeList = new ArrayList<>();
        employees.forEach(employeeList::add);
        return employeeList;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = getEmployeeById(employeeId);
        if(employee == null){
            return;
        }
        employee.setDaysAvailable(daysAvailable);
        saveEmployee(employee);
    }

    public List<Employee> getEmployeesForService(LocalDate date,Set<EmployeeSkill> skills){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Set<DayOfWeek> daySet = new HashSet<>();
        daySet.add(dayOfWeek);
        List<Employee> employees = employeeRepository.findByDaysAvailableInAndSkillsIn(daySet,skills);
        List<Employee> employeeList = new ArrayList<>();
        for(Employee employee: employees){
            if(employee.getSkills().containsAll(skills)){
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    public void addScheduleForEmployee(List<Employee> employeeList,Schedule schedule){

        if(employeeList != null){
            for(Employee employee:employeeList){
                List<Schedule> schedules = employee.getSchedules();
                if(schedules != null){
                    schedules.add(schedule);
                }else{
                    schedules = new ArrayList<>();
                    schedules.add(schedule);
                }
                employee.setSchedules(schedules);
                employeeRepository.save(employee);
            }
        }
    }

}
