package connectfour

import java.lang.reflect.Array
import java.util.*

var name1 = ""
var name2 = ""

var rowM =1
var colM =1

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
           }

      }

    }else{
    println("Invalid input")

       dimensionPrint()
    }
    }
  }
}