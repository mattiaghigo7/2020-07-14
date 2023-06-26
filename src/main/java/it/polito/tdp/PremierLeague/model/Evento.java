package it.polito.tdp.PremierLeague.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{

	public enum EventType{
		VITTORIA,
		SCONFITTA,
		PAREGGIO
	}
	
	private Team t1;
	private Team t2;
	private LocalDateTime data;
	private EventType type;

	public Evento(Team t1, Team t2, LocalDateTime data, EventType type) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.data = data;
		this.type = type;
	}

	public Team getT1() {
		return t1;
	}

	public void setT1(Team t1) {
		this.t1 = t1;
	}

	public Team getT2() {
		return t2;
	}

	public void setT2(Team t2) {
		this.t2 = t2;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Evento o) {
		return this.data.compareTo(o.data);
	}

}
