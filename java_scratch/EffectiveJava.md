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

* Classes and Interfaces
  * [I-15] Minimize the accessibility of classes and members
    * Information hiding or encapsulation
    * Make each class or member as inaccessible as possible
      * private/package-private/protected/public
    * The need for protected members should be relatively rare.
    * It is acceptable to make a private member of a public class package-private in order to test it, but it is not acceptable to raise the accessibility any higher.
    * Classes with public mutable fields are not generally thread-safe.
    * It is wrong for a class to have a public static final array field, or an accessor that returns such a field.
      * You could either return a public Unmodifiable list, or
      * Return a copy of the private array
    * Ensure that objects referenced by public static final fields are immutable.
  * [I-16] In public classes, use accessor methods, not public fields
  * [I-17] Minimize mutability
    * Make immutable objects. They are
      * Threadsafe; No-Synchronization needed
      * Simple
      * Can be shared freely
      * Provide failure atomicity
        * Their state never changes, so there is no possibility of a temporary inconsistency.
      * [**** IMP ****] The major disadvantage of immutable classes is that they require a separate object for each distinct value
        * If a multistep operation requires creation of a lot of new immutable objects, then it would un-necessarily introduce a lot of objects.
        * Instead, if we know which complex operations clients will perform then we can use primitives instead.
        * This kind of capability can be introduced either as a package-private or a public companion class.
        * Eg. StringBuilder for String introduces a public companion class.
      * Some immutable classes have one or more nonfinal fields in which they cache the results of expensive computations the first time they are needed. 
        * If the same value is requested again, the cached value is returned, saving the cost of recalculation. 
        * This trick works precisely because the object is immutable, which guarantees that the computation would yield the same result if it were repeated.
      * Classes should be immutable unless there’s a very good reason to make them mutable
      * Constructors should create fully initialized objects with all of their invariants established
  * [I-18] Favour composition over inheritance
    * Inheritance violates encapsulation
      * The superclass’s implementation may change from release to release, and if it does, the subclass may break, even though its code has not been touched.
      * One caveat is that wrapper classes are not suited for use in callback frameworks, wherein objects pass self- references to other objects for 
      subsequent invocations (“callbacks”). Because a wrapped object doesn’t know of its wrapper, it passes a reference to itself (this) and callbacks 
      elude the wrapper. This is known as the SELF problem.
      * Inheritance propagates any flaws in the superclass’s API, while composition lets you design a new API that hides these flaws.
  * [I-19] Design & Document for inheritance or else prohibit it
    *  A class may have to provide hooks into its internal workings in the form of judiciously chosen protected methods.
    * The only way to test a class designed for inheritance is to write subclasses.
  * [I-20] Prefer interfaces to Abstract classes
    * If you provide default methods, be sure to document them for inheritance using the @implSpec Javadoc tag
    * You can, however, combine the advantages of interfaces and abstract classes by providing an abstract skeletal implementation class to go with 
      an interface. 
      * The interface defines the type, perhaps providing some default methods, while the skeletal implementation class implements the remaining 
      non-primitive interface methods atop the primitive interface methods. 
      * Extending a skeletal implementation takes most of the work out of implementing an interface.
      * Writing a skeletal implementation is a relatively simple, if somewhat tedious, process. First, study the interface and decide which methods 
      are the primitives in terms of which the others can be implemented. These primitives will be the abstract methods in your skeletal implementation.
      * A simple implementation is like a skeletal implementation in that it implements an interface and is designed for inheritance, but it differs in 
      that it isn’t abstract: it is the simplest possible working implementation. You can use it as it stands or subclass it as circumstances warrant.
  * [I-21] Design interface for posterity
    * Using default methods to add new methods to existing interfaces should be avoided unless the need is critical, in which case 
      you should think long and hard about whether an existing interface implementation might be broken by your default method implementation.
    * Default methods are, however, extremely useful for providing standard method implementations when an interface is created, to ease the task of 
      implementing the interface.
  * [I-22] Use interfaces to only define types
  * [I-23] Prefer class hierarchies to tagged classes
    * Tagged classes are verbose, error-prone, and inefficient
  * [I-24] Favor static member classes over nonstatic
    * Four kinds of Nested classes
      * Static member classes
        * If you omit this modifier, each instance will have a hidden extraneous reference to its enclosing instance.
        * Storing this reference takes time and space.
        * Eg. Many Map implementations have an internal Entry object for each key-value pair in the map.
        * While each entry is associated with a map, the methods on an entry (getKey, getValue, and setValue) do not need access to the map.
      * Nonstatic member classes
        * Each instance of a nonstatic member class is implicitly associated with an enclosing instance of its containing class.
        * If an instance of a nested class can exist in isolation from an instance of its enclosing class, then the nested class must 
        be a static member class. 
        * It is impossible to create an instance of a nonstatic member class without an enclosing instance.
        * One common use of a nonstatic member class is to define an Adapter that allows an instance of the outer class to be viewed as an instance of 
        some unrelated class
        *  For example, implementations of the Map interface typi- cally use nonstatic member classes to implement their collection views, which are 
        returned by Map’s keySet, entrySet, and values methods. 
        * Similarly, implementations of the collection interfaces, such as Set and List, typically use nonstatic member classes to implement their 
        iterators.
      * Anonymous classes
      * Local classes
        *  A local class can be declared practically anywhere a local variable can be declared and obeys the same scoping rules
    * [I-25] Limit source files to single top-level class
    
