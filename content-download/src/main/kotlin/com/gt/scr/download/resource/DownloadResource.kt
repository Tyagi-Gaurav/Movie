package com.gt.scr.download.resource

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class DownloadResource {

    @GetMapping(
        path = ["/movie/download"],
        produces = ["application/vnd.movie.download.v1+json"]
    )
    @ResponseStatus(code = HttpStatus.OK)
    fun getMovie(): Mono<String> = Mono.just("Hello World." )
}