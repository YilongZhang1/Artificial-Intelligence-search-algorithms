
import java.util.List;

public class Node {
	public int[][] configuration;
	public Node parent;
	public List<String> step;
	public boolean[] actions = new boolean[4]; // represent the action this Node
												// has already take

	public Node(String s) {
		if (s.equals("cutoff")) {
			int[][] array = new int[3][3];
			configuration = array;
			parent = null;
			step = null;
		}

	}

	public Node(int[][] array, Node parent, List<String> step) {
		configuration = array;
		this.parent = parent;
		this.step = step;
	}

	public Node() {

	}

}
