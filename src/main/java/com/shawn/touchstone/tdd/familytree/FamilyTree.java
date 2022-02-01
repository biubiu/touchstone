package com.shawn.touchstone.tdd.familytree;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;


public class FamilyTree {

    Node root;

    public FamilyTree(Node root) {
        this.root = root;
    }

    public void addChild(String parentName, String childName, Node.Gender gender) {
        Node node = searchByName(parentName);
        Preconditions.checkArgument(node != null, "cannot find the parent with name" + parentName);
        Preconditions.checkArgument(node.getGender().equals(Node.Gender.FEMALE), "cannot add child to father" + parentName);
        node.addChild(childName, gender);
    }

    private Node searchByName(String parentName) {
        return checkNode(root, parentName);
    }

    private Node checkNode(Node node, String name) {
        if (node == null) return null;
        if (node.getName().equals(name)) {
            return node;
        } else if (node.getSpouse() != null && node.getSpouse().getName().equals(name)) {
            return node.getSpouse();
        } else if (node.getChildren() != null) {
            for (Node child : node.getChildren()) {
                Node checked = checkNode(child, name);
                if (checked != null) {
                    return checked;
                }
            }
        }
        return null;
    }

    public List<Node> getRelationShip(String name, Relationship relation) {
        Node node = searchByName(name);
        if (node == null) return Collections.emptyList();
        return relation.apply(node);
    }

    public static Predicate<Node> male = n -> n.getGender().equals(Node.Gender.MALE);

    public enum Relationship {
        SON(node -> node.getChildren().stream().filter(male).toList()),
        DAUGHTER(node -> node.getChildren().stream().filter(not(male)).toList()),
        SIBLINGS(node -> {
            Node parent = node.getParent();
            if (parent == null) return Collections.emptyList();
            else return parent.getChildren().stream().filter(n -> !n.getName().equals(node.getName())).toList();
        }),
        BROTHER_IN_LAWS(node -> {
            Node spouse = node.getSpouse();
            if (spouse == null) return Collections.emptyList();
            else return SIBLINGS.apply(spouse).stream().filter(male).toList();
        }),
        SISTER_IN_LAWS(node -> {
            Node spouse = node.getSpouse();
            if (spouse == null) return Collections.emptyList();
            else return SIBLINGS.apply(spouse).stream().filter(not(male)).toList();
        }),
        MATERNAL_AUNT(node -> {
            Node parent = node.getParent();
            if (parent == null) return Collections.emptyList();
            return Relationship.SIBLINGS.apply(parent).stream().filter(not(male)).toList();
        }),
        PATERNAL_AUNT(node -> {
            Node parent = node.getParent();
            if (parent == null || parent.getSpouse() == null) return Collections.emptyList();
            return Relationship.SIBLINGS.apply(parent.getSpouse()).stream().filter(not(male)).toList();
        }),
        MATERNAL_UNCLE(node -> {
            Node parent = node.getParent();
            if (parent == null) return Collections.emptyList();
            return Relationship.SIBLINGS.apply(parent).stream().filter(male).toList();
        }),
        PATERNAL_UNCLE(node -> {
            Node parent = node.getParent();
            if (parent == null || parent.getSpouse() == null) return Collections.emptyList();
            return Relationship.SIBLINGS.apply(parent.getSpouse()).stream().filter(male).toList();
        });

        private final Function<Node, List<Node>> operation;

        Relationship(Function<Node, List<Node>> operation) {
            this.operation = operation;
        }

        public List<Node> apply(Node node) {
            return operation.apply(node);
        }
    }
}

