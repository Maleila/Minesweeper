package hu.ait.minesweeper.model

data class Field(var type: Int, var minesAround: Int, var isFlagged: Boolean, var wasClicked: Boolean, var showsNumber: Boolean) {
//are these all public? do I even need the getters...
    fun addMineNearby() {
        minesAround++
    }

    fun changeType(newType: Int) {
        type = newType
    }

    fun setClicked(clicked: Boolean) {
        wasClicked = clicked
    }

    fun showNumber() {
        showsNumber = true
    }


}
