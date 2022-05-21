package com.gt.scr.download

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@ComponentScan("com.gt.scr.download")
@EnableWebFlux
open class Application {
    companion object {
        @JvmStatic
        fun main(args : Array<String>) {
            println("Starting Download Application...")
            try {
                runApplication<Application>(*args)
            } catch(e : Exception) {
                error("Error Occurred in startup: ${e.message}")
            }
            println("Started Download Application...")
        }
    }
}