package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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



}
