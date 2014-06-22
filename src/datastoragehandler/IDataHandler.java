package datastoragehandler;

import java.io.Serializable;
import java.util.List;

import model.Action;

public interface IDataHandler extends Serializable 
{
	public void addAction(Action action);
	public List<Action> getAllActions();
	public void updateAction(Action action);
	public void deleteAction(Action action);
}
