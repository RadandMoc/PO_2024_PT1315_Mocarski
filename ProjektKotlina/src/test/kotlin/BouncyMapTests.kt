import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import model.*

class BouncyMapTests : FunSpec({

    test("should add an animal to the map") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(1, 1), MapDirection.NORTH)

        map.place(animal)

        map.objectAt(Vector2d(1, 1)) shouldBe animal
    }

    test("should bounce an animal if the position is occupied") {
        val map = BouncyMap(5, 5)
        val animal1 = Animal(Vector2d(1, 1), MapDirection.NORTH)
        val animal2 = Animal(Vector2d(2, 2), MapDirection.EAST)

        map.place(animal1)
        map.place(animal2)

        map.objectAt(Vector2d(1, 1)) shouldBe animal1
        map.objectAt(Vector2d(2, 2)) shouldBe animal2
    }

    test("should check if a position is inside the map boundaries") {
        val map = BouncyMap(5, 5)

        map.canMoveTo(Vector2d(3, 3)) shouldBe true
        map.canMoveTo(Vector2d(6, 6)) shouldBe false
    }

    test("should return animal or null at a given position") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(1, 1), MapDirection.NORTH)

        map.place(animal)

        map.objectAt(Vector2d(1, 1)) shouldBe animal
        map.objectAt(Vector2d(2, 2)) shouldBe null
    }

    test("should move an animal within the map boundaries") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(1, 1), MapDirection.NORTH)

        map.place(animal)
        map.move(animal, MoveDirection.FORWARD)

        map.objectAt(Vector2d(1, 2)) shouldBe animal
    }

    test("should not move an animal outside the map boundaries") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(0, 0), MapDirection.SOUTH)

        map.place(animal)
        map.move(animal, MoveDirection.FORWARD)

        map.objectAt(Vector2d(0, 0)) shouldBe animal
    }

    test("should not change animal position when turning right") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(1, 1), MapDirection.NORTH)

        map.place(animal)
        map.move(animal, MoveDirection.RIGHT)

        map.objectAt(Vector2d(1, 1)) shouldBe animal
    }

    test("should not change animal position when turning left") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(1, 1), MapDirection.NORTH)

        map.place(animal)
        map.move(animal, MoveDirection.LEFT)

        map.objectAt(Vector2d(1, 1)) shouldBe animal
    }

    test("should change animal orientation when turning right") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(1, 1), MapDirection.NORTH)

        map.place(animal)
        map.move(animal, MoveDirection.RIGHT)

        animal.getOrientation() shouldBe MapDirection.EAST
    }

    test("should change animal orientation when turning left") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(1, 1), MapDirection.NORTH)

        map.place(animal)
        map.move(animal, MoveDirection.LEFT)

        animal.getOrientation() shouldBe MapDirection.WEST
    }
})
