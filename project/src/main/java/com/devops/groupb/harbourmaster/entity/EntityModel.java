//Based upone github.com/slabiak/AppointmentScheduler
//

package com.devops.groupb.harbourmaster.entity;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

//Super class for all entities to take common shared vars/code from

@MappedSuperclass
public class EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //Data type 

    public Integer getId() {
        return this.id; //return ID as type int 
    }

    public void setId(Integer id) {
        this.id = id; //set class id to passed input id
    }
}