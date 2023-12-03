package mpbostock

fun <T> T.repeat(n: Int): List<T> = (1..n).map { this }
fun IntRange.intersects(other: IntRange): Boolean = this.contains(other.first) || this.contains(other.last)