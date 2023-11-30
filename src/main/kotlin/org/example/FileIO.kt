package org.example

object FileIO {
    fun <T> readInput(filename: String, lineMapper: (String) -> T): List<T> {
        return this.javaClass.getResourceAsStream(filename)!!
            .bufferedReader()
            .readLines()
            .map(lineMapper)
    }
}