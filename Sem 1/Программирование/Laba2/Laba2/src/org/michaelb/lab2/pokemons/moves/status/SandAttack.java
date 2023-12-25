package org.michaelb.lab2.pokemons.moves.status;

import ru.ifmo.se.pokemon.*;

public class SandAttack extends StatusMove {

    public SandAttack() {
        super(Type.GROUND, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().stat(Stat.ACCURACY, -1);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe() {
        return "использовал status атаку Sand Attack.";
    }
}
