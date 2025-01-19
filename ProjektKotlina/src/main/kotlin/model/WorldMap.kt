package model

import model.util.IncorrectPositionException

interface WorldMap : MoveValidator {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     * @throws IncorrectPositionException if the animal cannot be placed because the move is not valid.
     */
    @Throws(IncorrectPositionException::class)
    fun place(animal: Animal)

    /**
     * Moves an animal (if it is present on the map) according to the specified direction.
     * If the move is not possible, this method has no effect.
     */
    fun move(animal: Animal, direction: MoveDirection)

    /**
     * Return true if the given position on the map is occupied.
     */
    fun isOccupied(position: Vector2d): Boolean

    /**
     * Return a world element at a given position.
     */
    fun objectAt(position: Vector2d): WorldElement?

    /**
     * Return a collection (usually a list) of every element existing on the map.
     */
    fun getElements(): Collection<WorldElement>

    /**
     * Return the current boundary of the map.
     */
    fun getCurrentBounds(): Boundary

    /**
     * Return the ID of the map.
     */
    fun getId(): Int
}
