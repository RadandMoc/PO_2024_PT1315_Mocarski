package model

enum class MapDirection(val direction: Vector2d) {
    NORTH(Vector2d(0, 1)),
    SOUTH(Vector2d(0, -1)),
    WEST(Vector2d(-1, 0)),
    EAST(Vector2d(1, 0));

    override fun toString(): String = when (this) {
        NORTH -> "Północ"
        SOUTH -> "Południe"
        WEST -> "Zachód"
        EAST -> "Wschód"
    }

    fun next(): MapDirection = when (this) {
        NORTH -> EAST
        SOUTH -> WEST
        WEST -> NORTH
        EAST -> SOUTH
    }

    fun previous(): MapDirection = when (this) {
        NORTH -> WEST
        SOUTH -> EAST
        WEST -> SOUTH
        EAST -> NORTH
    }

}
