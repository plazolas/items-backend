package com.oz.demojar.utils;

import java.util.ArrayList;

public class BinaryTree {

    public TreeNode root;
    public StringNode stringRootNode;

    public BinaryTree() {}

    public BinaryTree(int d) {
        root = new TreeNode(d);
        root.left = null;
        root.right = null;
    }

    public BinaryTree(String data) {
        stringRootNode = new StringNode(data);
        stringRootNode.left = null;
        stringRootNode.right = null;
    }

    public static class TreeNode {

        public int data;
        public TreeNode left, right;

        public TreeNode(int d) {
            data = d;
            left = right = null;
        }
    }

    public static class StringNode {

        public String data;
        public StringNode left, right;

        public StringNode(String s) {
            data = s;
            left = right = null;
        }
    }

    // findHeight() will determine the maximum height of the binary tree
    public static int findHeight(TreeNode root) {
        //Check whether tree is empty
        if(root == null) {
            return 0;
        } else {
            int leftHeight = 0, rightHeight = 0;

            //Calculate the height of left subtree
            if(root.left != null)
                leftHeight = findHeight(root.left);

            //Calculate the height of right subtree
            if(root.right != null)
                rightHeight = findHeight(root.right);

            //Compare height of left subtree and right subtree
            //and store maximum of two in variable max
            int max = Math.max(leftHeight, rightHeight);

            //Calculate the total height of tree by adding height of root
            return (max + 1);
        }
    }

    // Check height balance
    public boolean checkHeightBalance(TreeNode root, Height height) {

        // Check for emptiness
        if (root == null) {
            height.height = 0;
            return true;
        }

        Height tempLeftHeight = new Height(), tempRightHeight = new Height();
        boolean left = checkHeightBalance(root.left, tempLeftHeight);
        boolean right = checkHeightBalance(root.right, tempRightHeight);
        int leftHeight = tempLeftHeight.height;
        int rightHeight = tempRightHeight.height;

        height.height = Math.max(leftHeight, rightHeight) + 1;

        if ((leftHeight - rightHeight >= 2) || (rightHeight - leftHeight >= 2))
            return false;
        else
            return left && right;
    }

    public static void preOrderBT (StringNode node, ArrayList<String> traversed) {
            if (node == null) return;
            traversed.add(node.data);
            preOrderBT(node.left, traversed);
            preOrderBT(node.right, traversed);
            return;
    }

    public static void inOrderBT (StringNode node, ArrayList<String> traversed) {
        if (node == null) return;
        inOrderBT(node.left, traversed);
        traversed.add(node.data);
        inOrderBT(node.right, traversed);
        return;
    }

    public static void postOrderBT (StringNode node, ArrayList<String> traversed) {
        if (node == null) return;
        postOrderBT(node.left, traversed);
        postOrderBT(node.right, traversed);
        traversed.add(node.data);
        return;
    }

    // Simple Height class
    public static class Height {
        int height = 0;
    }

    // linked list
    public static class LNode
    {
        int data;
        LNode next;
        LNode(int d) {
            data = d;
            next = null;
        }
    }

    // double linked list
    public static class DLNode
    {
        int data;
        DLNode next;
        DLNode previous;
        DLNode(int d) {
            data = d;
            next = null;
            previous = null;
        }
    }



}
