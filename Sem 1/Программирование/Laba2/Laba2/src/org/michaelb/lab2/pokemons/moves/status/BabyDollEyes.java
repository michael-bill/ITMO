package org.michaelb.lab2.pokemons.moves.status;

import ru.ifmo.se.pokemon.*;

public class BabyDollEyes extends StatusMove {

    public BabyDollEyes() {
        super(Type.FAIRY, 0, 100, 1, 1);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().stat(Stat.ATTACK, -1);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe() {
        return "использовал status атаку Baby-Doll Eyes.";
    }
}
