import java.io.BufferedReader

fun strLenSafe(s: String?) = if (s != null) s.length else 0

println(strLenSafe("a"))
println(strLenSafe(null))

//Safe call operator
//s?.toUpperCase() -> if (s != null) s.toUppercase() else null

fun printAllCaps(s: String?) {
    val toUpperCase = s?.toUpperCase()
    println(toUpperCase)
}

printAllCaps("abcd")
printAllCaps(null)

class Address(
    val streetAddress: String,
    val zipCode: Int,
    val city: String,
    val country: String
)

class Company(
    val name: String,
    val address: Address?
)

class Person(
    val name: String,
    val company: Company?
)

fun Person.countryName() : String =
    this.company?.address?.country ?: "Unknown"

val person = Person("Dimitry", null)
println (person.countryName())

//Elvis operator ?:
fun foo(s : String?) {
    val t : String = s ?: ""
}

fun strLenSafe2(s: String?) = s?.length ?: 0

println(strLenSafe2("a"))
println(strLenSafe2(null))

fun printShippingLabel(person : Person) {
    val address = person.company?.address ?: throw IllegalArgumentException("No address")
    with (address) {
        println(streetAddress)
        println("$zipCode $city $country")
    }
}

val address = Address("Elsstree 47", 8092, "munch", "Gibr")
val jetbrains = Company("JetBrains", address)
val person2 = Person("Dmitry", jetbrains)

printShippingLabel(person2)

//Safe casts: as?
class PersonSafe(val firstName : String, val lastName : String) {
    override fun equals(other: Any?): Boolean {
        //Check type and return false if no match
        val otherPerson = other as? PersonSafe ?: return false

        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int =
        firstName.hashCode() * 37 + lastName.hashCode()
}

//Not-null assertions !!
fun ignoreNulls(s : String?) {
    val sNotNull : String = s!!
    println(sNotNull.length)
}

//let : Makes it easier to deal with passing nullable value as argument
// to a function that expects a non-null value.
fun sendEmailTo(email : String) {//Non-null argument
    println("send email to $email")
}

var email : String? = "ddd@example.com"
//sendEmailTo(email) --> Can't do.
email?.let { email -> sendEmailTo(email) }

email = null
email?.let { email -> sendEmailTo(email) }

//lateinit properties
//Important: You can call an extension function that was declared for
//a nullable receiver without safe access.
fun String?.isNullOrBlank() : Boolean = this == null || this.isBlank()

fun verifyUserInput(input : String?) {
    if (input.isNullOrBlank()) { //No safe call is needed for null here.
        println("Please fill in the required fields")
    }
}

//This works because in Kotlin "this" can be null (esp. for extension functions) whereas in Java
//"this" is always non-null

verifyUserInput(" ")
verifyUserInput(null)

//Inheriting a Java interface
//Kotlin doesn't distinguish between wrapper and primitive types. You
//always use the same type.

//At runtime, kotlin compiles Int to primitive int.
//In case of generics, however, it compiles them to Wrapper types.
//Nullable types, Int?, Boolean?, etc are also wrappers as they need to store nulls.

//Any & Any?
//Any - supertype of all types including the primitive types such as Int

//Void ~ Unit
//Nothing ~ This function never returns

fun readNumbers(reader : BufferedReader) : List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) {
        try {
            val number = line.toInt()
            result.add(number)
        }
        catch(e : NumberFormatException) {
            result.add(null)
        }
    }
    return result
}

//List<Int?> vs List<Int>? vs List<Int?>?
//Every Java collection interface has two representations in
// Kotlin: a read-only one and a mutable one
// Each mutable interface extends the corresponding readonly interface.
//Arrays of Primitive types
//ByteArray, CharArray, IntArray
val fiveZeros = IntArray(5)
val fiveZeroesToo = intArrayOf(0, 0, 0, 0, 0)
