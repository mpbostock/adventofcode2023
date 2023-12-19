package mpbostock

object Day10 {
    fun partOne(grid: TileGrid): Int {
        val loopCoords = grid.loop
        return (loopCoords.size + 1) / 2
    }

    fun partTwo(grid: TileGrid): Int {
        val insideLoop = grid.insideLoop
        grid.print()
        return insideLoop.count()
    }

    private val input = FileIO.readInput("day10input.txt") { s -> s }

    @JvmStatic
    fun main(args: Array<String>) {
        val grid = TileGrid.fromInput(input)
        val partOneSolution = partOne(grid)
        println(partOneSolution)

        val partTwoSolution = partTwo(grid)
        println(partTwoSolution)
    }

    enum class Tile(val symbol: Char, val print: Char = ' ') : PipeConnections<Direction> {
        VerticalPipe('|', '│') {
            override val validConnections: Set<Direction> by lazy { setOf(Direction.North, Direction.South) }
        },
        HorizontalPipe('-', '─') {
            override val validConnections: Set<Direction> by lazy { setOf(Direction.East, Direction.West) }
        },
        BottomLeftBend('L', '╰') {
            override val validConnections: Set<Direction> by lazy { setOf(Direction.North, Direction.East) }
        },
        BottomRightBend('J', '╯') {
            override val validConnections: Set<Direction> by lazy { setOf(Direction.North, Direction.West) }
        },
        TopRightBend('7', '╮') {
            override val validConnections: Set<Direction> by lazy { setOf(Direction.South, Direction.West) }
        },
        TopLeftBend('F', '╭') {
            override val validConnections: Set<Direction> by lazy { setOf(Direction.South, Direction.East) }
        },
        Ground('.') {
            override val validConnections: Set<Direction> get() = emptySet()
        },
        StartingPosition('S', 'S') {
            override val validConnections: Set<Direction> get() = emptySet()
        };

        companion object {
            fun fromChar(c: Char): Tile {
                return Tile.values().single { it.symbol == c }
            }

            fun fromNESW(north: Tile, east: Tile, south: Tile, west: Tile): Tile {
                val validNorth = north in Direction.North
                val validSouth = south in Direction.South
                val validEast = east in Direction.East
                val validWest = west in Direction.West
                return when {
                    validNorth && validSouth -> VerticalPipe
                    validEast && validWest -> HorizontalPipe
                    validNorth && validEast -> BottomLeftBend
                    validNorth && validWest -> BottomRightBend
                    validSouth && validEast -> TopLeftBend
                    validSouth && validWest -> TopRightBend
                    else -> Ground
                }
            }
        }
    }

    interface PipeConnections<T> {
        val validConnections: Set<T>
        operator fun contains(tile: T): Boolean = validConnections.contains(tile)
    }

    enum class Direction : PositionMover, PipeConnections<Tile> {
        North {
            override fun move(pos: Coordinate): Coordinate = pos.copy(y = pos.y - 1)
            override val validConnections: Set<Tile> by lazy {
                setOf(
                    Tile.VerticalPipe,
                    Tile.TopLeftBend,
                    Tile.TopRightBend
                )
            }
        },
        South {
            override fun move(pos: Coordinate): Coordinate = pos.copy(y = pos.y + 1)
            override val validConnections: Set<Tile> by lazy {
                setOf(
                    Tile.VerticalPipe,
                    Tile.BottomLeftBend,
                    Tile.BottomRightBend
                )
            }
        },
        East {
            override fun move(pos: Coordinate): Coordinate = pos.copy(x = pos.x + 1)
            override val validConnections: Set<Tile> by lazy {
                setOf(
                    Tile.HorizontalPipe,
                    Tile.TopRightBend,
                    Tile.BottomRightBend
                )
            }
        },
        West {
            override fun move(pos: Coordinate): Coordinate = pos.copy(x = pos.x - 1)
            override val validConnections: Set<Tile> by lazy {
                setOf(
                    Tile.HorizontalPipe,
                    Tile.TopLeftBend,
                    Tile.BottomLeftBend
                )
            }
        }
    }

    data class PositionedTile(val pos: Coordinate, val tile: Tile)

    class TileGrid(private val grid: Grid<Tile>): Grid<Tile> by grid {
        private val startPos: Coordinate by lazy { coordinates.single { getCell(it) == Tile.StartingPosition } }
        private val start: PositionedCell<Tile> by lazy { PositionedCell(startPos, resolveStartTile()) }
        val loop: Set<Coordinate> by lazy { calculateLoop() }
        val insideLoop: Set<Coordinate> by lazy { calculateInsideLoop() }
        private fun connectingTiles(current: PositionedCell<Tile>) =
            current.cell.validConnections.map {
                it to it.move(current.pos).asPositionedCell()
            }.filter { it.second.cell in it.first }.map { it.second }

        private fun calculateLoop(): Set<Coordinate> {
            val startingPaths = connectingTiles(start)
            tailrec fun walkPaths(
                paths: List<PositionedCell<Tile>>,
                acc: Set<Coordinate>
            ): Set<Coordinate> {
                return if (paths.all { it.pos == paths[0].pos }) {
                    acc
                } else {
                    val newPaths =
                        paths.map { connectingTiles(it).single { tile -> !acc.contains(tile.pos) } }
                    walkPaths(newPaths, acc + newPaths.map { it.pos }.toSet())
                }
            }
            return walkPaths(startingPaths, setOf(startPos) + startingPaths.map { it.pos })
        }

        private fun calculateInsideLoop(): Set<Coordinate> {
            val notLoop = coordinates.filterNot { it in loop }
            return notLoop.fold(emptySet()) { acc, coordinate ->
                val allNSCrossingsToEast = allNSCrossingsEastOf(coordinate)
                val northCrossingCount = allNSCrossingsToEast.count { it in Direction.North }
                val southCrossingCount = allNSCrossingsToEast.count { it in Direction.South }
                if (northCrossingCount.isOdd() || southCrossingCount.isOdd()) acc + coordinate else acc
            }
        }

        private fun allNSCrossingsEastOf(pos: Coordinate): List<Tile> {
            return loop.map {
                val tile = if (it == startPos) start.cell else getCell(it)
                PositionedTile(it, tile)
            }.filter {
                it.pos.y == pos.y && it.pos.x > pos.x && (it.tile in Direction.North || it.tile in Direction.South)
            }.map { it.tile }
        }

        fun print() {
            (0 until height).onEach { y ->
                (0 until width).onEach { x ->
                    val coordinate = Coordinate(x, y)
                    print(if (coordinate in loop) getCell(coordinate).print else if (coordinate in insideLoop) '*' else '.')
                }
                println()
            }
        }

        private fun resolveStartTile(): Tile = Tile.fromNESW(
            startPos.move(Direction.North),
            startPos.move(Direction.East),
            startPos.move(Direction.South),
            startPos.move(Direction.West)
        )

        companion object {
            fun fromInput(input: List<String>): TileGrid {
                return TileGrid(Grid.fromInput(input, Tile::fromChar, Tile.Ground))
            }
        }
    }
}