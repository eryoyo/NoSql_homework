package com.eryo;

/**
 *  author:18301092-陈佳林
 *  time:2020年12月2日
 */
public class MinSumTree {

    private static int minSum;
    private static  TreeNode minRoot;

    public static int findMinSumtree1(TreeNode root){
        minSum = Integer.MAX_VALUE;
        minRoot = null;
        getSum(root);
        return minSum;
    }

    private static int getSum(TreeNode root){
        if (root == null){
            return 0;
        }

        int sum = getSum(root.left) + getSum(root.right) + root.val;
        if (sum < minSum)
        {
            minSum = sum;
            minRoot = root;
        }

        return sum;
    }

    private static int sum = Integer.MAX_VALUE;

    public static int findMinSumTree2(TreeNode root) {
        if(root == null) {
            return 0;
        }

        int left = findMinSumTree2(root.left);
        int right = findMinSumTree2(root.right);
        sum = Math.min(sum, left + right + root.val);
        return sum;
    }


    public static void main(String[] args) {
        String str = "[1,2,3,null,5]";
        TreeNode root = TreeNode.stringToTreeNode(str);

        System.out.println("第一种方法");
        System.out.println(findMinSumtree1(root));

        System.out.println("第二种方法：");
        System.out.println(findMinSumTree2(root));
    }
}
