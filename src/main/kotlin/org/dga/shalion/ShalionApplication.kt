package org.dga.shalion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShalionApplication

fun main(args: Array<String>) {
    runApplication<ShalionApplication>(*args)
}
