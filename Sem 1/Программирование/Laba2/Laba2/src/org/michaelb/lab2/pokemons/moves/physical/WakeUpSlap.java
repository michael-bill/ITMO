package org.michaelb.lab2.pokemons.moves.physical;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public class WakeUpSlap extends PhysicalMove {

    public WakeUpSlap() {
        super(Type.FIGHTING, 70, 100);
    }

    @Override
    protected double calcBaseDamage(Pokemon att, Pokemon def) {
        double defaultValue = super.calcBaseDamage(att, def);
        if (def.getCondition().equals(Status.SLEEP)) return defaultValue * 2;
        else return defaultValue;
    }

    @Override
    protected String describe() {
        return "использовал physical атаку Wake-Up Slap.";
    }
}
