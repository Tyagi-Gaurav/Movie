println ("Hello World")

fun max1(a : Int, b : Int) : Int {
    return if (a > b) a else b
}

fun max2(a : Int, b : Int) : Int = if (a > b) a else b

max1(4, 5)
max2(4, 5)

val answer1 = 42
val answer2 : Int = 42

println ("Hello World $answer1")

//Default Visibility: public
class Person(val name: String, //Read-only property
             var isMarried: Boolean)  //Writable field

val person = Person("Bob", true)
println (person.name)
println (person.isMarried) //Property accessed but getter is invoked

enum class Color {
    RED, ORANGE, GREEN
}

fun getMmemonic(color : Color) =
        when (color) {
            Color.RED -> "Rate"
            Color.ORANGE -> "Or"
            Color.GREEN -> "Grate"
            else -> throw Exception("Dirty Color")
        }

println ("Color Mnemonic: ${getMmemonic(Color.RED)}")

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Exprm

fun eval1(e : Expr) : Int {
    if (e is Num) { //Smart Casts
        return e.value
    }
    if (e is Sum) {
        return eval1(e.left) + eval1(e.right)
    }
    throw IllegalArgumentException("Unknown Expression")
}

//Kotlin doesn't distinguish between checked and unchecked exceptions.
fun eval2(e : Expr) : Int =
    when (e) {
        is Num ->
            e.value
        is Sum ->
            eval2(e.left) + eval2(e.right)
        else ->
            throw IllegalArgumentException("Unknown Expression")
    }

eval1(Num(5))
eval1(Sum(Num(1), Num(2)))
eval1(Sum(Num(1), Sum(Num(4),Num(2))))

eval2(Sum(Num(1), Sum(Num(4),Num(2))))

val oneToTen = 1..10 //Ranges in Kotlin are always closed

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 5 == 0 -> "Buzz "
    i % 3 == 0 -> "Fizz "
    else -> "$i "
}

for (i in 1 to 3) {
    println (i)
}

for (i in 1..15) {
    print (fizzBuzz(i))
}

for (i in 15 downTo 1 step 2) {
    print (fizzBuzz(i))
}

val binaryMaps = java.util.TreeMap<Char, String>()

for (c in 'A'..'F') {
    val toBinaryString = Integer.toBinaryString(c.code)
    binaryMaps[c] = toBinaryString
}

for ((letter, binary) in binaryMaps) { //in keyword
    println ("$letter = $binary")
}

fun isLetter(c: Char) = c !in '0'..'9'

println (isLetter('q'))
println (isLetter('9'))
