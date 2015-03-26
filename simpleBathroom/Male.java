package simpleBathroom;

public class Male extends Person{
	

	public Male(){
		super();
		type = "M";
	}
	
	public void entryProtocol() throws InterruptedException{
		Program.firstM.acquire();
		
		//provera uslova da li je njegova grupa
		Program.mutex.acquire();
		if (Program.cntF>0 || Program.cntR>0){
			Program.waitM++;
			Program.mutex.release();
			changeState("ceka da se kupatilo isprazni");
			Program.groupA.acquire();
			Program.mutex.acquire();
			Program.waitM--;
		}
			
		//provera uslova da li ima mesta
		if (Program.N==(Program.cntM+Program.cntC)){
			Program.waitM++;
			Program.mutex.release();
			changeState("ceka da se napravi mesta u kupatilu");
			Program.secondM.acquire();
			Program.mutex.acquire();
			Program.waitM--;
		}
		
		//ako neko ceka da izadje zbog dece pusti ga
		
		Program.cntM++;
		changeState("zavrsene provere");
		Program.mutex.release();
		
		//pusta sledeceg iz svoje grupe
		Program.firstM.release();
		
		
	}
	
	
	public void exitProtocol() throws InterruptedException{
		Program.mutex.acquire();
		Program.cntM--;
		changeState("izasao");
		//ako ima dece i poslednji sam cekaj
		
		//ako jos neko ceka
		if ((Program.waitC>0 && Program.waitM>0 && Program.childTurn) || (Program.waitC>0 && Program.waitM==0)){
			Program.secondC.release();
			Program.childTurn = false;
		}else if (Program.waitM>0){
			Program.secondM.release();
			if (Program.childTurn) Program.childTurn = true;
		}
		
		//ako je poslednji i niko od njegovih ne ceka pusti drugu grupu ako neko ceka tamo
		if ((Program.cntM == 0)&&(Program.waitM == 0)&&(Program.waitF>0||Program.waitR>0)){
			Program.groupA.release();
		}
		Program.mutex.release();
	}
	
}