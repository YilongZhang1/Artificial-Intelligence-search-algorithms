


import java.util.Scanner;



public class startHere
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("--------------Solve 8-puzzle with depth-first graph search--------------");
		System.out.println("Suppose your initial state is:");
		System.out.println("******************************************");
		System.out.print("\t\t. 1 3\n\t\t4 2 5\n\t\t7 8 6\n");
		System.out.println("******************************************");
		System.out.println("your input should be like \".13425786\"");
		System.out.println("******************************************");
		System.out.println("Please input a string which represents the initial state");  
		String inputStr = scan.next();

		if(inputStr.length() == 9 && inputStr.contains("1") && inputStr.contains("2") && inputStr.contains("3") &&
				inputStr.contains("4") && inputStr.contains("5") && inputStr.contains("6") && inputStr.contains("7")
				&& inputStr.contains("8") && inputStr.contains("."))
		{
			long startTime = System.currentTimeMillis();
			DFGS.depthFirstGraphSearch(inputStr);
			long endTime = System.currentTimeMillis();
			System.out.println("time used is " + (endTime - startTime) + " ms");			
		}
		else
		{
			System.out.println("Hey! Careful about the format and try again!");
		}
		scan.close();
	}
}
