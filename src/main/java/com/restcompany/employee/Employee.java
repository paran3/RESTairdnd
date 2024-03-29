package com.restcompany.employee;

import com.restcompany.room.Room;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Slf4j
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private Status status;

    public Employee(){

    }

    public Employee(String firstName, String lastName, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }


    public Employee markBusy() {

        if (this.status != Status.FREE) {
            throw new IllegalStateException(
                    String.format("Employee must be in state free to set busy! Current status: %s", this.status));
        }

        this.status = Status.BUSY;

        return this;
    }

    public Employee markFree() {

        if (this.status != Status.BUSY) {
            throw new IllegalStateException(
                    String.format("Employee must be in state busy to set free! Current status: %s", this.status));
        }

        this.status = Status.FREE;

        return this;
    }

    public void cleaning(Integer roomNumber){
        try {
            LOG.info("Employee id : " + this.id + ", Room Number : " + roomNumber);
            LOG.info("start cleaning");
            Thread.sleep(10000);
            LOG.info("Employee id : " + this.id + ", Room Number : " + roomNumber);
            LOG.info("finish cleaning");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}