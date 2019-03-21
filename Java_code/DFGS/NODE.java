

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

enum ACTION {UP, DOWN, LEFT, RIGHT}
public class NODE
{
	// these members are created according to node definition in textbook
	private String state;
	private NODE parent;
	// make the actions a stack rather than a set to ensure the first generated child node will be popped out first
	private LinkedList<ACTION> actions = new LinkedList<ACTION>();
	private int path_cost;

	// constructor
	NODE(String state, NODE parent, int p)
	{
		this.state = state;
		this.parent = parent;
		this.path_cost = p;
		setAction();
	}
	
	// get and set methods, may not be used in DFGS
	public void setState(String str)
	{
		state = str;
	}
	
	public String getState()
	{
		return state;
	}
	
	public void setParent(NODE p)
	{
		parent = p;
	}
	
	public NODE getParent()
	{
		return parent;
	}
	
	public void setAction()
	{
		HashMap<Character, ACTION> possibleActions = new HashMap<Character, ACTION>();
		// find the position of "."
		int pos = state.indexOf('.');
		// check DOWN and RIGHT first, since the target pos of "." is in the right-bottom corner
		if(!(pos < 3))
		{
			possibleActions.put(state.charAt(pos - 3), ACTION.UP);
		}
		if(!(pos > 5))
		{
			possibleActions.put(state.charAt(pos + 3), ACTION.DOWN);
		}		
		if(pos != 0 && pos != 3 && pos != 6)
		{
			possibleActions.put(state.charAt(pos - 1), ACTION.LEFT);
		}
		if(pos != 2 && pos != 5 && pos != 8)
		{
			possibleActions.put(state.charAt(pos + 1), ACTION.RIGHT);			
		}
		
		while(possibleActions.isEmpty() == false)
		{
			Iterator<Character> itr = possibleActions.keySet().iterator();
			Character tempChar1 = itr.next();
			while(itr.hasNext())
			{
				Character tempChar2 = itr.next();
				if(Character.getNumericValue(tempChar1) < Character.getNumericValue(tempChar2))
				{
					tempChar1 = tempChar2;
				}
			}
			actions.addLast(possibleActions.remove(tempChar1));
		}
	}
	public LinkedList<ACTION> getAction()
	{
		return actions;
	}
	
	public void setPathCost()
	{
		path_cost = parent.path_cost + 1;
	}
	
	public int getPathCost()
	{
		return path_cost;
	}
}
