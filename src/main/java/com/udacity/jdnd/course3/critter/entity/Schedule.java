package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;

import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    private Schedule[] schedules;

    @OneToOne
    private Employee employee;

    @OneToMany
    private List<Pet> pet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schedule[] getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }
}
