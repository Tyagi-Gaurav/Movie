data class Person(val name: String, val age : Int)

val people = arrayListOf(Person("Alice", 29), Person("Bob", 31))
val getAge = Person::age

println (people.maxByOrNull { it.age })

//println ("Age is ${getAge(Person("Alice", 29))}")

val sum = {x : Int, y : Int ->
    println("Computing sum of $x & $y")
    x + y
}
println (sum(1,2))

val names = people.joinToString(separator = " ", transform = { p : Person -> p.name })
println (names)

fun salute() = println("Salute!")
::salute

//Postpone the action of creating an instance of a class using constructor reference
val createPerson = ::Person
val p = createPerson("Alice", 40)
println (p)

//Extension functions
fun Person.isAdult() = age >= 21
val predicate = { person: Person -> person.isAdult() }

val list = arrayListOf(1, 2, 3, 4)
println  (list.filter { it % 2 == 0})

//Elements in sequence are evaluated lazily just like streams.
people.asSequence()
    .map(Person::name)
    .filter{it.startsWith("A")}
    .toList() //Terminal operation. The sequence is executed only when this is invoked

val naturalNumbers = generateSequence(0) { it  + 1 }
val naturalNosUpto100 = naturalNumbers.takeWhile {it  <= 100}

println (naturalNosUpto100.sum())

//If lambda captures variables from surrounding scope, its no longer
//possible to reuse the same instance for every invocation.

//SAM Constructor
//A Compiler generated function that lets you perform an explicit conversion
//of a lambda into an instance of a functional interface
fun createAllDoneRunnable() : Runnable {
    return Runnable { printlln  ("All done!")}
}

createAllDoneRunnable().run()

//Using with() to build the alphabet.
fun alphabet_with() : String {
    val stringBuilder = stringBuilder()
    return with(stringBuilder) {
        for (letter in 'A'..'Z') {
            this.append(letter)
        }
        append("\nNow I know the alphabet!")
        this.toString()
    }
}

fun alphabet_apply() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()

