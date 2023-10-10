package hu.ait.minesweeper.model

data class Field(var type: Int, var minesAround: Int, var isFlagged: Boolean, var wasClicked: Boolean) {
    fun addMineNearby() {
        minesAround++
    }

    fun changeType(newType: Int) {
        type = newType
    }

    fun setClicked() {
        wasClicked = true
    }

    fun flag() {
        isFlagged = true
    }
}
