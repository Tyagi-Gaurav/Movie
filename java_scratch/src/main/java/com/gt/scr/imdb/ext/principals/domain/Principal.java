package com.gt.scr.imdb.ext.principals.domain;

import com.gt.scr.imdb.ext.principals.PrincipalRow;

import java.util.List;

public record Principal(String titleId,
                        List<SubPrincipals> subPrincipals) {

    public record SubPrincipals(int ordering,
                                String peopleId,
                                String category,
                                String job,
                                String characters) {

        public static Principal.SubPrincipals from(PrincipalRow principalConsumer) {
            return new Principal.SubPrincipals(principalConsumer.ordering(),
                    principalConsumer.nameId(),
                    principalConsumer.category(),
                    principalConsumer.job(),
                    principalConsumer.characters());
        }
    }
}
