package org.michaelb.lab2.pokemons.moves.status;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {

    public Swagger() {
        super(Type.NORMAL, 0, 85);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().stat(Stat.ATTACK, 2));
        Effect.confuse(pokemon);
    }

    @Override
    protected String describe() {
        return "использовал status атаку Swagger.";
    }
}
