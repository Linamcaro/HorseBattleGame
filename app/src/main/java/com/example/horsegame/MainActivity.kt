package com.example.horsegame

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow

class MainActivity : AppCompatActivity() {

    private var cellSelected_x = 0
    private var cellSelected_y = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startScreenGame()
        initialPosition()

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
        paintHorseCell(cellSelected_x, cellSelected_y, "previous_cell")

        cellSelected_x = x
        cellSelected_y = y

        paintHorseCell(cellSelected_x, cellSelected_y, "selected_cell")
    }

    private fun paintHorseCell(cellselectedX: Int, cellselectedY: Int, s: String) {
        var cell:ImageView = findViewById(resources.getIdentifier("c$column$row", "id", packageName))
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