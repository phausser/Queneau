package de.binaerraum.queneau

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QueneauApplication

fun main(args: Array<String>) {
    runApplication<QueneauApplication>(*args)
}
