package com.springframework.springpetclinic;

import com.springframework.springpetclinic.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringPetClinicApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringPetClinicApplication.class, args);

        Person person = new Person();
        person.setFirstName("Ivan");
        System.out.println(person.getFirstName());

    }
}
