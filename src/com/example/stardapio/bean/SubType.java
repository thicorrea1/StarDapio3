package com.example.stardapio.bean;

public class SubType extends Type {
	private Type groupType;
	
	public SubType(Type type) {
		groupType = type;
	}

	public Type getGroupType() {
		return groupType;
	}

	public void setGroupType(Type groupType) {
		this.groupType = groupType;
	}
}
