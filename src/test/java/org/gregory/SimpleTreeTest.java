package org.gregory;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.*;

class SimpleTreeTest {

    SimpleTree<Integer> treeInt;
    SimpleTreeNode<Integer> root;
    SimpleTreeNode<Integer> nodeFirstLevel1Value2;

    @BeforeEach
    void setUp() {
        root = new SimpleTreeNode<>(1, null);
        treeInt = new SimpleTree<>(root);
        nodeFirstLevel1Value2 = new SimpleTreeNode<>(2, null);
    }

    @AfterEach
    void tearDown() {
        root = null;
        treeInt = null;
        nodeFirstLevel1Value2 = null;
    }

    @Test
    void addChild_OnlyRootInTree() {
        treeInt.AddChild(treeInt.getRoot(), nodeFirstLevel1Value2);

        assertThat(treeInt.getRoot().getChildren())
                .as("Root = %d Child = %s ", treeInt.getRoot().getNodeValue(), treeInt.getRoot().getChildren().toString())
                .isNotNull()
                .isNotEmpty()
                .contains(nodeFirstLevel1Value2)
                .isEqualTo(new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeFirstLevel1Value2);
                }});
        assertThat(nodeFirstLevel1Value2.getParent(), is(treeInt.getRoot()));
    }

    @Test
    void addChild_TreeIsEmptyAddRoot() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(null);
        SimpleTreeNode<Integer> node = new SimpleTreeNode<>(10);
        treeActual.AddChild(null, node);
        assertThat(treeActual.getRoot())
                .isNotNull()
                .isEqualTo(node);
        assertThat(treeActual.getRoot().getChildren()).isEmpty();
        assertThat(treeActual.getRoot().getNodeValue()).isEqualTo(10);
    }

    @Test
    void addChild_2NodesAddToTheFirstLevelAnd1ToTheSecondLevel() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(treeActual.getRoot().getNodeValue()).isEqualTo(1);
        assertThat(treeActual.getRoot().getChildren())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeFirstLevel1);
                    add(nodeSecondLevel1);

                }});
        assertThat(treeActual.getRoot().getChildren().get(0))
                .isEqualTo(nodeFirstLevel1);
        assertThat(treeActual.getRoot().getChildren().get(1))
                .isEqualTo(nodeSecondLevel1);
        assertThat(treeActual.getRoot().getChildren().get(0).getChildren().get(0).getNodeValue())
                .isEqualTo(nodeFirstLevel2.getNodeValue());
        assertThat(treeActual.getRoot().getChildren().get(0).getChildren().get(0))
                .isEqualTo(nodeFirstLevel2);

    }

    @Test
    void deleteNode_WithoutChildren() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        treeActual.DeleteNode(nodeFirstLevel2);

        assertThat(treeActual.getRoot().getNodeValue()).isEqualTo(1);
        assertThat(treeActual.getRoot().getChildren())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeFirstLevel1);
                    add(nodeSecondLevel1);

                }});
        assertThat(treeActual.getRoot().getChildren().get(0))
                .isEqualTo(nodeFirstLevel1);
        assertThat(treeActual.getRoot().getChildren().get(1))
                .isEqualTo(nodeSecondLevel1);

        assertThat(treeActual.getRoot().getChildren().get(0).getChildren())
                .isEmpty();
    }

    @Test
    void deleteNode_WithChild() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        treeActual.DeleteNode(nodeFirstLevel1);

        assertThat(treeActual.getRoot().getNodeValue()).isEqualTo(1);
        assertThat(treeActual.getRoot().getChildren())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeSecondLevel1);

                }});
        assertThat(treeActual.getRoot().getChildren().get(0))
                .isEqualTo(nodeSecondLevel1);
        assertThat(treeActual.getRoot().getChildren().size())
                .isEqualTo(1);

        assertThat(treeActual.getRoot().getChildren().get(0).getChildren())
                .isEmpty();
    }

    //use Apache Commands Collections
    @Test
    void getAllNodes() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(CollectionUtils.isEqualCollection(
                treeActual.GetAllNodes(),
                new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(treeActual.getRoot());
                    add(nodeFirstLevel1);
                    add(nodeSecondLevel1);
                    add(nodeFirstLevel2);
                }}
        ))
                .isTrue();
    }

    @Test
    void findNodesByValue_NoRepeat_FindRoot() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(CollectionUtils.isEqualCollection(
                treeActual.FindNodesByValue(1),
                new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(treeActual.getRoot());
                }}
        )).isTrue();
    }

    @Test
    void findNodesByValue_NoRepeat_FindNodeMiddle() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(CollectionUtils.isEqualCollection(
                treeActual.FindNodesByValue(12),
                new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeSecondLevel1);
                }}
        )).isTrue();
    }

    @Test
    void findNodesByValue_NoRepeat_FindLeaf() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(CollectionUtils.isEqualCollection(
                treeActual.FindNodesByValue(21),
                new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeFirstLevel2);
                }}
        )).isTrue();
    }

    @Test
    void findNodesByValue_Repeat_FindNodeLeafAndRoot() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(1);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(CollectionUtils.isEqualCollection(
                treeActual.FindNodesByValue(1),
                new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeFirstLevel2);
                    add(treeActual.getRoot());
                }}
        ))
                .isTrue();
    }

    //TODO: Need to do test
    @Test
    void moveNode() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(1);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        treeActual.MoveNode(nodeFirstLevel1, nodeSecondLevel1);

        assertThat(treeActual.getRoot().getNodeValue()).isEqualTo(1);
        assertThat(treeActual.getRoot().getChildren())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(new LinkedList<SimpleTreeNode<Integer>>() {{
                    add(nodeSecondLevel1);
                }});
        assertThat(treeActual.getRoot().getChildren().get(0))
                .isEqualTo(nodeSecondLevel1);
        assertThat(treeActual.getRoot().getChildren().size())
                .isEqualTo(1);
        assertThat(treeActual.getRoot().getChildren().get(0).getChildren().get(0))
                .isEqualTo(nodeFirstLevel1);
        assertThat(treeActual.getRoot().getChildren().get(0).getChildren().size())
                .isEqualTo(1);
        assertThat(treeActual.getRoot().getChildren().get(0).getChildren().get(0).getChildren().get(0))
                .isEqualTo(nodeFirstLevel2);
    }

    @Test
    void count() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(1);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(treeActual.Count()).isEqualTo(4);
    }

    @Test
    void count_TreeOnlyRoot() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        assertThat(treeActual.Count()).isEqualTo(1);
    }

    @Test
    void count_TreeEmpty() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(null);
        assertThat(treeActual.Count()).isEqualTo(0);
    }

    @Test
    void count_AfterDeleteNodes() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        treeActual.DeleteNode(nodeFirstLevel1);

        assertThat(treeActual.Count()).isEqualTo(2);
    }

    @Test
    void leafCount() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        assertThat(treeActual.LeafCount()).isEqualTo(2);
    }

    @Test
    void leafCount_OnlyRoot() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        assertThat(treeActual.LeafCount()).isEqualTo(1);
    }

    @Test
    void writeDepthNodes() {
        SimpleTree<Integer> treeActual = new SimpleTree<>(new SimpleTreeNode<Integer>(1));
        SimpleTreeNode<Integer> nodeFirstLevel1 = new SimpleTreeNode<>(11);
        SimpleTreeNode<Integer> nodeSecondLevel1 = new SimpleTreeNode<>(12);
        SimpleTreeNode<Integer> nodeFirstLevel2 = new SimpleTreeNode<>(21);

        treeActual.addChildArray(treeActual.getRoot(), new SimpleTreeNode[]{nodeFirstLevel1, nodeSecondLevel1});
        treeActual.AddChild(nodeFirstLevel1, nodeFirstLevel2);

        treeActual.writeLevelNodes();
        assertThat(treeActual.getRoot().getLevel()).isEqualTo(1);
        assertThat(nodeFirstLevel1.getLevel()).isEqualTo(2);
        assertThat(nodeSecondLevel1.getLevel()).isEqualTo(2);
        assertThat(nodeFirstLevel2.getLevel()).isEqualTo(3);
    }
}