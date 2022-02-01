package com.shawn.touchstone.tdd.familytree;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private String name;
    private List<Node> children = new ArrayList<>();
    private Gender gender;
    private Node spouse;
    private Node parent;

    public Node(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public Node(String name, Gender gender, Node parent) {
        this.name = name;
        this.gender = gender;
        this.parent = parent;
    }

    public void addChild(String name, Gender gender) {
        children.add(new Node(name, gender, this));
    }

    public void setSpouse(Node spouse) {
        this.spouse = spouse;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Node getSpouse() {
        return spouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name.equals(node.name) && Objects.equals(children, node.children) && gender.equals(node.gender) && Objects.equals(spouse, node.spouse) && Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, gender, spouse, parent);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("gender", gender)
                .add("spouse", spouse)
                .add("parents", parent)
                .toString();
    }

    enum Gender {
        MALE,
        FEMALE
    }
}
