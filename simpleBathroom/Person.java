package simpleBathroom;

public abstract class Person  extends Thread{
	
	private static int lastID = 1;
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
	
	protected abstract void entryProtocol() throws InterruptedException;
	protected abstract void exitProtocol() throws InterruptedException;
	
	public void run(){
		
		try{
			
			entryProtocol();
			
			//entry protocol
			
			Program.mutex.acquire();
			changeState("ulazi i kupa se, u kupatilu (M,F,C,R)("+Program.cntM+","+Program.cntF+","+Program.cntC+","+Program.cntR+")");
			Program.mutex.release();
			
			sleep((long)(Math.random()*maxTime*1000));
			changeState("okupao se i izlazi");
			
			exitProtocol();
			
		}catch (InterruptedException e){}
		
	}
	
	public String toString(){
		return ID + type + ": " + state;
	}
	
}