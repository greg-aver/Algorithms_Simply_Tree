package org.gregory;

import java.util.*;

class SimpleTree<T> {
    public SimpleTreeNode<T> Root; // корень, может быть null

    public SimpleTree(SimpleTreeNode<T> root) {
        Root = root;
    }

    public void AddChild(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T> NewChild) {
        // ваш код добавления нового дочернего узла существующему ParentNode
        if (ParentNode == null && getRoot() == null) {
            setRoot(NewChild);
            return;
        }
        ParentNode.addChild(NewChild);
        if (NewChild.getParent() != ParentNode) {
            NewChild.setParent(ParentNode);
        }
    }

    public void addChildArray(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T>[] NewChildArray) {
        for (SimpleTreeNode<T> nodeChild : NewChildArray) {
            AddChild(ParentNode, nodeChild);
        }
    }

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete) {
        // ваш код удаления существующего узла NodeToDelete
        NodeToDelete.getParent().deleteChild(NodeToDelete);
        NodeToDelete.resetParent();
    }

    public List<SimpleTreeNode<T>> GetAllNodes() {
        // ваш код выдачи всех узлов дерева в определённом порядке
        List<SimpleTreeNode<T>> listAllNodes = new LinkedList<>();
        if (getRoot() == null) {
            return listAllNodes;
        }
        crawlerTreeAllNodes(getRoot(), listAllNodes);
        return listAllNodes;
    }

    private void crawlerTreeAllNodes(SimpleTreeNode<T> rootNode, List<SimpleTreeNode<T>> listNodes) {
        listNodes.add(rootNode);
        for (SimpleTreeNode<T> nodeChild : rootNode.getChildren()) {
            crawlerTreeAllNodes(nodeChild, listNodes);
        }
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val) {
        // ваш код поиска узлов по значению
        LinkedList<SimpleTreeNode<T>> listFindNodes = new LinkedList<>();
        if (getRoot() == null) {
            return listFindNodes;
        }
        crawlerTreeFindNode(getRoot(), listFindNodes, val);
        return listFindNodes;
    }

    private void crawlerTreeFindNode(SimpleTreeNode<T> rootNode, List<SimpleTreeNode<T>> listNodes, T val) {
        if (rootNode.getNodeValue().equals(val)) {
            listNodes.add(rootNode);
        }
        for (SimpleTreeNode<T> nodeChild : rootNode.getChildren()) {
            crawlerTreeFindNode(nodeChild, listNodes, val);
        }
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent) {
        // ваш код перемещения узла вместе с его поддеревом --
        // в качестве дочернего для узла NewParent
        DeleteNode(OriginalNode);
        AddChild(NewParent, OriginalNode);
    }

    public int Count() {
        // количество всех узлов в дереве
        if (getRoot() != null) {
            return 1 + crawlerTreeCountNodes(getRoot());
        }
        return 0;
    }

    private int crawlerTreeCountNodes(SimpleTreeNode<T> nodeRoot) {
        int counter = 0;

        if (nodeRoot.getChildren().isEmpty()) {
            return counter;

        } else {
            for (SimpleTreeNode<T> nodeChild : nodeRoot.getChildren()) {
                counter += 1 + crawlerTreeCountNodes(nodeChild);
            }
        }
        return counter;
    }

    public int LeafCount() {
        // количество листьев в дереве
        if (getRoot() != null) {
            return crawlerTreeLeafCount(getRoot());
        } else {
            return 0;
        }
    }

    private int crawlerTreeLeafCount(SimpleTreeNode<T> nodeRoot) {
        int counter = 0;
        if (nodeRoot.getChildren().isEmpty()) {
            counter++;
        } else {
            for (SimpleTreeNode<T> nodeChild : nodeRoot.getChildren()) {
                counter += crawlerTreeLeafCount(nodeChild);
            }
        }
        return counter;
    }

    public void writeLevelNodes() {
        if (getRoot() != null) {
            getRoot().setLevel(1);
            crawlerTreeWriteLevelNodes(getRoot());
        }
    }

    private void crawlerTreeWriteLevelNodes(SimpleTreeNode<T> nodeRoot) {
        for (SimpleTreeNode<T> nodeChild : nodeRoot.getChildren()) {
            nodeChild.setLevel(nodeChild.getParent().getLevel() + 1);
            crawlerTreeWriteLevelNodes(nodeChild);
        }
    }

    public SimpleTreeNode<T> getRoot() {
        return Root;
    }

    public void setRoot(SimpleTreeNode<T> node) {
        this.Root = node;
    }
}