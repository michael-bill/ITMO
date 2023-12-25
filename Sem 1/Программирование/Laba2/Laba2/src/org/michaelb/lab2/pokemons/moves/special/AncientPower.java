package org.michaelb.lab2.pokemons.moves.special;

import ru.ifmo.se.pokemon.*;

public class AncientPower extends SpecialMove {

    public AncientPower() {
        super(Type.ROCK, 60, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        if (Math.random() <= 0.1) {
            Effect effect = new Effect();
            effect.stat(Stat.ATTACK, 6);
            effect.stat(Stat.DEFENSE, 6);
            effect.stat(Stat.SPECIAL_ATTACK, 6);
            effect.stat(Stat.SPECIAL_DEFENSE, 6);
            effect.stat(Stat.SPEED, 6);
            pokemon.addEffect(effect);
        }
    }

    @Override
    protected String describe() {
        return "использовал special атаку Ancient Power.";
    }
}
