# Effective Java

* Creating and destroying objects
  * Consider static factory methods instead of constructors
  * Conside builder when faced with many constructor parameters (4 or more)
  * Enforce the singleton property with a private constructor or enum type
  * Enforce non-instantiability with a private constructor
  * Prefer dependency injection to hardwiring resources
  * Avoid creating unnecessary objects
    * Prefer primitives to boxed primitives, and watch out for unintentional autoboxing
    * Autoboxing blurs but does not erase the distinction between primitive and boxed primitive types
    * Creating additional objects to enhance the clarity, simplicity, or power of a program is generally a good thing.
    * Conversely, avoiding object creation by maintaining your own object pool is a bad idea unless the objects in the pool are extremely heavyweight.
  * Eliminate obsolete object references
    * Common Sources are caches, callbacks and listeners
  * Use try-with-resources method
    
* Methods common to all objects
  * [I-10] Obey general contract when overriding equals
    * When is it appropriate to override equals?
      * When class has a notion of logical equality that differs from mere object identity
      * Superclass has not already overridden equals
    * One kind of value class that does not require the equals method to be overridden is a class that uses instance control to
      ensure that at most one object exists with each value .ie. Enum class
    * Equals method implements the Equivalence relation
    * Case of subclass that adds a new value component to its superclass ie. subclass adds a piece if information that affects equals comparisons
      * There is no way to extend an instantiable class and add a value component while preserving the equals contract.
        * There is a fine workaround. -> "Favour composition over inheritance"
        * The "equals" implementation of Timestamp does violate symmetry and can cause erratic behavior if Timestamp and Date objects are 
        used in same collection or are otherwise intermixed.
      * You can add a value component to a subclass of an abstract class without violating the equals contract.
    * Making a high quality equals method
      * Use the == operator to check if the argument is a reference to this object
      * Use the instanceof operator to check if the argument has the correct type
      * Cast the argument to correct type
      * For each "significant" field in the class, check if that field of the argument matches the corresponding field of this object
  * [I-11] Always override hashcode when you override equals
    * Equal objects must have equal hashcodes
  * [I-12] Always override toString
    * Helps in diagnostic messages
    * Recommended to implement for value classes. Helps in transforming strings back into objects.
  * [I-13] Override Clone Judiciously
    * What does Cloneable do, given that it contains no methods?
      * It determines the behavior of Objects protected clone implementation: 
        * If a class implements Cloneable, Object’s clone method returns a field-by-field copy of the object; otherwise it throws `CloneNotSupportedException`
    * Immutable classes should never provide clone method as it encourages wasteful copying.
    * Public clone methods should omit the `throws` clause as its easier to use classes that don't throw ClassCastException
    * Remember that objects clone method is not synchronized, so even if its implementation is otherwise satisfactory, you may have to write a synchronized
    clone method that returns a `super.clone()`
    * All classes that implement Cloneable should override clone with a public method whose return type is the class itself.
      * This method should first call `super.clone()`, then fix any fields that need fixing.
    * If you extend a class that already implements Cloneable, you have little choice but to implement a well-behaved clone method
    * A better approach to object copying is to provide a copy constructor or copy factory
      * This way we could provide copy constructors or even conversion constructors to convert across subclasses. Eg Convert from HashSet to TreeSet
  * [I-14] Consider Implementing Comparable
    * By implementing Comparable, you allow your class to interoperate with all of the many generic algorithms and collection implementations 
    that depend on this interface.
      * The general contract of `compareTo` is similar to `equals` method. ie. follows an equivalence relation
        * Symmetric
        * Reflexive
        * Transitive
    