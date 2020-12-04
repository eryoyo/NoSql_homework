package com.eryo;

import java.util.logging.Level;

/**
 *  author:18301092-陈佳林
 *  time:2020年12月2日
 */
public class MinSumTree {

    private static int sum = Integer.MAX_VALUE;

    public static int findMinSumTree(TreeNode root) {
        if(root == null) {
            return 0;
        }

        int left = findMinSumTree(root.left);
        int right = findMinSumTree(root.right);
        sum = Math.min(sum, left + right + root.val);
        return sum;
    }


    public static void main(String[] args) {
        String str = "[1,2,3,null,5]";
        TreeNode root = TreeNode.stringToTreeNode(str);
        System.out.println(findMinSumTree(root));
    }
}
