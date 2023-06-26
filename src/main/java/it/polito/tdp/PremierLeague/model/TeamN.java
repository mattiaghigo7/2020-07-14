package it.polito.tdp.PremierLeague.model;

import java.util.Objects;

public class TeamN {
	
	private Team team;
	private int N;
	
	public TeamN(Team team, int n) {
		super();
		this.team = team;
		N = n;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	@Override
	public int hashCode() {
		return Objects.hash(N, team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamN other = (TeamN) obj;
		return N == other.N && Objects.equals(team, other.team);
	}
	
	
}
