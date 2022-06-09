import java.time.*
import java.util.Comparator
import java.util.stream.IntStream as IntStream1
import java.util.stream.Collectors

object Hamming {

    fun compute(leftStrand: String, rightStrand: String): Int {
        if (leftStrand.length != rightStrand.length) {
            throw IllegalArgumentException("left and right strands must be of equal length")
        }
        var count = 0
        for (i in leftStrand.indices) {
            if (leftStrand[i] != rightStrand[i])
                ++count
        }
        return count
    }
}

println(Hamming.compute("ABCD", "ABCE"))

class Gigasecond(val gigaSend: LocalDateTime) {
    constructor(gigaSend: LocalDate) : this(gigaSend.atStartOfDay())

    val date: LocalDateTime = gigaSend.plusSeconds(1_000_000_000)
}

val gigaSecond2 = Gigasecond(LocalDate.of(2011, Month.APRIL, 25))
println(gigaSecond2.date)

val one_value = setOf('A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T')

println("CABBAGE".asSequence()
    .map { it ->
        when {
            one_value.contains(it) -> 1
            else -> 2
        }
    }
    .sum())

object ScrabbleScore {

    fun scoreLetter(c: Char): Int {
        val one_value = setOf('A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T')
        val two_value = setOf('D', 'G')
        val three_value = setOf('B', 'C', 'M', 'P')
        val four_value = setOf('F', 'H', 'V', 'W', 'Y')
        val five_value = setOf('K')
        val eight_value = setOf('J', 'X')

        val upperCase = c.uppercaseChar();

        return when {
            one_value.contains(upperCase) -> 1
            two_value.contains(upperCase) -> 2
            three_value.contains(upperCase) -> 3
            four_value.contains(upperCase) -> 4
            five_value.contains(upperCase) -> 5
            eight_value.contains(upperCase) -> 8
            else -> 10
        }
    }

    fun scoreWord(word: String): Int {
        return word.asSequence()
            .map { scoreLetter(it) }
            .sum()
    }
}

println(ScrabbleScore.scoreWord("CABBAGE"))

class Matrix(private val matrixAsString: String) {

    val internalList = matrixAsString.split("\n")
        .map {
            it.split(" ")
                .filter { !it.equals("") }
                .map { it.toInt() }
        }

    fun column(colNr: Int): List<Int> {
        return internalList.map { it.get(colNr - 1) }
    }

    fun row(rowNr: Int): List<Int> {
        return internalList.get(rowNr - 1)
    }
}

val matrixAsString1 = """
            1 2 3
            4 5 6
            7 8 9
            8 7 6
            """.trimIndent()

val matrixAsString2 = """
            89 1903   3
            18    3   1
             9    4 800
            """.trimIndent()

println(Matrix(matrixAsString1).column(1))
println(Matrix(matrixAsString1).row(2))

println(Matrix(matrixAsString2).column(1))
println(Matrix(matrixAsString2).row(2))

class Squares(val x: Int) {
    fun sumOfSquares() = 1.rangeTo(x).map { it * it }.sum()

    fun squareOfSum(): Int {
        var sum = 1.rangeTo(x).sum()
        return sum * sum
    }

    fun difference() = squareOfSum() - sumOfSquares()
}

println(Squares(5).sumOfSquares())
println(Squares(5).squareOfSum())
println(Squares(5).difference())

enum class Signal {
    WINK, DOUBLE_BLINK, CLOSE_YOUR_EYES, JUMP
}

object HandshakeCalculator {
    fun calculateHandshake(number: Int): List<Signal> {
        return with(arrayListOf<Signal>()) {
            if (number and 1 == 1) add(Signal.WINK)
            if (number and 2 == 2) add(Signal.DOUBLE_BLINK)
            if (number and 4 == 4) add(Signal.CLOSE_YOUR_EYES)
            if (number and 8 == 8) add(Signal.JUMP)
            if (number and 16 == 16) reverse()
            this
        }
    }
}

println(HandshakeCalculator.calculateHandshake(24))

//out is called variance annotation. Triangle is producer of T's
//not consumer of T's. Below it provides declaration site variance.
//T is a covariant type parameter. C is producer of T's and not
//consumer of T's.
class Triangle<out T : Number>(val a: T, val b: T, val c: T) {

    init {
        fun verifiesInequality(a: T, b: T, c: T) = a.toDouble() > b.toDouble() + c.toDouble()

        if (a == 0 || b == 0 || c == 0
            || verifiesInequality(a, b, c)
            || verifiesInequality(b, a, c)
            || verifiesInequality(c, a, b)
        ) {
            throw IllegalArgumentException()
        }
    }

    val isEquilateral: Boolean = a == b && b == c
    val isIsosceles: Boolean = isEquilateral || (a == b && b != c) || (a == c && c != b) || (b == c && c != a)
    val isScalene: Boolean = a != b && b != c && c != a
}
//val t = Triangle(0, 0, 0)
//val t1 = Triangle(7, 1, 1)

//Saddle points
data class MatrixCoordinate(val row: Int, val col: Int)

class MatrixInt(val rawMatrix: List<List<Int>>) {
    val rows = rawMatrix.size
    val cols = if (rawMatrix.isEmpty())  0 else rawMatrix[0].size

    val saddlePoints = calculate()

    fun calculate() : Set<MatrixCoordinate> {
        val result = mutableSetOf<MatrixCoordinate>()

        //Calculate max for each row
        val maxRowSet = rawMatrix.map { it -> it.maxOrNull() }

        //println(maxRowSet)

        val minColSet = (0..cols - 1)
            .map { it -> rawMatrix.map { l -> l[it] }}
            .map { it -> it.minOrNull()}

        //println(minColSet)

        //Return intersection of two sets
        for ((rowIndex, row) in rawMatrix.withIndex()) {
            for ((colIndex, _) in row.withIndex()) {
                val cell = rawMatrix[rowIndex][colIndex]
                if (cell == maxRowSet[rowIndex] && cell == minColSet[colIndex]) {
                    result.add(MatrixCoordinate(rowIndex +1, colIndex + 1))
                }
            }
        }
        return result
    }
}

val m = MatrixInt(
    listOf(
        listOf(9, 8, 7),
        listOf(5, 3, 2),
        listOf(6, 6, 7)
    )
)

m.saddlePoints()