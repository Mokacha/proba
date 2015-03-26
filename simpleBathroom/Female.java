package simpleBathroom;

public class Female extends Person{
	

	public Female(){
		super();
		type = "F";
	}
	
	public void entryProtocol() throws InterruptedException{
		Program.firstF.acquire();
		
		//provera uslova da li je njena grupa
		Program.mutex.acquire();
		if (Program.cntM>0 || Program.cntR>0){
			Program.waitF++;
			Program.mutex.release();
			changeState("ceka da se kupatilo isprazni");
			Program.groupA.acquire();
			Program.mutex.acquire();
			Program.waitF--;
		}
			
		//provera uslova da li ima mesta
		if (Program.N==(Program.cntF+Program.cntC)){
			Program.waitF++;
			Program.mutex.release();
			changeState("ceka da se napravi mesta u kupatilu");
			Program.secondF.acquire();
			Program.mutex.acquire();
			Program.waitF--;
		}
		
		//ako neko ceka da izadje zbog dece pusti ga
		
		Program.cntF++;
		changeState("zavrsene provere");
		Program.mutex.release();
		
		//pusta sledeceg iz svoje grupe
		Program.firstF.release();
		
		
	}
	
	
	public void exitProtocol() throws InterruptedException{
		
		Program.mutex.acquire();
		Program.cntF--;
		changeState("izasao");
		
		//ako ima dece i poslednji sam cekaj
		
		//ako jos neko ceka
		if ((Program.waitC>0 && Program.waitF>0 && Program.childTurn) || (Program.waitC>0 && Program.waitF==0)){
			Program.secondC.release();
			Program.childTurn = false;
		}else if (Program.waitF>0){
			Program.secondF.release();
			if (Program.childTurn) Program.childTurn = true;
		}
		
		//ako je poslednji
		if ((Program.cntF == 0)&&(Program.waitM>0||Program.waitR>0)){
			Program.groupA.release();
		}
		Program.mutex.release();
	}
	
}