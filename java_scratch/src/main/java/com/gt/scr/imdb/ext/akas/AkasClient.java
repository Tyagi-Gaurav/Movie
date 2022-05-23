package com.gt.scr.imdb.ext.akas;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AkasClient implements AkasReader {

    private final ConcurrentHashMap<String, Aka> akasMap;
    private final List<String> akasAsString;
    private final AtomicLong totalItems = new AtomicLong(0);

    public AkasClient(DataReader dataReader) {
        Flux<String> stringFlux = dataReader.readAll();
        akasMap = new ConcurrentHashMap<>();
        akasAsString = new ArrayList<>();
        stringFlux
                //.map(AkaRow::from)
                .subscribe(akaConsumer -> {
                    long l = totalItems.incrementAndGet();
                    if (l % 1000 == 0) {
                        System.out.println("Adding : " + l);
                    }
//                    akasMap.compute(akaConsumer.titleId(), (key, oldValue) -> {
//                        if (oldValue != null) {
//                            return oldValue.add(akaConsumer);
//                        } else {
//                            return Aka.from(akaConsumer);
//                        }
//                    });
                    akasAsString.add(akaConsumer);
                });
        //System.out.println("Total items subscribed: " + totalItems.get());
    }

    @Override
    public Mono<Aka> getTitleById(String titleId) {
        return Mono.justOrEmpty(akasMap.get(titleId));
    }

    @Override
    public long getTotalNumberOfTitles() {
        return totalItems.incrementAndGet();
    }
}
