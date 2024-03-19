package com.example.attendxbackendv2.interfaces;

public interface SelectableInterface {
    /**
     * This interface provides general approach for getting Entity identifier values
     * that'll be listed on AttendX-Hub
     * @return identifier of the entity
     */
    String getIdentifier();

    String getLabel();
}

