package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

//    private List<Long> employeeIds;
//    private List<Long> petIds;
    private LocalDate date;
    @ElementCollection
    private Set<EmployeeSkill> activities;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="schedule_employee",
        joinColumns = @JoinColumn(name="schdeule_emp_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="schdeule_pet",
        joinColumns = @JoinColumn(name = "schedule_pet_id"),
        inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Long> getEmployeeIds() {
//        return employeeIds;
//    }
//
//    public void setEmployeeIds(List<Long> employeeIds) {
//        this.employeeIds = employeeIds;
//    }
//
//    public List<Long> getPetIds() {
//        return petIds;
//    }
//
//    public void setPetIds(List<Long> petIds) {
//        this.petIds = petIds;
//    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public List<Employee> getEmployee() {
        return employees;
    }

    public void setEmployee(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPet() {
        return pets;
    }

    public void setPet(List<Pet> pets) {
        this.pets = pets;
    }
}
