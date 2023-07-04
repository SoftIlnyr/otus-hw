package com.softi.bridge.shapes;

import com.softi.bridge.colors.Color;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Shape {

    protected String description = "Фигура";
    
    private Color color;

    public String getDescription() {
        return color == null ? description
                : String.join(", ", description, color.getDescription());
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
