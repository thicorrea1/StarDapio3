package com.example.stardapio.bean;

import java.util.List;

public class ContainerTypeAndSubType {
    private List<Type> types;
    private List<SubType> subTypes;

    public List<Type> getTypes() {
            return types;
    }

    public void setTypes(List<Type> types) {
            this.types = types;
    }

    public List<SubType> getSubTypes() {
            return subTypes;
    }

    public void setSubTypes(List<SubType> subTypes) {
            this.subTypes = subTypes;
    }
}
