package mpbostock

import kotlin.math.abs

interface Grid<T> {
    val cells: Array<Array<T>>
    val defaultCell: T
    val width: Int
        get() = cells[0].size
    val height: Int
        get() = cells.size
    val coordinates: Set<Coordinate>
    fun getCell(pos: Coordinate) = if (validPos(pos)) cells[pos.y][pos.x] else defaultCell
    private fun validPos(pos: Coordinate) = pos.x in 0 until width && pos.y in 0 until height
    fun Coordinate.asPositionedCell() = PositionedCell(this, getCell(this))
    fun Coordinate.move(mover: PositionMover) = getCell(mover.move(this))

    companion object {
        inline fun <reified T> fromInput(input: List<String>, crossinline charMapper: (Char) -> T, defaultCell: T): Grid<T> {
            return object: Grid<T>{
                override val cells: Array<Array<T>>
                    get() = input.map { line -> line.map { charMapper(it) }.toTypedArray() }.toTypedArray()
                override val defaultCell: T
                    get() = defaultCell
                override val coordinates: Set<Coordinate> by lazy {
                    (0 until height).fold(emptySet()) { acc, y ->
                        acc + (0 until width).map { Coordinate(it, y) }
                    }
                }
            }
        }
    }
}

data class Coordinate(val x: Int, val y: Int)
data class Line(val start: Coordinate, val end: Coordinate) {
    fun stepsBetween(): Int {
        return abs(end.x - start.x) + abs(end.y - start.y)
    }
}
data class PositionedCell<T>(val pos: Coordinate, val cell: T)
interface PositionMover {
    fun move(pos: Coordinate): Coordinate
}