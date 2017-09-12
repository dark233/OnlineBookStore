package com.taunton.javabean;

public class Recharge {
private String id;
private float face_value;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public float getFace_value() {
	return face_value;
}
public void setFace_value(float face_value) {
	this.face_value = face_value;
}
@Override
public String toString() {
	return "Recharge [id=" + id + ", face_value=" + face_value + "]";
}
public Recharge() {
	super();
}

}
