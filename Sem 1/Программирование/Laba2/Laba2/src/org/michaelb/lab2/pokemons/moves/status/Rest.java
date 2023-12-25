package org.michaelb.lab2.pokemons.moves.status;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {

    public Rest() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
       Effect effect = new Effect().turns(2).condition(Status.SLEEP);
       pokemon.restore();
       pokemon.addEffect(effect);
    }

    @Override
    protected String describe() {
        return "использовал status атаку Rest.";
    }
}
