package org.michaelb.lab2;

import org.michaelb.lab2.pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Lab2 {
    public static void main(String[] args) {
        Battle battle = new Battle();

        battle.addAlly(new Furfrou());
        battle.addAlly(new Kabuto());
        battle.addAlly(new Kabutops());

        battle.addFoe(new Poliwag());
        battle.addFoe(new Poliwhirl());
        battle.addFoe(new Poliwrath());

        battle.go();
    }
}