* Generics
  * [I-26] Don't use raw types
    * Raw types behave as if all the generic type information were erased from the type declaration.
    * If you want to use a generic type, but you don’t know or care what the actual type parameter is, you can use a 
    question mark (Unbounded wildcard) instead.
      * Wildcard type is safe and the raw type isn’t.
        * You CAN put any element into a raw type Collection
        * You CAN'T put any element (other than null) into a Collection<?>
    * `List.class`, `String[].class`, and `int.class` are all legal, but `List<String>.class` and `List<?>.class` are not.
  * [I-27] Eliminate unchecked warnings
    * If you can’t eliminate a warning, but you can prove that the code that provoked the warning is typesafe, then (and only then) suppress 
    the warning with an `@SuppressWarnings("unchecked")` annotation.
    * Every time you use a `@SuppressWarnings("unchecked")` annotation, add a comment saying why it is safe to do so.
  * [I-28] Prefer list to arrays
    * Arrays are covariant which means that if Sub is a subtype of super, then Sub[] is a subtype of array type Super[]
    * Generics are invariant
    * Arrays fail at runtime while generics fail at compile time.
    * Arrays are reifed which means they enforce their element type at compile time.
    * It is illegal to create an array of a generic type, a parameterized type, or a type parameter.
      * This is illegal because it's not type safe.
    * Types such as E, List<E>, and List<String> are technically known as non-reifiable types.
    * non-reifiable type is one whose runtime representation contains less information than its compile-time representation.
    * Because of erasure, the only parameterized types that are reifiable are unbounded wildcard types such as List<?> and Map<?,?>
      * It is legal, though rarely useful,  to create arrays of unbounded wildcard types.
  * [I-29] Favour generic types
  * [I-30] Favour generic methods
    * Generic Singleton Factory
  * [I-31] Use bounded wildcards to increase API flexibility
    * ? super E
    * ? extends E
    * For maximum flexibility, use wildcard types on input parameters that represent producers or consumers
      * (PECS) Producers-extends Consumer-super
    * Do not use bounded wildcard types as return types
    * If the user of a class has to think about wildcard types, there is probably something wrong with its API.
    * if a type parameter appears only once in a method declaration, replace it with a wildcard.
    * What is unbounded wildcard?
    * If it’s an unbounded type parameter, replace it with an unbounded wildcard.
    * if it’s a bounded type parameter, replace it with a bounded wildcard.
  * [I-32] Combine generics and varargs judiciously
    * It is a leaky abstraction.
    * If a method declares its varargs parameter to be of a non-reifiable type, the compiler generates a warning on the declaration.
    * Heap pollution occurs when a variable of a parameterized type refers to an object that is not of that type.
    * How do we know when to put @SafeArgs?
    * If the method doesn’t store anything into the array (which would overwrite the parameters) and doesn’t allow a reference 
    to the array to escape (which would enable untrusted code to access the array), then it’s safe.
    * It is unsafe to give another method access to a generic varargs parameter array.
  * [I-33] Consider typesafe heterogeneous containers
    * When a class literal is passed among methods to communicate both compile-time and runtime type information, it is called a type token.

