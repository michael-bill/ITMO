package org.michaelb.lab2.pokemons.moves.status;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {

    public Confide() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().stat(Stat.SPECIAL_ATTACK, -1);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe() {
        return "использовал status атаку Confide.";
    }
}
