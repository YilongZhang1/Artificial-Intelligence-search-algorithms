

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;



public class AStarSearch
{
	private static LinkedList<NODE> fringe = new LinkedList<NODE>();
	private static Stack<String> PATHtoGoal = new Stack<String>();
	
	// to count # of node expanded
	private static int expandCnt = 0;
	
	public static void AStar_Search(String inputStr)
	{
		fringe.clear();
		// fringe = Insert(Make-Node(Initial-State[problem]), fringe));
		NODE n = new NODE(inputStr, null, 0);

		fringe.add(n);
		while (true)
		{
			// if fringe is empty then return failure
			if(fringe.isEmpty())
			{
				System.out.println("fringe is empty, failure!");
				break;	
			}
			Iterator<NODE> itr = fringe.iterator();
			int leastFn = itr.next().getFn();
			int leastIdx = 0;
			int travIdx = 0;
			while(itr.hasNext())
			{
				travIdx++;
				int travFn = itr.next().getFn();
				if(leastFn > travFn)
				{
					leastFn = travFn;
					leastIdx = travIdx;
				}
			}
			NODE tempNode = fringe.remove(leastIdx);
//			System.out.println("hn = " + (tempNode.getFn() - tempNode.getGn()));
			if(tempNode.getState().equals("12345678."))
			{
				System.out.println("the goal is FOUND!");
				System.out.println("# of moves from initial state to goal state is: " + tempNode.getFn());
				while(tempNode != null)
				{
					PATHtoGoal.push(tempNode.getState());
					tempNode = tempNode.getParent();
				}
				int tempCnt = 0;
				System.out.println("******************************************");
				System.out.println("******************************************");
				System.out.println("the solution goes like this...");
				while(PATHtoGoal.isEmpty() == false)
				{
					System.out.println("******* move # " + tempCnt++ + " *******");					
					String tempStr = PATHtoGoal.pop();
					System.out.println("\t\t" + tempStr.charAt(0) + " " + tempStr.charAt(1) + " " + tempStr.charAt(2) + 
							"\n\t\t" + tempStr.charAt(3) + " " + tempStr.charAt(4) + " " + tempStr.charAt(5) + 
							"\n\t\t" + tempStr.charAt(6) + " " + tempStr.charAt(7) + " " + tempStr.charAt(8));	
					if(tempCnt > 500)
					{
						System.out.println("*** Apologize I cannot show more since there are too many steps! ***");
						break;
					}
				}
				System.out.println("# of nodes expanded is " + expandCnt);
				break;
			}
			else
			{
				expandAndInsert(tempNode);
				expandCnt++;
				if(expandCnt <= 5)
				{
					System.out.println("state of node # " + expandCnt + " is: ");
					System.out.println("******************************************");
					System.out.println("\t\t"+tempNode.getState().charAt(0)+" "+tempNode.getState().charAt(1)+" "+
							tempNode.getState().charAt(2)+"\n\t\t"+tempNode.getState().charAt(3)+" "
							+tempNode.getState().charAt(4)+" "+tempNode.getState().charAt(5)+"\n\t\t"+
							tempNode.getState().charAt(6)+" "+tempNode.getState().charAt(7)+" "
							+tempNode.getState().charAt(8));
					System.out.println("******************************************");						
				}
			}
			if(expandCnt >= 100000)
			{
				System.out.println("time out since # of expanded node is greater than 100,000");
				break;
			}
		}
	}
	
	private static void expandAndInsert(NODE p)
	{	
		while(p.getAction().isEmpty() == false)
		{
			ACTION act = p.getAction().removeFirst();
			String childString = move(p.getState(), act);
			if(p.getParent() == null || childString.equals(p.getParent().getState()) == false)
			{
				NODE node = new NODE(childString, p, p.getGn() + 1);
				fringe.add(node);
			}
		}
	}

	
	// used in expand
	private static String move(final String s, ACTION act)
	{
	    String str = s;
	    int pos = s.indexOf('.');
	    switch(act)
	    {
	    	case UP:
	    	{
		        char a = str.charAt(pos - 3);
		        String newS = str.substring(0, pos) + a + str.substring(pos + 1);
		        str = newS.substring(0, (pos - 3)) + '.' + newS.substring(pos - 2);		        
		        break;
	    	}
	    	case DOWN:
	    	{
		        char a = str.charAt(pos + 3);
		        String newS = str.substring(0, pos) + a + str.substring(pos + 1);
		        str = newS.substring(0, (pos + 3)) + '.' + newS.substring(pos + 4);	 
		        break;
	    	}
	    	case LEFT:
	    	{
		        char a = str.charAt(pos - 1);
		        String newS = str.substring(0, pos) + a + str.substring(pos + 1);
		        str = newS.substring(0, (pos - 1)) + '.' + newS.substring(pos);	   
		        break;
	    	}
	    	case RIGHT:
	    	{
		        char a = str.charAt(pos + 1);
		        String newS = str.substring(0, pos) + a + str.substring(pos + 1);
		        str = newS.substring(0, (pos + 1)) + '.' + newS.substring(pos + 2);	    	
		        break;
	    	}
	    	default:
	    	{
	    		System.out.println("error in expanding node");
	    		break;
	    	}
	    }
	    return str;
	}
}
