package com.example.demo;

import jakarta.persistence.*;

import java.util.Arrays;
// Custom converter for handling pgvector as float[]
@jakarta.persistence.Converter
public class Converter implements AttributeConverter<float[], String> {
    @Override
    public String convertToDatabaseColumn(float[] attribute) {
        if (attribute == null) {
            return null;
        }
        return Arrays.toString(attribute)
                .replace("[", "")
                .replace("]", "");
    }
    @Override
    public float[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] stringArray = dbData.split(",");
        float[] floatArray = new float[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            floatArray[i] = Float.parseFloat(stringArray[i].trim());
        }
        return floatArray;
    }
}