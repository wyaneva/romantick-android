package model;

public class Action extends ToDoBase 
{
	private static final long serialVersionUID = 1L;

	//Override
	@Override
	public String toString() 
	{
		return getSummary();
	}
}
