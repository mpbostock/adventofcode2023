package mpbostock

fun <T> T.repeat(n: Int): List<T> = (1..n).map { this }