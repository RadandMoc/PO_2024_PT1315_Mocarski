package model

data class Vector2d(val x: Int, val y: Int) {

    override fun toString(): String = "($x,$y)"

    operator fun plus(other: Vector2d): Vector2d = Vector2d(x + other.x, y + other.y)

    operator fun minus(other: Vector2d): Vector2d = Vector2d(x - other.x, y - other.y)

    operator fun compareTo(other: Vector2d): Int {
        val precedes = (x <= other.x) && (y <= other.y)
        val follows = (x >= other.x) && (y >= other.y)
        return when {
            precedes && follows -> 0
            precedes -> -1
            else -> 1
        }
    }

    fun upperRight(other: Vector2d): Vector2d =
        Vector2d(maxOf(x, other.x), maxOf(y, other.y))

    fun lowerLeft(other: Vector2d): Vector2d =
        Vector2d(minOf(x, other.x), minOf(y, other.y))

    fun opposite(): Vector2d = Vector2d(-x, -y)

}

fun MapDirection.toUnitVector(): Vector2d = when (this) {
    MapDirection.NORTH -> Vector2d(0, 1)
    MapDirection.SOUTH -> Vector2d(0, -1)
    MapDirection.EAST -> Vector2d(1, 0)
    MapDirection.WEST -> Vector2d(-1, 0)
}