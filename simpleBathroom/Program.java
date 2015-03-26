package simpleBathroom;

import java.util.concurrent.Semaphore;

public class Program{
	
	//////////////////////////////////////////
	//				SHARED DATA				//
	//////////////////////////////////////////
	
	//kapacitet kupatila
	public static int N = 2;
	
	//sinhronizacioni semafori
	public static Semaphore mutex = new Semaphore(1);		//medjusobno iskljucenje za deljene podatke
	public static Semaphore groupA = new Semaphore(0);		//uslovna sinhronizacija: Neodgovarajuca grupa za sve osim za decu
	public static Semaphore groupC = new Semaphore(0);		//uslovna sinhronizacija: Neodgovarajuca grupa za decu
	public static Semaphore firstM = new Semaphore(1);
	public static Semaphore firstF = new Semaphore(1);
	public static Semaphore firstC = new Semaphore(1);
	public static Semaphore secondM = new Semaphore(0);
	public static Semaphore secondF = new Semaphore(0);
	public static Semaphore secondC = new Semaphore(0);
	
	
	//broj Male, Frmale, Child, Repairman osoba koje su u kupatilu, respektivno
	public static int cntM = 0;
	public static int cntF = 0;
	public static int cntC = 0;
	public static int cntR = 0;
	
	//broj Male, Frmale, Child, Repairman osoba koje cekaju, respektivno
	public static int waitM = 0;
	public static int waitF = 0;
	public static int waitC = 0;
	public static int waitR = 0;
	
	//ukoliko dete i roditelj cekaju da udju ciji je red
	public static boolean childTurn = true;
	
	public static void main(String[] args){
		
		//broj blokova
		int rnds = 3;
		
		//broj instanciranih osoba u bloku
		int round [] = {5, 10, 15};
		
		//pauza izmedju blokova
		long timeout[] = {15,20};
		
		for (int rnd = 0; rnd<rnds; rnd++){
			System.out.println("============= BLOCK "+(rnd+1)+" START  ============");
			//kreiraj i startuj osobe
			for (int i=0; i<round[rnd]; i++){
				switch ((int)(Math.random()*2)){
				case 0:
					new Male();break;
				case 1:
					new Female();break;
				case 2:
			//		new Child();break;
				case 3:
			//		new Repairman();
				}
			}
			
			//timeout
			try {
				//ako nije poslednji blok
				if (rnd<rnds-1)
					Thread.sleep(timeout[rnd]*1000);
			} catch (InterruptedException e) {}
			System.out.println("============= BLOCK "+rnd+" FINISH ============");
		}
		
	}
	
}