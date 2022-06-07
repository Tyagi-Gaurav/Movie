package com.gt.scr.imdb.ext.people;

import com.gt.scr.imdb.ext.people.domain.Person;
import com.gt.scr.imdb.ext.people.domain.Persons;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PeoplesReader {
    Mono<Person> getPersonBy(String personId);

    Mono<Persons> getPersonBy(List<String> personId);
}
