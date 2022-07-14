package com.gt.scr.imdb.sealed;

//Records are implicitly final

sealed interface SealedRecord permits CLevel, Programmer{}

record CLevel() implements SealedRecord {}
record Programmer() implements SealedRecord {}
