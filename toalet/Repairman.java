package toalet;

public class Repairman extends Thread{
	
	private static int lastID = 0;
	private int ID = lastID++;
	private int maxTime = 5;
	private String state;
	
	private void printMe(){
		System.out.println(this);
	}
	
	public Repairman(){
		start();
	}
	
	public void run(){
		
		try{
			
			state = "pocinje";
			printMe();
			
			//entry protocol
			
			sleep((long)(Math.random()*maxTime));
			
			//exit protocol
			state = "zavrsio";
			printMe();
			
		}catch (InterruptedException e){}
		
	}
	
	public String toString(){
		return ID + "R: " + state;
	}
	
}