//Declare a  class as a data class to generate several standard methods
//for the class
interface Clickable {
    fun click()

    //Default  implementation
    fun showOff() = println("I am clickable!")
}

interface Focusable {
    fun showOff() = println("I am focusable!")
}

//All kotlin classes are final by default
class Button : Clickable, Focusable {
    override fun click() = println ("I was clicked")

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

val b = Button()
b.click()
b.showOff()

open class RichButton : Clickable {
    fun disable() {} // Function is final. Cannot override it.

    open fun animate() {} //This function is open. Can be overridden in subclasses

    override fun click() {} //Overrides an open function and is open as well
}

abstract class Animated {
    abstract fun animate()

    open fun stopAnimating() {}

    fun animateTwice() {} //Aren't marked as open
}

//Extension functions don't get access to private and protected
//Visibility modifiers in Kotlin - public, private, protected, internal
internal open class  TalkativeButton  : Focusable {
    private fun yell() = println ("Hey!")
    protected fun whisper() = println ("Lets Talk!")
}

//internal gets translated to public in Java.
//Potentially, you could access internal class from Java and not from Kotlin.

//Kotlin inner classes do not have access to outer class instance. In Java
//serializing an inner class, without serializing outer class is not  possible,
//because inner class contains a reference to outer class. Declaring the inner
//class as static removes that reference.

interface State : java.io.Serializable

interface View {
    fun getCurrentState() : State
    fun restoreState(state : State) {}
}

class SerButton : View {
    override fun getCurrentState(): State = ButtonState()

    override fun restoreState(state: State) {
        super.restoreState(state)
    }

    class ButtonState : State {
        //This class is same as a static nested class in Java
    }
}

//Declaring the class as inner class ensures that it contains a reference
// to outer class.
class Outer {
    inner class Inner  {
        fun getOuterReference() : Outer = this@Outer
    }
}

sealed class ExprV2  { //Mark base class as sealed. Implies class is open.
    //All Direct subclasses must be nested in Superclass
    class Num(val value : Int)  : ExprV2()
    class Sum(val left : ExprV2, val right : ExprV2) : ExprV2()
}

fun eval(e : ExprV2) : Int =
    when (e) {
        is ExprV2.Num -> e.value
        is ExprV2.Sum -> eval(e.left) + eval(e.right)
        else -> throw IllegalArgumentException("Unknown Expression")
    }


//Class with private Constructor
class Secretive private constructor() {}

interface User {
    val nickname : String
//Classes implementing the User interface should provide a way to  obtain
//a value for nickname
}

class PrivateUser(override val nickname: String) : User

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore("@")
}

class LengthCounter {
    var counter : Int = 0
        private set //Private Setter for counter.

    fun addWord(word : String) {
        counter += word.length
    }
}

class Client(val name : String, val postalCode: Int) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client)
            return false

        //In kotlin, == would call .equals for object references.
        return other.name == name && other.postalCode == postalCode
    }

    override fun hashCode(): Int = name.hashCode() * 31 + postalCode
}

data class ClientV2(val name : String, val postalCode : Int)
//The data class overrides toString, equals and hashCode for us.

val bob = ClientV2("Bob", 973293)
println(bob.copy(postalCode = 38255))

//Class delegation
class DelegatingCollection<T>(innerList : Collection<T> = ArrayList<T>())
    : Collection<T> by innerList {
}

class CountingSet<T>(val innerSet: MutableCollection<T> = HashSet<T>())
    : MutableCollection<T> by innerSet { //Delegates MutableCollection implementation  to innerSet

    var objectsAdded = 0

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerSet.addAll(elements)
    }
}

val cset = CountingSet<Int>()
cset.addAll(listOf(1, 2, 3))

println("${cset.objectsAdded} objects were added, ${cset.size} remain")

data class Person(val name : String)

//Object declarations: Singletons made easy
object Payroll {
    val allEmployees = arrayListOf<Person>()

    fun calculateSalary() {
        for (person in allEmployees) {

        }
    }
}

Payroll.calculateSalary()

class A {
    companion object { //static method invocation
        fun bar() {
            println ("Companion object called")
        }
    }
}

A.bar()

//Using object as a factory
class UserV2 private constructor(val nickname : String) {
    companion object {
        fun newSubscribingUser(email : String) =
            UserV2(email.substringBefore('@'))

        fun newFacebookUser(accountId : Int) =
            UserV2("From Facebook: ${accountId}")
    }
}

val subscribingUser = UserV2.newSubscribingUser("a@n.com")
val facebookUserV2 = UserV2.newFacebookUser(110)

println (facebookUserV2.nickname)
