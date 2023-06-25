import java.util.ArrayDeque;

/** This class contains a single static function for finding the level with the largest sum in an integer binary tree. */
public class LevelLargestSum {
    /**
     * Finds the level with the largest sum in an integer binary tree.
     * If there are multiple levels with the largest sum, the higher level will be returned.
     *
     * @param root the root of the integer binary tree
     * @return the level with the largest sum, or -1 if the tree is empty.
     */
    public static int getLevelWithLargestSum(BinNode<Integer> root) {
        if (root == null) {
            return -1;
        }
        int sum = 0;
        int largestSum = root.getData();
        int level = 0;
        int largestSumLevel = level;

        ArrayDeque<BinNode<Integer>> currentLevel = new ArrayDeque<>();
        currentLevel.add(root);
        ArrayDeque<BinNode<Integer>> nextLevel = new ArrayDeque<>();

        while (!currentLevel.isEmpty()) {
            for (BinNode<Integer> node : currentLevel) {
                // Sum level
                sum += node.getData();
                // Get next level
                if (node.getLeft() != null) {
                    nextLevel.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    nextLevel.add(node.getRight());
                }
            }
            // Update largest sum and level
            if (sum > largestSum) {
                largestSum = sum;
                largestSumLevel = level;
            }
            // Next level
            currentLevel = nextLevel;
            nextLevel = new ArrayDeque<>();
            sum = 0;
            level++;
        }
        return largestSumLevel;
    }
}
