package datastoragehandler;

import java.util.List;

import model.Kiss;

public interface IDataHandlerKisses 
{
	public void addKiss(Kiss kiss);
	public List<Kiss> getAllKisses();
	public int updateKiss(Kiss kiss);
	public void deleteKiss(Kiss kiss);
	public List<Kiss> queryKissesDoneStatus(boolean status);
}
