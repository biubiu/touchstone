package com.shawn.touchstone.tdd.familytree;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.shawn.touchstone.tdd.familytree.FamilyTree.Relationship.MATERNAL_AUNT;
import static com.shawn.touchstone.tdd.familytree.FamilyTree.Relationship.MATERNAL_UNCLE;
import static com.shawn.touchstone.tdd.familytree.FamilyTree.Relationship.PATERNAL_AUNT;
import static com.shawn.touchstone.tdd.familytree.FamilyTree.Relationship.PATERNAL_UNCLE;
import static com.shawn.touchstone.tdd.familytree.Node.Gender.*;
import static com.shawn.touchstone.tdd.familytree.Node.Gender.FEMALE;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FamilyTreeTest {

    Node sue;
    Node adam;
    FamilyTree familyTree;
    @Before
    public void setupTree() {
        sue = new Node("Sue", FEMALE);
        adam = new Node("Adam", MALE);
        sue.setSpouse(adam);

        familyTree = new FamilyTree(sue);

        familyTree.addChild("Sue", "Lihoe", MALE);
        familyTree.addChild("Sue", "Lee", FEMALE);
        familyTree.addChild("Sue", "Ajab", MALE);
        familyTree.addChild("Sue", "Susan", FEMALE);

        familyTree.addChild("Lee", "Saturday", MALE);


    }

    @Test
    public void shouldReturnDaughterAndSon() {

        List<Node> sons = familyTree.getRelationShip("Sue", FamilyTree.Relationship.SON);
        assertThat(sons.size(), is(2));
        assertThat(sons, hasItem(hasProperty("name", is("Lihoe"))));
        assertThat(sons, hasItem(hasProperty("name", is("Ajab"))));

        List<Node> daughters = familyTree.getRelationShip("Sue", FamilyTree.Relationship.DAUGHTER);
        assertThat(daughters.size(), is(2));
        assertThat(daughters, hasItem(hasProperty("name", is("Susan"))));
        assertThat(daughters, hasItem(hasProperty("name", is("Lee"))));

    }

    @Test
    public void shouldReturnParent() {
        List<Node> son = familyTree.getRelationShip("Sue", FamilyTree.Relationship.SON);
        assertThat(son.get(0).getParent().getName(), is("Sue"));
        assertThat(son.get(0).getParent().getSpouse().getName(), is("Adam"));
    }

    @Test
    public void shouldReturnAllSiblings() {
        List<Node> siblings = familyTree.getRelationShip("Lihoe", FamilyTree.Relationship.SIBLINGS);
        assertThat(siblings.size(), is(3));
        assertThat(siblings, hasItem(hasProperty("name", is("Susan"))));
        assertThat(siblings, hasItem(hasProperty("name", is("Ajab"))));
        assertThat(siblings, hasItem(hasProperty("name", is("Lee"))));
    }

    @Test
    public void shouldReturnMaternalAunt() {
        List<Node> maternalAunts = familyTree.getRelationShip("Saturday", MATERNAL_AUNT);
        assertThat(maternalAunts.size(), is(1));
        assertThat(maternalAunts, hasItem(hasProperty("name", is("Susan"))));
    }

    @Test
    public void shouldReturnMaternalUncle() {

        List<Node> maternalUncles = familyTree.getRelationShip("Saturday", MATERNAL_UNCLE);
        assertThat(maternalUncles.size(), is(2));
        assertThat(maternalUncles, hasItem(hasProperty("name", is("Lihoe"))));
        assertThat(maternalUncles, hasItem(hasProperty("name", is("Ajab"))));
    }

    @Test
    public void shouldReturnPaternalAunt() {
        List<Node> aunts = familyTree.getRelationShip("", PATERNAL_AUNT);
    }

    @Test
    public void shouldReturnPaternalUncle() {
        List<Node> uncles = familyTree.getRelationShip("", PATERNAL_UNCLE);
    }


}