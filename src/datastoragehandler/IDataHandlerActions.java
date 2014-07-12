package datastoragehandler;

import java.io.Serializable;
import java.util.List;

import model.Action;

public interface IDataHandlerActions extends Serializable 
{
	public void addAction(Action action);
	public List<Action> getAllActions();
	public int updateAction(Action action);
	public void deleteAction(Action action);
}
