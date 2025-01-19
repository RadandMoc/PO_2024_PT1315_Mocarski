package model

import model.util.IncorrectPositionException
import model.util.randomFreePosition
import model.util.randomPosition

class BouncyMap(
    private val width: Int,
    private val height: Int
) : WorldMap {

    private val animals: MutableMap<Vector2d, Animal> = mutableMapOf()

    override fun getId(): Int = hashCode()

    override fun getElements(): Collection<WorldElement> = animals.values

    override fun getCurrentBounds(): Boundary =
        Boundary(Vector2d(0, 0), Vector2d(width - 1, height - 1))

    override fun canMoveTo(position: Vector2d): Boolean =
        position.x in 0 until width && position.y in 0 until height

    override fun isOccupied(position: Vector2d): Boolean = animals.containsKey(position)

    override fun objectAt(position: Vector2d): WorldElement? = animals[position]

    override fun place(animal: Animal) {
        val currentPosition = animal.getPosition()

        if (!canMoveTo(currentPosition)) {
            throw IncorrectPositionException(currentPosition)
        }

        if (isOccupied(currentPosition)) {
            handleCollision(animal)
        } else {
            animals[currentPosition] = animal
        }
    }

    override fun move(animal: Animal, direction: MoveDirection) {
        val currentPosition = animal.getPosition()
        val oldAnimal = animals[currentPosition]

        if (oldAnimal == animal) {
            animal.move(direction, this)
            val newPosition = animal.getPosition()

            if (currentPosition != newPosition){
                animals.remove(currentPosition)
                place(animal)
            }
        }
        else{
            animal.move(direction, this)
            place(animal)
        }
    }

    private fun handleCollision(newAnimal: Animal) {
        val freePosition = animals.randomFreePosition(Vector2d(width, height))

        if (freePosition != null) {
            animals[freePosition] = newAnimal
        } else {
            val randomOccupiedPosition = animals.randomPosition()
            if (randomOccupiedPosition != null) {
                animals.remove(randomOccupiedPosition)
                newAnimal.setPosition(randomOccupiedPosition)
                animals[randomOccupiedPosition] = newAnimal
            }
        }
    }

    private fun MoveDirection.opposite(): MoveDirection = when (this) {
        MoveDirection.FORWARD -> MoveDirection.BACKWARD
        MoveDirection.BACKWARD -> MoveDirection.FORWARD
        MoveDirection.RIGHT -> MoveDirection.LEFT
        MoveDirection.LEFT -> MoveDirection.RIGHT
    }
}
