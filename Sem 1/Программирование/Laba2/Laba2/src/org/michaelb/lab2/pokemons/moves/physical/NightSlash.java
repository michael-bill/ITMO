package org.michaelb.lab2.pokemons.moves.physical;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class NightSlash extends PhysicalMove {

    public NightSlash() {
        super(Type.DARK, 70, 100);
    }

    @Override
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
       if (Math.random() < 0.125) return 2;
       return 1;
    }

    @Override
    protected String describe() {
        return "использовал physical атаку Night Slash.";
    }
}
