package pl.pgj2015.entities;

public enum IdManager {
	INSTANCE;
	
	private static long NEXT_ID = 0;
	
	public long getNextId(){
		synchronized (this) {
			return NEXT_ID++;
		}
	}
}
