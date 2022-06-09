package com.gt.scr.imdb.ext.people;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.people.domain.Person;
import com.gt.scr.imdb.ext.people.domain.Persons;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class PeoplesClient implements PeoplesReader {

    private final DataReader dataReader;

    public PeoplesClient(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    public Mono<Person> getPersonBy(String personId) {
        return dataReader.fetchRowUsingIndexKey(personId)
                .map(PersonRow::from)
                .map(Person::from)
                .next();
    }

    @Override
    public Mono<Persons> getPersonBy(List<String> persons) {
        return Flux.fromIterable(persons)
                        .flatMap(dataReader::fetchRowUsingIndexKey)
                .map(PersonRow::from)
                .map(Person::from)
                .collectMap(Person::personId, Function.identity())
                .map(Persons::new);
    }
}
