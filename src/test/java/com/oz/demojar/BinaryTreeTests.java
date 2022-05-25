package com.oz.demojar;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.CountryService;
import com.oz.demojar.service.PersonService;
import com.oz.demojar.utils.BinaryTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
// @ContextConfiguration(classes = BinaryTreeTests.class)
@ActiveProfiles(value="dev")
public class BinaryTreeTests {

    private static final Logger log = LoggerFactory.getLogger(BinaryTreeTests.class);

    private BinaryTree tree;

    @Test
    @DisplayName("Fetch tree nodes in pre-order traversal")
    public void testPreOrderBT() {
        ArrayList<String> traversedString = new ArrayList<>();
        traversedString.add("1");
        traversedString.add("2");
        traversedString.add("4");
        traversedString.add("5");
        traversedString.add("3");

        tree = new BinaryTree("1");
        tree.stringRootNode.left = new BinaryTree.StringNode("2");
        tree.stringRootNode.left.left = new BinaryTree.StringNode("4");
        tree.stringRootNode.left.right = new BinaryTree.StringNode("5");
        tree.stringRootNode.right = new BinaryTree.StringNode("3");

        ArrayList<String> traversal = new ArrayList<>();
        BinaryTree.preOrderBT(tree.stringRootNode, traversal);

        log.info(traversedString.toString());
        log.info(traversal.toString());

        assertEquals(traversedString, traversal,
                "expected traversal asserted");
    }

    @Test
    @DisplayName("Fetch tree nodes in pre-order traversal")
    public void testInOrderBT() {
        ArrayList<String> traversedString = new ArrayList<>();
        traversedString.add("4");
        traversedString.add("2");
        traversedString.add("5");
        traversedString.add("1");
        traversedString.add("3");

        tree = new BinaryTree("1");
        tree.stringRootNode.left = new BinaryTree.StringNode("2");
        tree.stringRootNode.left.left = new BinaryTree.StringNode("4");
        tree.stringRootNode.left.right = new BinaryTree.StringNode("5");
        tree.stringRootNode.right = new BinaryTree.StringNode("3");

        ArrayList<String> traversal = new ArrayList<>();
        BinaryTree.inOrderBT(tree.stringRootNode, traversal);

        log.info(traversedString.toString());
        log.info(traversal.toString());

        assertEquals(traversedString, traversal,
                "expected traversal asserted");
    }

}
