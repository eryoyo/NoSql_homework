package com.eryo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *  author:18301092-陈佳林
 *  time:2020年12月2日
 */
public class PreOrder {

    public static List<Integer> preorderTraversal1(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> preorder = new ArrayList<Integer>();

        if (root == null) {
            return preorder;
        }

        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            preorder.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return preorder;
    }

    public static ArrayList<Integer> preorderTraversal2(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        traverse2(root, result);
        return result;
    }

    // 把root为根的preorder加入result里面
    private static void traverse2(TreeNode root, ArrayList<Integer> result) {
        if (root == null) {
            return;
        }

        result.add(root.val);
        traverse2(root.left, result);
        traverse2(root.right, result);
    }

    public static ArrayList<Integer> preorderTraversal3(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        // null or leaf
        if (root == null) {
            return result;
        }

        // Divide
        ArrayList<Integer> left = preorderTraversal3(root.left);
        ArrayList<Integer> right = preorderTraversal3(root.right);

        // Conquer
        result.add(root.val);
        result.addAll(left);
        result.addAll(right);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("第一种方法");
        String str = "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]";
        TreeNode root = TreeNode.stringToTreeNode(str);
        List<Integer> paths = preorderTraversal1(root);
        for(int path : paths) {
            System.out.println(path);
        }

        System.out.println();
        System.out.println("第二种方法");
        paths = preorderTraversal2(root);
        for(int path : paths) {
            System.out.println(path);
        }

        System.out.println();
        System.out.println("第三种方法");
        paths = preorderTraversal3(root);
        for(int path : paths) {
            System.out.println(path);
        }
    }
}
