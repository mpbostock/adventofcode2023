package mpbostock

fun <T> T.repeat(n: Int): List<T> = (1..n).map { this }
fun IntRange.intersects(other: IntRange): Boolean = this.contains(other.first) || this.contains(other.last)
fun String.toListOfInts(delimiter: Char = ' '): List<Int> = this.trim().split(delimiter).map { it.toInt() }
fun String.toListOfLongs(delimiter: Char = ' '): List<Long> = this.trim().split(delimiter).map { it.toLong() }
fun String.squashSpaces(): String = this.replace("""\s+""".toRegex(), " ")
fun String.squashAndTrim(): String = this.squashSpaces().trim()