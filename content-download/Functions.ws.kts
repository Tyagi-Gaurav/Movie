val set = hashSetOf(1, 7, 53)
val list = arrayListOf(1, 7, 53)
val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

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
