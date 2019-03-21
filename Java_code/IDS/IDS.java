
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rui Huang
 *
 */
public class IDS {
	public Node cutoff = new Node("cutoff");
	public Node failure = new Node("failure");
	public int count = 5; // output first 5 Nodes
	public int number = 0; // record how many Nodes have been expanded

	public Node eightPuzzle(Node state, int limit) {
		number++;
		if(number > 100000) return failure;
		if (count >= 1) {
			System.out.println("---------------------------the " + (6 - count) + "th Node been expanded is :-------------------");
			for (int row = 0; row < 3; row++) {
				System.out.print("                                    ");
				for (int column = 0; column < 3; column++) {
					if(state.configuration[row][column] == 9) {
						System.out.print("* ");
						continue;
					}
					System.out.print(state.configuration[row][column] + " ");
				}
				System.out.println("");
			}
			count--;
		}
		if (check(state)) {
			return state;
		} else if (limit == 0)
			return cutoff;
		else {
			for (int i = 1; i <= 4; i++) {
				if (canMove(state)) {
					Node child = childNode(state);
					Node result = eightPuzzle(child, limit - 1);
					if (!result.equals(cutoff))
						return result;

				}

			}
			return cutoff;

		}
	}

	public Node IDSSearch(Node state) {
		Node result = new Node();
		for (int i = 1; ; i++) {
			Node initialState = new Node(state.configuration, null, new ArrayList<String>());

			
			result = eightPuzzle(initialState, i);
			if(result.equals(failure)) {
				break;
			}
			if (result != cutoff) {
				break;
			}
		}
		return result;
	}

