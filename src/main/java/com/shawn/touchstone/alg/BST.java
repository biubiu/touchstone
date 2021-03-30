package com.shawn.touchstone.alg;

import java.util.LinkedList;
import java.util.List;

public class BST {
    public int value;
    public BST left;
    public BST right;

    public BST(int value) {
        this.value = value;
    }

    public BST insert(int value) {
        BST curr = this;
        while (true) {
            if ( value < curr.value) {
                if (curr.left == null) {
                    BST newNode = new BST(value);
                    curr.left = newNode;
                    break;
                } else {
                    curr = curr.left;
                }
            } else {
                if (curr.right == null) {
                    BST newNode = new BST(value);
                    curr.right = newNode;
                    break;
                } else {
                    curr = curr.right;
                }
            }
        }
        return this;
    }

    public boolean contains(int value) {
        if (value > this.value) {
            if (right != null) {
                right.contains(value);
            } else {
                return false;
            }
        } else if (value < this.value) {
            if (left != null) {
                left.contains(value);
            } else {
                return false;
            }
        }
        return true;
    }

    public BST remove(int value) {
        remove(value, null);
        return this;
    }

    private void remove(int value, BST parent) {
        if (value < this.value) {
            if (left != null) {
                left.remove(value, this);
            }
        } else if (value < this.value) {
            if (right != null) {
                right.remove(value, this);
            }
        } else {
            if (left != null && right != null) {
                this.value = right.getMinValue();
                right.remove(this.value, this);
            } else if (parent == null) {
                if (left != null) {
                    this.value = left.value;
                    right = left.right;
                    left = left.left;
                } else if (right != null) {
                    this.value = right.value;
                    left = right.left;
                    right = right.right;
                }
            } else if (parent.left == this) {
                parent.left = left != null ? left : right;
            } else if (parent.right == this) {
                parent.right = left != null ? left : right;
            }
        }
    }

    private void removeIter(int value, BST parent) {
        BST curr = this;
        while (curr != null) {
            if (value < curr.value) {
                parent = curr;
                curr = curr.left;
            } else if (value > curr.value) {
                parent = curr;
                curr = curr.right;
            } else {
                if (curr.left != null && curr.right != null) {
                    curr.value = curr.right.getMinValue();
                    curr.right.remove(curr.value, curr);
                } else if (parent == null) {
                    if (curr.left != null) {
                        curr.value = curr.left.getMinValue();
                        curr.left = curr.left.left;
                        curr.right = curr.left.right;
                    } else if (curr.right != null) {
                        curr.value = curr.right.getMinValue();
                        curr.left = curr.right.left;
                        curr.right = curr.right.right;
                    } else {

                    }
                } else if (parent.left == curr) {
                    parent.left = curr.left != null ? curr.left : curr.right;
                } else if (parent.right == curr) {
                    parent.right = curr.left != null ? curr.left : curr.right;
                 }
                break;
            }
        }
    }

    public static List<Integer> inOrderTraverse(BST tree, List<Integer> array) {
        LinkedList<BST> stack = new LinkedList<>();
        BST curr = tree;
        while (curr != null || !stack.isEmpty()) {
            while(curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            array.add(curr.value);
            curr = curr.right;
        }
        return array;
    }

    public static List<Integer> preOrderTraverse(BST tree, List<Integer> array) {
        LinkedList<BST> stack = new LinkedList<>();
        stack.add(tree);
        while (!stack.isEmpty()) {
            BST curr = stack.pop();
            array.add(curr.value);
            if (curr.right != null) {
                stack.push(curr.right);
            }
            if (curr.left != null) {
                stack.push(curr.left);
            }
        }
        return array;
    }

    public static List<Integer> postOrderTraverse(BST tree, List<Integer> array) {
        LinkedList<BST> stack = new LinkedList<>();
        stack.push(tree);
        BST prev = null;
        while (!stack.isEmpty()) {
            BST curr = stack.peek();
            if (prev == null || prev.left == curr || prev.right == curr) {
                if (curr.left != null) {
                    stack.push(curr.left);
                } else if (curr.right != null) {
                    stack.push(curr.right);
                } else {
                    stack.pop();
                    array.add(curr.value);
                }
            } else if (curr.left == prev) {
                if (curr.right != null) {
                    stack.push(curr.right);
                } else {
                    stack.pop();
                    array.add(curr.value);
                }
            } else if (curr.right == prev) {
                stack.pop();
                array.add(curr.value);
            }
            prev = curr;
        }
        return array;
    }

    private int getMinValue() {
        if (left == null) {
            return this.value;
        } else {
            return left.getMinValue();
        }
    }
}
