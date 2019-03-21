

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

enum ACTION {UP, DOWN, LEFT, RIGHT}

public class NODE
{
	// these members are created according to node definition in textbook
	private String state;
	private NODE parent;
	private LinkedList<ACTION> actions = new LinkedList<ACTION>();
	private int gn;
	private int fn;

	// constructor
	NODE(String state, NODE parent, int gn)
	{
		this.state = state;
		this.parent = parent;
		this.gn = gn;
		this.fn = gn + manhattan(state);
		this.setAction();
	}
	
	// get hn
	private int manhattan(String state)
	{
        int count = 0;
        for (int i = 0; i < state.length(); i++)
        {
        	char tempChar = state.charAt(i);
        	if(tempChar != '.')
        	{
            	int value = Character.getNumericValue(tempChar);
            	count += Math.abs((value - 1)/3 - i/3) + Math.abs((value - 1)%3 - i%3);
        	}      		
        	if(tempChar == '.')
        	{
        		count += Math.abs(2 - i/3) + Math.abs(2 - i%3);
        	}        		
        }
        return count;
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
				if(Character.getNumericValue(tempChar1) > Character.getNumericValue(tempChar2))
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
	
	public int getGn()
	{
		return this.gn;
	}
	
	public int getFn()
	{
		return this.fn;
	}
}
