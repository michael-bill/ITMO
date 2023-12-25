package org.michaelb.lab2.pokemons.moves.status;

import ru.ifmo.se.pokemon.*;

public class RockPolish extends StatusMove {

    public RockPolish() {
        super(Type.ROCK, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().stat(Stat.SPEED, -2);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe() {
        return "использовал status атаку Rock Polish";
    }
}
