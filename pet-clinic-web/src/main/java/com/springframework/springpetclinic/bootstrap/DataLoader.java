package com.springframework.springpetclinic.bootstrap;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.model.Pet;
import com.springframework.springpetclinic.model.PetType;
import com.springframework.springpetclinic.model.Vet;
import com.springframework.springpetclinic.services.OwnerService;
import com.springframework.springpetclinic.services.PetTypeService;
import com.springframework.springpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Pet pet1 = new Pet();
        pet1.setName("Gav-Gav");
        pet1.setPetType(savedDogPetType);
        pet1.setBirthDate(LocalDate.now());

        Pet pet2 = new Pet();
        pet2.setName("Meo-Meo");
        pet2.setPetType(savedCatPetType);
        pet2.setBirthDate(LocalDate.now());

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setCity("New York");
        owner1.setAddress("124 Caringhton");
        owner1.setTelephone("1234455246");
        pet1.setOwner(owner1);
        owner1.getPets().add(pet1);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glendale");
        owner2.setCity("Miami");
        owner2.setAddress("4 Millwall");
        owner2.setTelephone("1232134233234");
        pet2.setOwner(owner2);
        owner2.getPets().add(pet2);
        ownerService.save(owner2);

        System.out.println("Loading owners by " + ownerService.getClass().getName());

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Fisher");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Alex");
        vet2.setLastName("Long");

        vetService.save(vet2);

        System.out.println("Loading vets by " + vetService.getClass().getName());
    }
}
