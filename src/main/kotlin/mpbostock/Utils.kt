package mpbostock

fun <T> T.repeat(n: Int): List<T> = (1..n).map { this }
fun IntRange.intersects(other: IntRange): Boolean = this.contains(other.first) || this.contains(other.last)
fun String.toListOfInts(delimiter: Char = ' '): List<Int> = this.trim().split(delimiter).map { it.toInt() }
fun String.toListOfLongs(delimiter: Char = ' '): List<Long> = this.trim().split(delimiter).map { it.toLong() }
fun String.squashSpaces(): String = this.replace("""\s+""".toRegex(), " ")
fun String.squashAndTrim(): String = this.squashSpaces().trim()
fun <T> Sequence<T>.indefinitely(): Sequence<T> = generateSequence(this) { this  }.flatten()
fun lowestCommonMultiple(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}
fun Int.isOdd(): Boolean = this % 2 == 1