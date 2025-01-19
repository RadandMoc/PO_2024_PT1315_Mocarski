package model.util

import model.Vector2d

class IncorrectPositionException(position: Vector2d) : Exception("Position $position is not correct.")
