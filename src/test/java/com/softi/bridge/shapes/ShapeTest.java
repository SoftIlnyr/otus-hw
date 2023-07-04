package com.softi.bridge.shapes;

import com.softi.bridge.colors.CustomColor;
import com.softi.bridge.colors.Red;
import com.softi.bridge.colors.Yellow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {
    
    @Test
    public void testShape() {
        Shape triangle = new Triangle();
        triangle.setColor(new Red());

        System.out.println(triangle.getDescription());
        
        Shape rectangle = new Rectanble();
        rectangle.setColor(new Yellow());

        System.out.println(rectangle.getDescription());

        CustomShape customShape = new CustomShape();
        customShape.setDescription("Кракозябра");

        CustomColor customColor = new CustomColor();
        customColor.setDescription("Серо-буро-малиновый");
        
        customShape.setColor(customColor);

        System.out.println(customShape.getDescription());
    }

}