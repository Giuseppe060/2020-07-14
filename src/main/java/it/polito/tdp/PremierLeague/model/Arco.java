package it.polito.tdp.PremierLeague.model;

public class Arco implements Comparable<Arco>{
	
	Team t;
	int punti;
	
	
	public Arco(Team t, int punti) {
		super();
		this.t = t;
		this.punti = punti;
	}


	public Team getT() {
		return t;
	}


	public void setT(Team t) {
		this.t = t;
	}


	public int getPunti() {
		return punti;
	}


	public void setPunti(int punti) {
		this.punti = punti;
	}


	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return this.punti-o.punti;
	}


	@Override
	public String toString() {
		return t.name+"("+punti+")"+"\n";
	}
	
	
	
	

}
