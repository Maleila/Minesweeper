package hu.ait.minesweeper.model

data class Field(var type: Int, var minesAround: Int, var isFlagged: Boolean, var wasClicked: Boolean) {
//are these all public? do I even need the getters...
    fun addMineNearby() {
        minesAround++
    }

    fun changeType(newType: Int) {
        type = newType
    }

    //maybe I don't need a parameter for this... like it only goes one way right...
    fun setClicked(clicked: Boolean) {
        wasClicked = clicked
    }

    fun flag() {
        isFlagged = true
    }
}
