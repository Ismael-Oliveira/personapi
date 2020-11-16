package roca.made.personapi.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roca.made.personapi.dto.request.PersonDTO;
import roca.made.personapi.dto.response.MessageResponseDTO;
import roca.made.personapi.entity.Person;
import roca.made.personapi.exception.PersonNotFoundException;
import roca.made.personapi.mapper.PersonMapper;
import roca.made.personapi.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personTosaved = personMapper.toModel(personDTO);

        Person personSaved = personRepository.save(personTosaved);

        return new MessageResponseDTO("Created "+personSaved.toString());

    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {

        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);

        return createMessageResponse( "Updated person with ID "+id);
    }

    public void delete(Long id) throws  PersonNotFoundException{
        verifyIfExists(id);
        personRepository.deleteById(id);
    }


    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(String message) {
        return new MessageResponseDTO(message);
    }
}
