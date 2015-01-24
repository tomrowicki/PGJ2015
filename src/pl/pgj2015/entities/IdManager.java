package pl.pgj2015.entities;

public class IdManager {
	private static long NEXT_ID = 0;
	
	public static long getNextId(){
		synchronized (IdManager.class) {
			return NEXT_ID++;
		}
	}
}
