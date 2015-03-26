package simpleBathroom;

public class Repairman extends Person{
	

	public Repairman(){
		super();
		type = "R";
	}
	
	public void entryProtocol() throws InterruptedException{
		Program.firstR.acquire();
		
		//provera uslova da li je njegova grupa
		Program.mutex.acquire();
		if (Program.cntF>0 || Program.cntM>0){
			Program.waitR++;
			Program.mutex.release();
			changeState("ceka da se kupatilo isprazni");
			Program.groupA.acquire();
			Program.mutex.acquire();
			Program.waitR--;
		}
			
		//provera uslova da li ima nekoga (mora da udje sam)
		if (Program.cntR==1){
			Program.waitR++;
			Program.mutex.release();
			changeState("ceka da se napravi mesta u kupatilu jer je unutra drugi domar");
			Program.secondR.acquire();
			Program.mutex.acquire();
			Program.waitR--;
		}
		
		Program.cntR++;
		changeState("zavrsene provere");
		Program.mutex.release();
		
		//pusta sledeceg iz svoje grupe
		Program.firstR.release();
		
		
	}
	
	
	public void exitProtocol() throws InterruptedException{
		Program.mutex.acquire();
		Program.cntR--;
		changeState("izasao");
		
		//ako jos neko ceka
		if (Program.waitR>0) {
			Program.secondR.release();
		}
		
		//ako je poslednji
		if ((Program.waitR == 0)&&(Program.waitF>0||Program.waitM>0)){
			Program.groupA.release();
		}
		Program.mutex.release();
	}	
}