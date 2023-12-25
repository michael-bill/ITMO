package org.michaelb.lab3.story.enums;

public enum SeeingType {

    VISIBLE_AT_A_GLANCE ("виден как на ладони"),
    SEEM_VERY_TINY ("казались совсем крошечными"),
    COULD_NOT_SEE_AT_ALL ("совсем нельзя было разглядеть"),
    SEEN_FAR_BEHIND ("виднелся далеко позади");

    private final String title;

    private SeeingType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
