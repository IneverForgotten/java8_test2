package com.fanlm.algorithm;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName: TreeSort
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2022/7/26 17:20
 */
public class TreeSort {

    class TreeNode{
        int val ;
        TreeNode left;
        TreeNode right;

        TreeNode(int val){
            this.val = val;
        }
    }

    public static void main(String[] args) {

    }

    class Solution{
        public List<Integer> list = new ArrayList();

        //前序遍历   根-左-右
        public void preorder(TreeNode root){
            if (root != null){
                list.add(root.val);
                preorder(root.left);
                preorder(root.right);
            }
        }
        //中序遍历  左-根-右
        public void inorder1(TreeNode root){
            if (root != null){
                inorder1(root.left);
                list.add(root.val);
                inorder1(root.right);
            }
        }

        //中序遍历 - 迭代、栈
        public void inorder2(TreeNode root){
            Stack<TreeNode> stack = new Stack<>();
            while (root != null || !stack.empty()){
                while (root != null){
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                list.add(root.val);
                root = root.right;
            }
        }

        //后序遍历 左-右-根
        public void backorder1(TreeNode root){
            if (root != null){
                backorder1(root.left);
                backorder1(root.right);
                list.add(root.val);
            }
        }
    }

}
