/** This class contains a single static recursive function for validating the existence of a path in a character binary tree. */
public class PathFromRoot {
    /**
     * Checks if a there is a path of characters in a binary tree that match the given string.
     *
     * @param root the root of the character binary tree
     * @param str the string to find in the tree
     * @return true if found path, false if path does not exist
     */
    public static boolean doesPathExist(BinNode<Character> root, String str) {
        // Base case
        if (str.isEmpty()) {
            return true;
        }
        // Check if reached path end before string end
        if (root == null) {
            return false;
        }
        // Check if the current node contains the first character
        if (root.getData().equals(str.charAt(0))) {
            // Check if the left or the right subtree contains a valid path for the rest of the string
            return doesPathExist(root.getLeft(), str.substring(1)) || doesPathExist(root.getRight(), str.substring(1));
        }
        // Path is not valid
        return false;
    }
}