* Enums & Annotations 
  * [I-34] Use enums instead of `int` constants
  * [I-35] Use instance fields instead of ordinals
    * Never derive a value associated with an enum from its ordinal; store it in an instance field instead
  * [I-36] Use `EnumSet` instead of bit fields
  * [I-37] Use `EnumMap` instead of ordinal indexing
    * There is a very fast Map implementation designed for use with enum keys, known as `java.util.EnumMap`.
    * The reason that EnumMap is comparable in speed to an ordinal-indexed array is that EnumMap uses such an array internally, but it 
    hides this implementation detail from the programmer, combining the richness and type safety of a Map with the speed of an array.
    * If the relationship you are representing is multidimensional, use EnumMap<..., EnumMap<...>>. 
  * [I-38] Emulate extensible enums with interfaces
    * Extensibility of enums is a bad idea
    * Enum types can implement interfaces
    * In summary, while you cannot write an extensible enum type, you can emulate it by writing an interface to accompany a basic enum type 
    that implements the interface.
  * [I-39] Prefer annotations to naming patterns
    * As of Java 8, there is another way to do multivalued annotations. 
    * Instead of declaring an annotation type with an array parameter, you can annotate the declaration of an annotation with the `@Repeatable` 
    meta-annotation, to indicate that the annotation may be applied repeatedly to a single element.
    * But `isAnnotationPresent` makes it explicit that repeated annotations are not of the annotation type, but of the containing annotation type.
    * There is simply no reason to use naming patterns when you can use annotations instead.
  * [I-40] Consistently use the Override annotation
  * [I-41] Use marker interfaces to define types
    * Marker interfaces have two advantages over marker annotations
      * Marker interfaces define a type that is implemented by instances of the marked class
      * Marker annotations do not
      * The existence of a marker interface type allows you to catch errors at compile time that you couldn’t catch until runtime if you 
      used a marker annotation.
      * Another advantage of marker interfaces over marker annotations is that they can be targeted more precisely.
    * The chief advantage of marker annotations over marker interfaces is that they are part of the larger annotation facility.
    * Clearly you must use an annotation if the marker applies to any program element other than a class or interface, because only classes and 
    interfaces can be made to implement or extend an interface.
    * If you find yourself writing a marker annotation type whose target is `ElementType.TYPE`, take the time to figure out whether it really should 
    be an annotation type or whether a marker interface would be more appropriate

* Lambdas and Streams
  * [I-42] Prefer lambdas to anonymous classes
    * Lambdas share with anonymous classes the property that you can’t reliably serialize and deserialize them across implementations.
  * [I-43] Prefer method references to lambdas
    * Where method references are shorter and clearer, use them; where they aren’t, stick with lambdas.
  * [I-44] Favour the use of standard functional interfaces
    * If one of the standard functional interfaces does the job, you should generally use it in preference to a purpose-built functional interface.
    * Don’t be tempted to use basic functional interfaces with boxed primitives instead of primitive functional interfaces
    * Always annotate your functional interfaces with the @FunctionalInterface annotation
      * Prevents maintainers from accidentally adding abstract methods to the interface as it evolves
      * Interface won’t compile unless it has exactly one abstract method
  * [I-45] Use Streams Judiciously
    * A stream pipeline consists of a source stream followed by zero or more intermediate operations and one terminal operation.
    * Stream pipelines are evaluated lazily: evaluation doesn’t start until the terminal operation is invoked.
    * A stream pipeline without a terminal operation is a silent no-op, so don’t forget to include one.
    * Overusing streams makes programs hard to read and maintain.
  * [I-46] Prefer side-effect free functions in streams
    * A pure function is one whose output depends only on its input
    * A `forEach` function that does nothing more than present the result of a computation performed by a stream is a bad smell.
    * A lambda that mutates code is also a bad smell.
    * The `forEach` should only be used to report the result of a stream operation and not perform the computation.
  * [I-47] Prefer collection to Stream as a return type
    * If you’re writing a method that returns a sequence of objects and you know that it will only be used in a stream pipeline, then of course 
    you should feel free to return a stream.
    * But if you’re writing a public API that returns a sequence, you should provide for users who want to write stream pipelines as well as 
    those who want to write for-each statements.
  * [I-48] Use caution when making streams parallel
    * Parallelizing a pipeline is unlikely to increase its performance if the source is from Stream.iterate, or the intermediate operation limit is used.
    * Do not parallelize stream pipelines indiscriminately.
    * Performance gains from parallelism are best on streams over ArrayList, HashMap, HashSet, and ConcurrentHashMap 
    instances; arrays; int ranges; and long ranges because these data structures can accurately be split into subranges of any desired sizes, which makes
    it easy to divide work among parallel threads.
    * The operations performed by Stream’s collect method, which are known as mutable reductions, are not good candidates for parallelism because 
    the overhead of combining collections is costly.
    * If a significant amount of work is done in the terminal operation compared to the overall work of the pipeline and that operation is inherently 
    sequential, then parallelizing the pipeline will have limited effectiveness.
    * 
    