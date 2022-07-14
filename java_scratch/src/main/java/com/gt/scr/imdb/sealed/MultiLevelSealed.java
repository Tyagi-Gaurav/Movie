package com.gt.scr.imdb.sealed;

sealed class Bottom permits Level1 {}

sealed class Level1 extends Bottom permits Level2 {}
sealed class Level2 extends Level1 permits Level3 {}

final class Level3 extends Level2 {}