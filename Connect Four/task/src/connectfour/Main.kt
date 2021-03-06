package connectfour

import java.util.*

const val KRUGLYAK = "o"
const val LEPEHA = "*"

var name1 = ""
var name2 = ""

var rowM =6
var colM =7

var board = mutableListOf<MutableList<String>>()
var turnInd =1

var countForDrow =0

var multiple = false
var gameCount = 0
var name1Status = 0
var name2Status = 0
var totalGame: Int = 1


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
       dimensionCheck(readLine()!!.trim())
    }

  private  fun dimensionCheck (string: String) {

      if (string=="") {
          rowM = 6
          colM = 7
          println(Game().optionGame())
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
               println(Game().optionGame())
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

  //  turnInd = turnInd * (-1)
    for (i in 0 .. rowM-1) {
        val listRow = mutableListOf<String>()
     board.add(listRow)
        for (j in 0..colM*2+1){
            if ((j % 2) > 0) board[i].add(" ") else board[i].add("???")
        }
    }
    for(i in 0..colM*2) {
        if (i % 2 > 0) indexList.add("${i/2+1}") else indexList.add(" ")
    }
    board.add(0, indexList)
    for(i in 0..colM*2) {
        when  {
            i== 0 -> bottomList.add("???")
            i== colM*2 -> bottomList.add("???")
            i%2 > 0 -> bottomList.add("???")
            i%2 == 0 -> bottomList.add("???")

        }

    }
    board.add(rowM+1, bottomList)
    for( i in 0 ..rowM+1)
    println(board[i].joinToString(""))
    Game().turn(turnInd)

}

class Game() {
    private var result = "77"
    fun turn(turnInd: Int) {
        val listColTurn = mutableListOf<String>()
        var colTurn = ""
        var disc = ""
        when (turnInd) {
            1 -> {
                print("$name1's turn:\n>")
                disc = KRUGLYAK
            }
            -1 -> {
                print("$name2's turn:\n>")
                disc = LEPEHA
            }
        }
        ////////
        colTurn = readln()!!
        ///////
        if (colTurn == "end") {
            println("Game over!")
            System.exit(0)
        }
        if (turnCheck(colTurn)) {

            for (i in rowM downTo 1) {
                listColTurn.add(board[i][colTurn.toInt() * 2 - 1])
                //  println(listColTurn)

            }
            if (listColTurn.contains(" ")) {
                board[rowM - listColTurn.indexOf(" ")][colTurn.toInt() * 2 - 1] = disc


                for (i in 0..rowM + 1) {
                    println(board[i].joinToString(""))
                }

                winConditionCheck(board)
                //      turn(turnInd * (-1))
            } else {
                // println(listColTurn.joinToString(","))
                println("Column $colTurn is full")
                turn(turnInd)
            }
        } else {
            turn(turnInd)
        }
    }

    fun turnCheck(strCheck: String): Boolean {
        val regex = Regex("\\D")
        val regexD = Regex("\\d+")
        if (!regex.matches(strCheck) && regexD.matches(strCheck)) {
            if (strCheck.toInt() in 1..colM) {
                return true
            } else {
                println("The column number is out of range (1 - $colM)")
                return false
            }
        } else {
            println("Incorrect column number")
            return false
        }

    }

    fun winConditionCheck(board: MutableList<MutableList<String>>) {

        turnInd = turnInd * -1
        countForDrow = 0

        // draw
        for (i in 0..rowM) {
            for (j in 0..colM * 2) {
                if (board[i][j] == " ") {
                    countForDrow = countForDrow + 1
                }

            }


        }


        // vertical win
        for (j in 1..colM * 2 step (2)) {
            for (i in rowM downTo 1) {
                if ((board[i][j] == board[i - 1][j]) && (board[i][j] == board[i - 2][j]) &&
                    (board[i][j] == board[i - 3][j]) && (board[i][j] != " ")
                ) win(turnInd)
            }
        }

        // horizontal win
        for (i in 1..rowM) {
            for (j in colM * 2 + 1 downTo 6 step (2)) {
                if ((board[i][j] == board[i][j - 2]) && (board[i][j] == board[i][j - 4]) &&
                    (board[i][j] == board[i][j - 6]) && (board[i][j] != " ")
                ) win(turnInd)
            }
        }

        // iskosokal win
        for (i in 1..rowM) {
            for (j in 1..colM * 2 - 6 step (2)) {
                if ((board[i][j] == board[i + 1][j + 2]) && (board[i][j] == board[i + 2][j + 4]) &&
                    (board[i][j] == board[i + 3][j + 6]) && (board[i][j] != " ")
                ) win(turnInd)
            }
        }

        // obratniy iskosokal win
        for (i in rowM downTo 1) {
            for (j in colM * 2 - 1 downTo 6 step (2)) {
                if ((board[i][j] == board[i + 1][j - 2]) && (board[i][j] == board[i + 2][j - 4]) &&
                    (board[i][j] == board[i + 3][j - 6]) && (board[i][j] != " ")
                ) win(turnInd)
            }
        }
        if (countForDrow == 6) win(0)

        turn(turnInd)

    }

    fun win(winInd: Int) {

        when (winInd) {
            1 -> {
                gameCount++
                name2Status += 2
                println("Player $name2 won\nScore")
                println("$name1: $name1Status $name2: $name2Status")
                if (totalGame >= gameCount) {
                    println("Game #${gameCount}")
                    board.clear()
                   // println(totalGame)
                    drawBoard()
                }else{
                   println("Game over!")
                    System.exit(0)
                }
            }
            -1 -> {
                gameCount++
                name1Status += 2
                println("Player $name1 won\nScore")
                println("$name1: $name1Status $name2: $name2Status")
                if (totalGame >= gameCount) {
                    println("Game #${gameCount}")
                    board.clear()
                    drawBoard()
                }else{
                    println("Game over!")
                    System.exit(0)
                }
            }
            0 ->{
                gameCount++
                name2Status += 1
                name1Status += 1
                println("It is a draw\nScore")
                println("$name1: $name1Status $name2: $name2Status")
                if (totalGame >= gameCount) {
                    println("Game #${gameCount}")
                    board.clear()
                    drawBoard()
                }else{
                    println("Game over!")
                    System.exit(0)
                }
            }

        }

    }

    fun optionGame(): String {
        val regex = Regex(pattern = "\\d+")
        println(
            """Do you want to play single or multiple games?
For a single game, input 1 or press Enter
Input a number of games:"""
        )
        print(">")
        var str = readLine()!!.trim()
        if (str == ""){
            multiple = false
            println("$name1 VS $name2")
            println("$rowM X $colM board")
            totalGame = 1
            result = "Single game"
        }else {
            if ((!regex.matches(str)) || (str.toInt() == 0)) {
                println("Invalid input")
                optionGame()
            } else {
                if (str.toInt() == 1) {
                    multiple = false
                    println("$name1 VS $name2")
                    println("$rowM X $colM board")
                    totalGame = 1
                    result = "Single game"
                } else {
                    multiple = true
                    gameCount = str.toInt()
                    println("$name1 VS $name2")
                    println("$rowM X $colM board")
                    gameCount = 1
                    totalGame = str.toInt()
                   // println(totalGame)
                    result = "Total ${str.toInt()} games\n" +
                            "Game #$gameCount"
                }

            }
        }
        return result
    }
}






/*


turn(turnInd * (-1))




 */