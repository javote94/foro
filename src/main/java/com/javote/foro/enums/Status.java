package com.javote.foro.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    RESOLVED,
    UNSOLVED;

    // Indica a la biblioteca Jackson que use este método para convertir una cadena
    // de texto JSON en una instancia del enum (Deserialización)
    @JsonCreator
    public static Status fromString(String value) {
        return Status.valueOf(value.toUpperCase());
    }

    // Indica a Jackson que use este método para convertir una instancia del enum en
    // una cadena JSON (Serialización)
    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
