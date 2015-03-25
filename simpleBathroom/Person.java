package simpleBathroom;

public abstract class Person  extends Thread{
	
	private static int lastID = 0;
	private int ID = lastID++;
	private int maxTime = 5;
	private String state;
	protected String type;
	
		public Person(){
		start();
	}
		
	private void printMe(){
		System.out.println(this);
	}

	protected void changeState(String newState){
		state = newState;
		printMe();
	}	
	
	protected abstract void entryProtocol();
	protected abstract void exitProtocol();
	
	public void run(){
		
		try{
			
			entryProtocol();
			
			//entry protocol
			
			changeState("kupa se");
			sleep((long)(Math.random()*maxTime));
			
			exitProtocol();
			
		}catch (InterruptedException e){}
		
	}
	
	public String toString(){
		return ID + type + ": " + state;
	}
	
}