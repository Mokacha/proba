package simpleBathroom;

public class Child extends Person{
	

	public Child(){
		super();
		type = "C";
	}
	
	public void entryProtocol() throws InterruptedException{
		Program.firstC.acquire();
		
		//provera da li sme i ima mesta
		Program.mutex.acquire();
		while ((Program.cntM==0 && Program.cntF==0) || 		//nema roditelja ili nema nikoga
			(Program.cntM+Program.cntC==Program.N) || 		//nema mesta (ako su muskarci unutra)
			(Program.cntF+Program.cntC==Program.N))	{		//nema mesta (ako su zene unutra)
				Program.waitC++;
				Program.mutex.release();
				changeState("ceka da udje (ne sme ili nema mesta)");
				Program.secondC.acquire();
				Program.mutex.acquire();
				Program.waitC--;
		}
		
		Program.cntC++;
		changeState("zavrsene provere");
		Program.mutex.release();
		
		//pusta sledeceg iz svoje grupe
		Program.firstC.release();
		
	}
	
	
	public void exitProtocol() throws InterruptedException{
		
		Program.mutex.acquire();
		Program.cntC--;
		changeState("izasao");
		
		//ako neko ceka
		if ((Program.waitC>0 && Program.cntF>1) || (Program.waitC>0 && Program.cntM>1)){
			Program.secondC.release();
		}else if (Program.waitF>0 && Program.cntF>0 && Program.waitC==0){
			Program.secondF.release();
		}else if (Program.waitM>0 && Program.cntM>0 && Program.waitC==0){
			Program.secondM.release();
		}
		
		Program.mutex.release();
	}
	
}