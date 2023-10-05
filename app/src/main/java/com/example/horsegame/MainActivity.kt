package com.example.horsegame

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var board: Array<IntArray>

    private var cellSelected_x = 0
    private var cellSelected_y = 0

    private var options = 0

    private var nameColorBlack = "black_cell"
    private var nameColorWhite = "white_cell"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startScreenGame()
        resetBoard()
        initialPosition()

    }

    fun cellClicked(view: View){
        var name = view.tag.toString()
        var x = name.subSequence(1,2).toString().toInt()
        var y = name.subSequence(2,3).toString().toInt()

        checkCell(x,y)

    }

    //check if the cell that the player selected can ba painted.
    private fun checkCell(x: Int, y: Int) {

        /*
        0 cell is free
        1 cell is marked
        2 is a bonus
        9 option for the current movement
     */

        var dif_x = x - cellSelected_x
        var dif_y = y - cellSelected_y

        var checkTrue = false

        if(dif_x == 1 && dif_y == 2) checkTrue = true //right - top long
        if(dif_x == 1 && dif_y == -2) checkTrue = true //right - bottom long
        if(dif_x == 2 && dif_y == 1) checkTrue = true //right long- top
        if(dif_x == 2 && dif_y == -1) checkTrue = true //right long - bottom
        if(dif_x == -1 && dif_y == 2) checkTrue = true //left - top long
        if(dif_x == -1 && dif_y == -2) checkTrue = true //left - bottom long
        if(dif_x == -2 && dif_y == 1) checkTrue = true //left long - top
        if(dif_x == -2 && dif_y == -1) checkTrue = true //left long - bottom

        if(board[x][y] == 1) checkTrue = false

        if(checkTrue) selectCell(x , y)

    }

    private fun resetBoard(){
        /*
        0 cell is free
        1 cell is marked
        2 is a bonus
        9 option for the current movement
     */

        board = arrayOf(
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0,0),
        )
    }

    //Set the new position randomly
    private fun initialPosition(){
        var x = 0
        var y = 0

        x= (0..7).random()
        y =(0..7).random()

        cellSelected_x = x
        cellSelected_y = y

        selectCell(x,y)

    }

    private fun selectCell(x: Int, y: Int) {

        board[x][y] = 1

        //Change the background color for the previous selected cell color
        paintHorseCell(cellSelected_x, cellSelected_y, "previous_cell")

        //Save last position
        cellSelected_x = x
        cellSelected_y = y

        clearOptions()

        paintHorseCell(cellSelected_x, cellSelected_y, "selected_cell")

        checkOption(x,y)
    }

    private fun clearCellOptine(x: Int, y: Int) {

        var cell:ImageView = findViewById(resources.getIdentifier("c$x$y", "id", packageName))

        if(checkColorCell(x,y) == "black") {
            cell.setBackgroundResource(
                ContextCompat.getColor(
                    this, resources.getIdentifier(nameColorBlack, "color", packageName)
                )
            )
        }
            else cell.setBackgroundResource(ContextCompat.getColor(
            this,resources.getIdentifier(nameColorWhite,"color",packageName)))
    }

    private fun clearOptions() {
        for(i in 0..7){
            for(j in 0..7){
                if(board[i][j] == 9 || board[i][j] == 2) {
                    if(board[i][j] == 9) {
                        board[i][j] = 0
                        clearCellOptine(i,j)
                    }

                }
            }
        }
    }



    //check all the movement options
    private fun checkOption(x: Int, y: Int){
        options = 0

        checkMove(x, y ,1 ,2) //right - top long
        checkMove(x, y, 1 ,-2)  //right - bottom long
        checkMove(x, y, 2, 1) //right long- top
        checkMove(x, y, 2, -1) //right long - bottom
        checkMove(x, y, -1, 2) //left - top long
        checkMove(x, y, -1, -2)  //left - bottom long
        checkMove(x, y, -2, 1)  //left long - top
        checkMove(x ,y, -2, -1)  //left long - bottom

        var optionsData = findViewById<TextView>(R.id.optionsData)
        optionsData.text = options.toString()

    }

    private fun checkMove(x: Int, y: Int, movX: Int, movY: Int) {

        var option_x = x + movX
        var option_y = y + movY

        if(option_x in 0..7 && option_y in 0..7) {

            if(board[option_x][option_y] == 0 || board[option_x][option_y] == 2) {
                options++

                paintOptions(option_x, option_y)

                board[option_x][option_y]
            }
        }

    }

    private fun paintOptions(optionX: Int, optionY: Int) {
        var cell:ImageView = findViewById(resources.getIdentifier("c$optionX$optionY", "id", packageName))
        if(checkColorCell(optionX,optionY) == "black") cell.setBackgroundResource(R.drawable.option_black)
        else cell.setBackgroundResource(R.drawable.option_white)
        
    }

    private fun checkColorCell(x: Int, y: Int): String {
        var color = ""
        //cells where the black cells are located
        var blackColumn_x = arrayOf(0,2,4,6)
        var blackrow_x = arrayOf(1,3,5,7)

        if(blackColumn_x.contains(x) && blackColumn_x.contains(y) || blackrow_x.contains(x) && blackrow_x.contains(y)) color = "black"
        else color = "white"

        return color
    }

    //Paint the horse on the selected cell
    private fun paintHorseCell(cellselectedX: Int, cellselectedY: Int, color: String) {
        //Identify the cells that was selected
        var cell:ImageView = findViewById(resources.getIdentifier("c$cellselectedX$cellselectedY", "id", packageName))
        //Change the background color for the selected cell
        cell.setBackgroundColor(ContextCompat.getColor(this,resources.getIdentifier(color,"color",packageName)))
        //paint the horse on the selected cell
        cell.setImageResource(R.drawable.horse)
    }


    private fun startScreenGame() {
        setBoardSize()
        hideMessage()
    }

    //Set the cells size of the board depending on the mobile's screen size
    private fun setBoardSize() {
        var cell: ImageView

        //Save the display
        val display = windowManager.defaultDisplay

        //Calculate the display size
        val size = Point()
        display.getSize(size)
        val  width = size.x

        //Transform the screen size in dp
        var width_dp = (width / resources.displayMetrics.density)

        var lateralMarginsDP = 0
        val cellWidth = (width_dp - lateralMarginsDP) / 8
        val cellHeight = cellWidth

        for(column in 0..7) {
            for (row in 0..7) {
                //find the cell's id
                cell = findViewById(resources.getIdentifier("c$column$row", "id", packageName))

                var height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,cellHeight, resources.displayMetrics).toInt()
                var width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,cellWidth, resources.displayMetrics).toInt()

                cell.layoutParams = TableRow.LayoutParams(width, height)
            }
        }
    }

    private fun hideMessage() {
        var endGameMessage = findViewById<LinearLayout>(R.id.endGameMessage)
        endGameMessage.visibility = View.INVISIBLE
    }
}