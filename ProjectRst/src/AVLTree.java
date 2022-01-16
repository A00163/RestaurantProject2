import java.util.ArrayList;

class Node {
    int key, height;
    Node left, right;
    Node(int d) {
        key = d;
        height = 1;
    }
}
public class AVLTree {
    Node root;

    int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int findMax(int a, int b) {
        if (a > b)
            return a;
        else
            return b;
    }

    Node rightRotate(Node node) {
        Node temp = node.left;
        Node T2 = temp.right;
        temp.right = node;
        node.left = T2;

        node.height = findMax(height(node.left), height(node.right)) + 1;
        temp.height = findMax(height(temp.left), height(temp.right)) + 1;
        return temp;
    }

    Node leftRotate(Node node) {
        Node temp = node.right;
        Node T2 = temp.left;
        temp.left = node;
        node.right = T2;
        node.height = findMax(height(node.left), height(node.right)) + 1;
        temp.height = findMax(height(temp.left), height(temp.right)) + 1;
        return temp;
    }

    int BalanceFactor(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    Node insert(Node node, int key) {
        if (node == null)
            return (new Node( key));
        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + findMax(height(node.left),
                height(node.right));

        int balance = BalanceFactor(node);

        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    Node minValueNode(Node node) {
        Node node1 = node;
        while (node1.left != null)
            node1 = node1.left;
        return node1;
    }

    Node delete(Node root, int key) {
        if (root == null)
            return root;
        if (key < root.key)
            root.left = delete(root.left, key);

        else if (key > root.key)
            root.right = delete(root.right, key);

        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }
        if (root == null)
            return root;
        root.height = findMax(height(root.left), height(root.right)) + 1;

        int balance = BalanceFactor(root);
        if (balance > 1 && BalanceFactor(root.left) >= 0)
            return rightRotate(root);
        // Left Right Case
        if (balance > 1 && BalanceFactor(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        // Right Right Case
        if (balance < -1 && BalanceFactor(root.right) <= 0)
            return leftRotate(root);
        // Right Left Case
        if (balance < -1 && BalanceFactor(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    public int search(int number) {
        try {
            if (number == root.key) {
                //System.out.println(root.nameOfPerson);
                return number;
            }
            if (number < root.key) {
                root = root.left;
                return search(number);
            } else {
                root = root.right;
                return search(number);
            }
        } catch (NullPointerException e) {
            System.out.println("There is no one with this number at the party!");
            return 0;
        }
    }
    static final int COUNT = 10;
    void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    public    void print2DUtil(Node root, int space)
    {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.key + "\n");

        // Process left child
        print2DUtil(root.left, space);
    }
    public  void print2D(Node root)
    {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }
}