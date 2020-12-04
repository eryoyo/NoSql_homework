package com.eryo;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  author:18301092-陈佳林
 *  time:2020年12月2日
 */
public class TreeNode {
    //结点总储存的数据
    public int val;
    //左孩子
    public TreeNode left;
    //右孩子
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    /**
     * 将字符串转换为二叉树结构，字符串格式为[xx,xx,xx,...]，xx为null表示空节点。
     * @param input 转换为二叉树的字符串
     * @return 二叉树的根节点
     */
    public static TreeNode stringToTreeNode(String input) {

        //获取由逗号分隔的字符串
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        //将字符串切分为一个个子字符串
        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        //将根节点入队列
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        //循环至队列之中没有结点
        int index = 1;
        while(!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            //添加左孩子，将左孩子加入到队列
            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            //添加右孩子将右孩子添加到队列
            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }
}
