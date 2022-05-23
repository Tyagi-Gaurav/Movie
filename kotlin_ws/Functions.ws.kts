val set = hashSetOf(1, 7, 53)
val list = arrayListOf(1, 7, 53)

val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three", 9.to("nine"))
//to is an infix call. It is same as 1.to("one")

println (list)

//Java would generate overloaded methods, omitting each of the parameters, one by one
@JvmOverloads
fun <T> joinToString(collection : Collection<T>,
                     separator : String = ",",
                     prefix: String = "",
                     postfix : String = "") : String {
    val result = StringBuilder(prefix)

    for((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}

println (joinToString(list, ";"))
println (joinToString(list))

//Extension Functions: A function that is a member of a class, but is
// defined outside of it.

fun String.lastChar() : Char = this.get(length - 1)

println ("Kotlin".lastChar())

//Examplle infix call
infix fun Any.to1(other: Any) = Pair(this, other)
val (number, name) = 1 to1 "one"

println ("Number $number")

fun parsePath(path : String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchRresult = regex.matchEntire(path)
    if (matchRresult != null) {
        val (directory, fileName, extension) = matchRresult.destructured
        println ("Dir: $directory, name: $fileName, extension: $extension")
    }
}

parsePath("/Users/yole/kotlin-book/chapter.adoc")