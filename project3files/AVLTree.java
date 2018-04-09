public class AVLTree<E extends Comparable<? super E>> extends BinarySearchTree<E> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void incCount(E data) {
        super.incCount(data);
        overallRoot = balance(overallRoot);
    }

   
    private BSTNode balance(BSTNode root) {
    	if(root == null)
        {
            return null;
        }

        if(height(root.left) - height(root.right) > 1)
        {
            if(height(root.left.left) >= height(root.left.right))
            {
                root = singleRightRotation(root);
            }
            else
            {
                root = doubleLeftRightRotation(root);
            }
        }
        else if(height(root.right) - height(root.left) > 1)
        {
            if(height(root.right.right) >= height(root.right.left))
            {
                root = singleLeftRotation(root);
            }
            else
            {
                root = doubleRightLeftRotation(root);
            }
        }

        return root;
    }


//Single Left Rotation
    private BSTNode singleLeftRotation(BSTNode k1) {
        BSTNode right = k1.right;
        k1.right = right.left;
        right.left = k1;
        return right;
    }

//Single RightRotation
    private BSTNode singleRightRotation(BSTNode k2) {
        BSTNode left = k2.left;
        k2.left = left.right;
        left.right = k2;
        return left;
    }

//Double Left Right
    private BSTNode doubleLeftRightRotation(BSTNode k3) {
        k3.left = singleLeftRotation(k3.left);
        return singleRightRotation(k3);
    }

  //Double Right Left
    private BSTNode doubleRightLeftRotation(BSTNode k1) {
		k1.right = singleRightRotation(k1.right);
		return singleLeftRotation(k1);
    }
}