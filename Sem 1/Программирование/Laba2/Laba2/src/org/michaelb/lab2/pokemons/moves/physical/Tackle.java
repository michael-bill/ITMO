package org.michaelb.lab2.pokemons.moves.physical;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Tackle extends PhysicalMove {

    public Tackle() {
        super(Type.NORMAL, 40, 100);
    }

    @Override
    protected String describe() {
        return "использовал physical атаку Tackle.";
    }
}
