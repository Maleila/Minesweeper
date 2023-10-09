package hu.ait.minesweeper.model

object MinesweeperModel {

    public val CLEAR: Int = 0
    public val MINE: Int = 1

    //should probably put this in a loop later...
    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
    )

    val minesList: Array<Array<Int>> = arrayOf(arrayOf(0, 0), arrayOf(0, 0), arrayOf(0, 0), arrayOf(0, 0))
    private var mines = 3
    private var notMines = 0

    init {
        resetModel()
    }

    fun resetModel() {
        //reset back to empty model
        for (i in 0..fieldMatrix.size - 1) {
            for (j in 0..fieldMatrix[i].size -1 ) {
                fieldMatrix[i][j] = Field(CLEAR, 0, false, false)
            }
        }
        for(i in 0..mines) {
            var r = (0..fieldMatrix.size - 1).random()
            var c = (0..fieldMatrix[0].size - 1).random()
            fieldMatrix[r][c].changeType(MINE)
            updateNeighbors(r, c)
            minesList[i] = arrayOf(r, c)
        }
        notMines = fieldMatrix.size* fieldMatrix[0].size - mines
    }

    fun updateNeighbors(r: Int, c: Int) {
        var up = r > 0
        var down = r < fieldMatrix.size - 1
        var right = c < fieldMatrix[r].size - 1
        var left = c > 0

        if(up) {
            fieldMatrix[r-1][c].addMineNearby()
        }
        if(down) {
            fieldMatrix[r+1][c].addMineNearby()
        }
        if(left) {
            fieldMatrix[r][c-1].addMineNearby()
        }
        if(right) {
            fieldMatrix[r][c+1].addMineNearby()
        }
        if(up && left) {
            fieldMatrix[r-1][c-1].addMineNearby()
        }
        if(up && right) {
            fieldMatrix[r-1][c+1].addMineNearby()
        }
        if(down && left){
            fieldMatrix[r+1][c-1].addMineNearby()
        }
        if(down && right) {
            fieldMatrix[r+1][c+1].addMineNearby()
        }
    }

    //rename this I keep forgetting what true/false means
    fun testSquare(r: Int, c: Int): Boolean {
        fieldMatrix[r][c].setClicked(true)
        if(fieldMatrix[r][c].type == MINE) {
            return true
        } else {
            notMines --
            return false
        }
    }

    fun checkWin(): Boolean {
        if(notMines == 0) //if all the CLEAR squares have been checked, return true
            return true
        for(m in minesList) { //if there are still undiscovered mines, return false
            if(!fieldMatrix[m[0]][m[1]].isFlagged) {
                return false
            }
        }
        return true
    }

    fun getField(r: Int, c: Int): Field {
        return fieldMatrix[r][c]
    }

    fun flag(r: Int, c: Int): Boolean {
        fieldMatrix[r][c].flag()
        if(fieldMatrix[r][c].type == MINE)
            return true
        return false
    }
}