package it.polito.tdp.PremierLeague.model;

import java.util.Objects;

public class TeamPunti implements Comparable<TeamPunti>{
	private Team team;
	private Integer punti;
	
	public TeamPunti(Team team, Integer punti) {
		super();
		this.team = team;
		this.punti = punti;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getPunti() {
		return punti;
	}

	public void setPunti(Integer punti) {
		this.punti = punti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(punti, team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamPunti other = (TeamPunti) obj;
		return Objects.equals(punti, other.punti) && Objects.equals(team, other.team);
	}

	@Override
	public String toString() {
		return "team=" + team + ", punti=" + punti;
	}

	@Override
	public int compareTo(TeamPunti o) {
		return this.punti-o.punti;
	}
	
	
}
