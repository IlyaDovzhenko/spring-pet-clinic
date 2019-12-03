package com.springframework.springpetclinic.bootstrap;

import com.springframework.springpetclinic.model.*;
import com.springframework.springpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialtyService specialtyService;

    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService,
                      PetService petService,
                      SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.specialtyService = specialtyService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
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
        petService.save(pet1);

        Pet pet2 = new Pet();
        pet2.setName("Meo-Meo");
        pet2.setPetType(savedCatPetType);
        pet2.setBirthDate(LocalDate.now());
        petService.save(pet2);

        Pet pet3 = new Pet();
        pet3.setName("Mew");
        pet2.setPetType(savedCatPetType);
        pet2.setBirthDate(LocalDate.now());
        //petService.save(pet3);

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
        //pet3.setOwner(owner2);
        owner2.getPets().add(pet2);
        //owner2.getPets().add(pet3);
        ownerService.save(owner2);

        System.out.println("Loading owners by " + ownerService.getClass().getName());

        Specialty radiology = new Specialty();
        radiology.setName("radiology");
        radiology.setDescription("radiology - is a science");
        specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setName("surgery");
        surgery.setDescription("surgery - is a science");
        specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setName("dentistry");
        dentistry.setDescription("dentistry - is a science");
        specialtyService.save(dentistry);

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Fisher");
        vet1.getSpecialties().add(radiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Alex");
        vet2.setLastName("Long");
        vet2.getSpecialties().add(surgery);
        vetService.save(vet2);

        System.out.println("Loading vets by " + vetService.getClass().getName());
    }
}
