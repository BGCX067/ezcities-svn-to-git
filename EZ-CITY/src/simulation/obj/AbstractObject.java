package simulation.obj;

public abstract class AbstractObject {
	
	protected int ID; // reserved
	protected int Type; 	
	
	public static int INVALID_VALUE = -999999999;
	public static int MAX_INT = Integer.MAX_VALUE;
	public static int MIN_INT = Integer.MIN_VALUE;
	
	public final static int BUILDING = 0;
	public final static int CELL = 1;
	public final static int NODE = 2;
	public final static int LINK = 3;
	
	public int getID(){
		return ID;
	}
	
	public int getType(){
		return Type;
	}
	
	abstract public boolean isValid();
    abstract public void setInvalid();
    

}
