package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.models.Person;
import org.dim4es.springserver.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPersons(){
        return personService.getAllPersons();
    }

    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable("id") int id){
        System.out.println(id);
        return personService.getPersonByUserId(id);
    }

    @PostMapping(path = "/{id}")
    public void addPerson(@RequestBody Person person, @PathVariable("id") int id){
        personService.addPerson(person, id);
    }

    @PutMapping(path = "/{id}")
    public void updatePerson(@PathVariable("id") int id, @RequestBody Person person){
        personService.updatePerson(person, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePerson(@PathVariable("id") int id){
        personService.deletePersonById(id);
    }

}
