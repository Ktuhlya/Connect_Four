package connectfour

import java.lang.reflect.Array
import java.util.*

var name1 = ""
var name2 = ""

var rowM =6
var colM =7

var board = mutableListOf<MutableList<String>>()

fun main() {


    println("Connect Four")
    print("First player's name:\n>")
    name1 = readLine()!!
    print("Second player's name:\n>")
    name2 = readLine()!!
    Dimension().dimensionPrint()


}

class Dimension() {

    fun dimensionPrint(){
        println("Set the board dimensions (Rows x Columns)")
        print("Press Enter for default (6 x 7)\n>")
       dimensionChek(readLine()!!.trim())
    }

  private  fun dimensionChek (string: String) {

      if (string=="") {
          rowM = 6
          colM = 7
          println("$name1 VS $name2")
          print("$rowM X $colM board")
          drawBoard()
      } else{
   val regex = Regex(pattern = "\\d*\\d\\s*X\\s*\\d\\d*")

    if (regex.matches(string.uppercase(Locale.getDefault()))) {
        val regexD =Regex("\\d+")
        val list = regexD.findAll(string).map { it.value }.toList()
        val row = list[0].toInt()
        val col =  list[1].toInt()
       if(row !in 5..9) {
           println("Board rows should be from 5 to 9")
           dimensionPrint()
       }else{
           if(col !in 5..9) {
               println("Board columns should be from 5 to 9")
               dimensionPrint()
       }else{
           rowM = row
               colM = col
               println("$name1 VS $name2")
               println("$rowM X $colM board")
               drawBoard()
           }

      }

    }else{
    println("Invalid input")

       dimensionPrint()
    }
    }
  }
}

fun drawBoard () {
    var indexList = mutableListOf<String>()
    var bottomList = mutableListOf<String>()
    for (i in 0 .. rowM-1) {
        val listRow = mutableListOf<String>()
     board.add(listRow)
        for (j in 0..colM*2+1){
            if ((j % 2) > 0) board[i].add(" ") else board[i].add("|")
        }
    }
    for(i in 0..colM*2) {
        if (i % 2 > 0) indexList.add("${i/2+1}") else indexList.add(" ")
    }
    board.add(0, indexList)
    for(i in 0..colM*2) {
        bottomList.add("=")
    }
    board.add(rowM+1, bottomList)
    for( i in 0 ..rowM+1)
    println(board[i].joinToString(""))

}

/*

    board.add(0, indexList)




 */