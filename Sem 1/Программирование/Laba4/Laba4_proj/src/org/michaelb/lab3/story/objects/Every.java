package org.michaelb.lab3.story.objects;

import org.michaelb.lab3.story.interfaces.Laughable;

public class Every implements Laughable {

    @Override
    public void laugh() {
        System.out.print("Все засмеялись");
    }
}
