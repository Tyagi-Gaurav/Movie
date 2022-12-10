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
  * Obey general contract when overriding equals
    * When is it appropriate to override equals?
      * When class has a notion of logical equality that differs from mere object identity
      * Superclass has not already overridden equals
    * One kind of value class that does not require the equals method to be overridden is a class that uses instance control to
      ensure that at most one object exists with each value .ie. Enum class
    * Equals method implements the Equivalence relation