package com.gt.scr.movie.audit;

import java.math.BigDecimal;

public record MovieCreateEvent(String name,
                               int yearProduced,
                               BigDecimal rating) {
}
