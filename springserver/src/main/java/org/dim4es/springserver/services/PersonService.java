package org.dim4es.springserver.services;

import org.dim4es.springserver.models.Person;
import org.dim4es.springserver.models.User;
import org.dim4es.springserver.repositories.PersonRepository;
import org.dim4es.springserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    public Person getPersonById(int id){
        Person person = personRepository.findById(id).orElse(null);
        if(person == null){
            return new Person("null", "null", -1);
        }
        return person;
    }

    public Person getPersonByUserId(int id){
        User user = userRepository.findById(id).get();
        Person person = user.getPerson();
        if(person == null){
            return new Person("null", "null", -1);
        }
        return person;
    }

    public void addPerson(Person person, int id){
        User user = userRepository.findById(id).get();
        user.setPerson(person);
        person.setUser(user);
        userRepository.save(user);
        personRepository.save(person);
    }

    public void updatePerson(Person person, int id){
        User user = userRepository.findById(id).get();


        if(user.getPerson() != null){
            if(person.getName() != null){
                user.getPerson().setName(person.getName());
            }
            if(person.getSurName() != null){
                user.getPerson().setSurName(person.getSurName());
            }
            if(person.getAge() > 0){
                user.getPerson().setAge(person.getAge());
            }

            person.setId(user.getPerson().getId());
        }


        user.setPerson(person);
        person.setUser(user);
        userRepository.save(user);
    }

    public void deletePersonById(int id){
        Optional<Person> optionalPerson = personRepository.findById(id);

        if(!optionalPerson.isPresent()){
            throw new IllegalStateException("User with this id not found!");
        }

        personRepository.delete(optionalPerson.get());
    }
}
