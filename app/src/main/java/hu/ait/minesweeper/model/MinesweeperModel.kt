package hu.ait.minesweeper.model

object MinesweeperModel {

    val CLEAR: Int = 0
    val MINE: Int = 1

    private val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
    )

    var minesList: MutableList<MutableList<Int>> = mutableListOf(mutableListOf(0, 0))
    private val mines = 3
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
        createMines()
    }

    private fun createMines() {
        minesList.clear()
        var r: Int
        var c: Int
        for(i in 0..mines - 1) {
            do {
                r = (0..fieldMatrix.size - 1).random()
                c = (0..fieldMatrix[0].size - 1).random()
            } while(minesList.contains(listOf(r,c)))
            fieldMatrix[r][c].changeType(MINE)
            updateNeighbors(r, c)
            minesList.add(i, mutableListOf(r, c))
        }
        notMines = fieldMatrix.size* fieldMatrix[0].size - minesList.size
    }

    private fun updateNeighbors(r: Int, c: Int) {
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

    fun testMine(r: Int, c: Int): Boolean {
        fieldMatrix[r][c].setClicked()
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