package org.michaelb.lab2.pokemons.moves.special;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class DarkPulse extends SpecialMove {

    public DarkPulse() {
        super(Type.DARK, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().chance(0.2);
        if (effect.success()) {
            Effect.flinch(pokemon);
        }
    }

    @Override
    protected String describe() {
        return "использовал special атаку Dark Pulse.";
    }
}
