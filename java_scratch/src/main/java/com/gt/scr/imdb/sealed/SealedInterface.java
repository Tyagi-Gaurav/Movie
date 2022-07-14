package com.gt.scr.imdb.sealed;

public sealed interface SealedInterface permits Imp1, Imp2 {
}

final class Imp1 implements SealedInterface {}
final class Imp2 implements SealedInterface {}
