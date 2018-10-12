# Programming Wars
_Programming Wars_ is a small game created for the purpose of carrying out a [Code Kata](https://en.wikipedia.org/wiki/Kata_(programming)). You're free to copy this project to perform a Code Kata as well.

## What is the game about?
In a 10x10 board, 4 pawns start at each corner. The pawns are:
* Red 🍎
* Green 🍏
* Blue 💙
* Yellow 🌻

One by one (turn-based), the pawns can move on the board in any direction (up, down, left, right, diagonally, don't move at all) by one cell each turn. However, they cannot move in cells where other pawns are at or go outside the board.

When a pawn visits a cell, it paints it with its color. For example, as the yellow pawn moves on the board, it paints cells that it visited with the yellow color. Visiting a cell which has another pawn's color replaces the previous color with the color of the pawn currently in the cell.

The winner of the game is the pawn that has covered the most cells with its color after 100 rounds, which is where the game ends.
The score is calculated by simply counting the number of cells with each pawn's color.

## How can this be used for a Code Kata?
### Implementation
Represent each pawn with a team of 2-3 people. Each team will be responsible for coding the moving behavior of its own pawn, competing with the rest of the teams for the highest score.

For each pawn, there is a `class` used to control its moving behavior.

|Pawn|class name|
|---|---|
|Red 🍎| `RedPawnBehavior`|
|Green 🍏| `GreenPawnBehavior`|
|Blue 💙| `BluePawnBehavior`|
|Yellow 🌻| `YellowPawnBehavior`|

In each behavior the teams will need to implement the `getMove(Board, BoardPosition): MovementOffset` method, which returns the amount of units to move in the X and Y coordinates:
* `Board` - the current state of the board. It can return the `Pawn` / `Trail` (paint left behind by pawns) at each position. There are some more helper methods, please refer to the javadoc.
* The current position of this pawn.
* The `Behavior` interface also has a helper method `getOwnTrail(Board, BoardPosition)` which can be used in case it's needed to distinguish the trail of the current pawn with the trails of other pawns.

### How to perform the kata

## Requirements
* Android Studio (required because the project use libGDX, a cross platform game engine for Desktop, Android, iOS and web)

To be able to run the project, you will need to create a _Run configuration_ within the IDE.
Follow the tutorial [here](https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA) to do so. The easiest one to choose is the **Desktop** configuration.