package com.gt.scr.imdb.sealed;

/*
In the code below, there can be no subclasses other than D1 and D2.
 */
public sealed class SealedClass permits D1, D2 {
}

//NOTE: If all subclasses are defined in the same file, you don’t need the permits clause


/*
Subclasses of Sealed should use one of the following.
- Sealed
- non-Sealed
- final
 */


final class D1 extends SealedClass {
}

final class D2 extends SealedClass {
}

//Illegal -> UnComment below to see compilation error
//final class D3 extends SealedClass
