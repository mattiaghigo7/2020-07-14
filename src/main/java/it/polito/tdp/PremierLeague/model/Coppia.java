package it.polito.tdp.PremierLeague.model;

import java.util.Objects;

public class Coppia {
	
	private Team t1;
	private Team t2;
	private Integer n;
	
	public Coppia(Team t1, Team t2, Integer n) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.n = n;
	}

	public Team getT1() {
		return t1;
	}

	public Team getT2() {
		return t2;
	}

	public Integer getN() {
		return n;
	}

	@Override
	public int hashCode() {
		return Objects.hash(n, t1, t2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coppia other = (Coppia) obj;
		return Objects.equals(n, other.n) && Objects.equals(t1, other.t1) && Objects.equals(t2, other.t2);
	}

	@Override
	public String toString() {
		return "t1=" + t1 + ", t2=" + t2 + ", n=" + n;
	}
	
	
}
