package model

interface MoveValidator {

    /**
     * Indicates if any object can move to the given position.
     *
     * @param position The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    fun canMoveTo(position: Vector2d): Boolean
}
