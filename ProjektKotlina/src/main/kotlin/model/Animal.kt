package model

import java.util.*

class Animal(
    initialPosition: Vector2d,
    private var orientation: MapDirection
) : WorldElement {

    private var position: Vector2d = initialPosition
        private set

    fun getOrientation(): MapDirection = orientation

    fun setPosition(position: Vector2d) {
        this.position = position
    }

    override fun getPosition(): Vector2d = position

    override fun toString(): String = when (orientation) {
        MapDirection.NORTH -> "^"
        MapDirection.SOUTH -> "v"
        MapDirection.WEST -> "<"
        MapDirection.EAST -> ">"
    }

    fun isAt(position: Vector2d): Boolean = this.position == position

    fun move(direction: MoveDirection, validator: MoveValidator) {
        val potentialMove = when (direction) {
            MoveDirection.RIGHT -> {
                orientation = orientation.next()
                null
            }
            MoveDirection.LEFT -> {
                orientation = orientation.previous()
                null
            }
            MoveDirection.FORWARD -> position + orientation.toUnitVector()
            MoveDirection.BACKWARD -> position - orientation.toUnitVector()
        }

        if (potentialMove != null && validator.canMoveTo(potentialMove)) {
            position = potentialMove
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Animal) return false
        return orientation == other.orientation && position == other.position
    }

    override fun hashCode(): Int = Objects.hash(orientation, position)
}