	public boolean check(Node state) {
		boolean result = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (state.configuration[i][j] != i * 3 + j + 1) {
					result = false;
					break;
				}

			}
			if (result == false)
				break;

		}
		return result;
	}

	public Node childNode(Node state) {
		int min = 10;
		int[][] position = getBlockPosition(state);
		int row = position[0][0];
		int column = position[0][1];
		int direction = 0;
		if (row > 0 && state.configuration[row - 1][column] < min && state.actions[0] == false) {
			min = state.configuration[row - 1][column];
			direction = 1; // 1 means move UP
		}
		if (row < 2 && state.configuration[row + 1][column] < min && state.actions[1] == false) {
			min = state.configuration[row + 1][column];
			direction = 2; // 2 means move down
		}
		if (column > 0 && state.configuration[row][column - 1] < min && state.actions[2] == false) {
			min = state.configuration[row][column - 1];
			direction = 3; // 3 means move LEFT
		}
		if (column < 2 && state.configuration[row][column + 1] < min && state.actions[3] == false) {
			min = state.configuration[row][column + 1];
			direction = 4; // 4 means move RIGHT
		}
		if (direction == 1) {
			state.actions[0] = true;
			return moveUp(state);
		}
		if (direction == 2) {
			state.actions[1] = true;
			return moveDown(state);
		}
		if (direction == 3) {
			state.actions[2] = true;
			return moveLeft(state);
		} else {

			state.actions[3] = true;
			return moveRight(state);
		}
	}

	public int[][] getBlockPosition(Node state) {
		int[][] result = new int[1][2];
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (state.configuration[i][j] == 9) {
					result[0][0] = i;
					result[0][1] = j;
					flag = true;
					break;

				}
			}
			if (flag)
				break;
		}

		return result;

	}

	public boolean canMove(Node state) {
		boolean result = false;
		int row = getBlockPosition(state)[0][0];
		int column = getBlockPosition(state)[0][1];
		if (row > 0 && state.actions[0] == false) // can move up
			result = true;
		if (row < 2 && state.actions[1] == false) // can move down
			result = true;
		if (column > 0 && state.actions[2] == false) // can move left
			result = true;
		if (column < 2 && state.actions[3] == false) // can move right
			result = true;
		return result;
	}

	public Node moveLeft(Node state) {

		int[][] array = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array[i][j] = state.configuration[i][j];
			}
		}
		int[][] blockPosition = getBlockPosition(state);
		int row = blockPosition[0][0];
		int column = blockPosition[0][1];
		int temp = array[row][column - 1];
		array[row][column - 1] = 9; // we use 9 to represent the white block
		array[row][column] = temp;
		Node result = new Node();
		result.configuration = array;

		result.parent = state;
		List<String> step = new ArrayList<String>();
		for (String elem : state.step)
			step.add(elem);
		step.add("Left");
		result.step = step;
		return result;

	}

	public Node moveRight(Node state) {

		int[][] array = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array[i][j] = state.configuration[i][j];
			}
		}
		int[][] blockPosition = getBlockPosition(state);
		int row = blockPosition[0][0];
		int column = blockPosition[0][1];
		int temp = array[row][column + 1];
		array[row][column + 1] = 9; // we use 9 to represent the white block
		array[row][column] = temp;
		Node result = new Node();
		result.configuration = array;
		result.parent = state;
		List<String> step = new ArrayList<String>();
		for (String elem : state.step)
			step.add(elem);
		step.add("Right");
		result.step = step;
		return result;

	}

	public Node moveUp(Node state) {

		int[][] array = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array[i][j] = state.configuration[i][j];
			}
		}
		int[][] blockPosition = getBlockPosition(state);
		int row = blockPosition[0][0];
		int column = blockPosition[0][1];
		int temp = array[row - 1][column];
		array[row - 1][column] = 9; // we use 9 to represent the white block
		array[row][column] = temp;

		Node result = new Node();
		result.configuration = array;
		result.parent = state;
		List<String> step = new ArrayList<String>();
		for (String elem : state.step)
			step.add(elem);
		step.add("Up");
		result.step = step;
		return result;

	}

	public Node moveDown(Node state) {

		int[][] array = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array[i][j] = state.configuration[i][j];
			}
		}
		int[][] blockPosition = getBlockPosition(state);
		int row = blockPosition[0][0];
		int column = blockPosition[0][1];
		int temp = array[row + 1][column];
		array[row + 1][column] = 9; // we use 9 to represent the white block
		array[row][column] = temp;
		Node result = new Node();
		result.configuration = array;
		result.parent = state;
		List<String> step = new ArrayList<String>();
		for (int i = 0; i < state.step.size(); i++)
			step.add(state.step.get(i));
		step.add("Down");
		result.step = step;
		return result;

	}

	public static void main(String args[]) { // µÝ¹éÌ«Éîµ¼ÖÂÕ»Òç³ö
		System.out.println("--------------------Solve 8-puzzle Iterative deeping tree(IDS) search-------------------------");
		Scanner scan = new Scanner(System.in);
		
		int[][] testCase1 = { { 9, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
		int[][] testCase2 = { { 9, 5, 2 }, { 1, 8, 3 }, { 4, 7, 6 } }; // test 2
		int[][] testCase3 = { { 8, 6, 7 }, { 2, 5, 4 }, { 3, 9, 1 } }; // test 3
																		// fail
		System.out.println("---------------------------There are three test cases :---------------------------------------");
		for (int caseNumber = 0; caseNumber < 3; caseNumber++) {
			System.out.println("-----------------------------------case " + (caseNumber + 1) + " :---------------------------------");
			int[][] testCase;
			if (caseNumber == 0) {
				
				testCase = testCase1;
				
			}
			else if (caseNumber == 1) {
				testCase = testCase2;
			} else {
				testCase = testCase3;
			}
			for (int i = 0; i < 3; i++) {
				System.out.print("                                    ");
				for (int j = 0; j < 3; j++){
					if(testCase[i][j] == 9) {
						System.out.print("* ");
						continue;
					}
					System.out.print(testCase[i][j] + " ");
				}
					
				System.out.println(" ");
			}
		}
		
		System.out.println("Which case you want to evaluate? For case 1, type in 1; For case 2 type in 2; For case 3, type in 3.");
		
	while(scan.hasNext()) {
		IDS instance = new IDS();
		
		int caseNumber = Integer.parseInt(scan.next());
		Node initialState;
		Node middleNode; // we use this node to show  the action need to be taken from initial state to final state
		if(caseNumber == 1) {
			initialState = new Node(testCase1,null,new ArrayList<String>());
			middleNode = new Node(testCase1, null, new ArrayList<String>());
		}
		else if(caseNumber == 2) {
			initialState = new Node(testCase2,null,new ArrayList<String>());
			middleNode = new Node(testCase2, null, new ArrayList<String>());
		}
		else {
			initialState = new Node(testCase3,null,new ArrayList<String>());
			middleNode = new Node(testCase3, null, new ArrayList<String>());
		}
		long startTime = System.currentTimeMillis();
		Node goalNode = instance.IDSSearch(initialState);
		long endTime = System.currentTimeMillis();
		if(goalNode.equals(instance.failure)) {
			System.out.println("------------------sorry, solution cann't be found within 100,000 nodes -----------------");
		}
		else {
		List<String> step = goalNode.step;
		System.out.println("");
		System.out.println("------------------------------the solution found is below: -------------------");
		System.out.println("");
		System.out.println("----------------------------------initial state: ------------------------------");
		for (int i = 0; i < 3; i++) {
			System.out.print("                                    ");
			for (int j = 0; j < 3; j++){
				if(initialState.configuration[i][j] == 9) {
					System.out.print("* ");
					continue;
				}
				System.out.print(initialState.configuration[i][j] + " ");
				}
			System.out.println("");
		}
		
		System.out.println("");
		for (int i = 0; i < step.size(); i++) { // show the moving process step
												// by step
			String elem = step.get(i);
			System.out.println("-------------------------------" + (i + 1) + "th step: move " + elem + "---------------------------");
			if (elem.equals("Left")) {
				middleNode = instance.moveLeft(middleNode);
				for (int row = 0; row < 3; row++) {
					System.out.print("                                    ");
					for (int column = 0; column < 3; column++){
						if(middleNode.configuration[row][column] == 9) {
							System.out.print("* ");
							continue;
						}
						System.out.print(middleNode.configuration[row][column] + " ");
					}
					System.out.println("");
				}
				System.out.println("");
			}

			if (elem.equals("Right")) {
				middleNode = instance.moveRight(middleNode);
				for (int row = 0; row < 3; row++) {
					System.out.print("                                    ");
					for (int column = 0; column < 3; column++){
						if(middleNode.configuration[row][column] == 9) {
							System.out.print("* ");
							continue;
						}
						System.out.print(middleNode.configuration[row][column] + " ");
					}
						
					System.out.println("");
				}
				System.out.println("");
			}

			if (elem.equals("Up")) {
				middleNode = instance.moveUp(middleNode);
				for (int row = 0; row < 3; row++) {
					System.out.print("                                    ");
					for (int column = 0; column < 3; column++){
						if(middleNode.configuration[row][column] == 9) {
							System.out.print("* ");
							continue;
						}
						System.out.print(middleNode.configuration[row][column] + " ");
					}
					System.out.println("");
				}
				System.out.println("");
			}

			if (elem.equals("Down")) {
				middleNode = instance.moveDown(middleNode);
				for (int row = 0; row < 3; row++) {
					System.out.print("                                    ");
					for (int column = 0; column < 3; column++){
						if(middleNode.configuration[row][column] == 9) {
							System.out.print("* ");
							continue;
						}
						System.out.print(middleNode.configuration[row][column] + " ");
					}
						
					System.out.println("");
				}
				System.out.println("");
			}

		}
		
		System.out.println("--------------------------the number of Node been expanded is:" + instance.number + "----------------");
		System.out.println("---------------------------------CPU execution time :" + (endTime - startTime) + "ms------------------------");
	}
		System.out.println("You can continue to evaluate testcase. For case 1, type in 1; For case 2 type in 2; For case 3, type in 3.");
		
}// end of while(hasNext())
	scan.close();
		
  }// end of main()

} //end of class
