package com.gt.scr.imdb.sealed;

//Sealed class does not prevent non-sealed classes
sealed class Super permits Sub1, Sub2 {}

final class Sub1 extends Super {}
non-sealed class Sub2 extends Super {}

class Any1 extends Sub2 {}
class Any2 extends Sub2 {}
