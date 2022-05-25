import java.time.*

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

println ("CABBAGE".asSequence()
    .map {it -> when {
        one_value.contains(it) -> 1
        else -> 2
    }}
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
