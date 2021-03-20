package org.gregory;

import java.util.*;

public class SimpleTreeNode<T> {
    public T NodeValue; // значение в узле
    public SimpleTreeNode<T> Parent; // родитель или null для корня
    public List<SimpleTreeNode<T>> Children; // список дочерних узлов или null
    private int level;

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent) {
        NodeValue = val;
        Parent = parent;
        Children = new LinkedList<>();
    }

    public SimpleTreeNode(T val) {
        NodeValue = val;
        Parent = null;
        Children = new LinkedList<>();
    }


    public void addChild(SimpleTreeNode<T> nodeChild) {
        getChildren().add(nodeChild);
    }

    public void deleteChild(SimpleTreeNode<T> nodeChild) {
        getChildren().remove(nodeChild);
    }

    public void resetParent() {
        this.Parent = null;
    }

    public T getNodeValue() {
        return NodeValue;
    }

    public SimpleTreeNode<T> getParent() {
        return Parent;
    }

    public List<SimpleTreeNode<T>> getChildren() {
        return Children;
    }

    public void setParent(SimpleTreeNode<T> parent) {
        Parent = parent;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}