package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;
import java.util.*;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {

	private List<Team> allTeam;
	private List<Match> allMatches;
	private int N;
	private int X;
	private List<TeamN> teamN;
	private Map<Integer,Team> idMap;
	private List<TeamPunti> allTeamPunti;
	
	private int mediaReporter;
	private int critici;
	
	private PriorityQueue<Evento> queue;
	
	public Simulatore(List<Team> allTeam, List<Match> allMatches, int N, int X, Map<Integer,Team> idMap, List<TeamPunti> allTeamPunti) {
		this.allTeam=allTeam;
		this.allMatches=allMatches;
		this.N=N;
		this.X=X;
		this.teamN=new ArrayList<>();
		for(Team t : allTeam) {
			this.teamN.add(new TeamN(t,N));
		}
		this.idMap=idMap;
		this.allTeamPunti=allTeamPunti;
		
		this.mediaReporter=0;
		this.critici=0;
		
		this.queue=new PriorityQueue<>();
	}
	
	public void initialize() {
		for(Match m : allMatches) {
			if(m.getResultOfTeamHome()==+1) {
				this.queue.add(new Evento(idMap.get(m.getTeamHomeID()),idMap.get(m.getTeamAwayID()),m.getDate(),EventType.VITTORIA));
			}
			else if(m.getResultOfTeamHome()==0) {
				this.queue.add(new Evento(idMap.get(m.getTeamHomeID()),idMap.get(m.getTeamAwayID()),m.getDate(),EventType.PAREGGIO));
			}
			else if(m.getResultOfTeamHome()==-1) {
				this.queue.add(new Evento(idMap.get(m.getTeamHomeID()),idMap.get(m.getTeamAwayID()),m.getDate(),EventType.SCONFITTA));
			}
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			Team home = e.getT1();
			Team away = e.getT2();
			LocalDateTime data = e.getData();
			EventType type = e.getType();
//			System.out.println(home+" "+away+" "+data+" "+type+"\n");
			int n = 0;
			for(TeamN t : this.teamN) {
				if(t.getTeam().getTeamID()==home.teamID) {
					this.mediaReporter+=t.getN();
					n+=t.getN();
				}
			}
			for(TeamN t : this.teamN) {
				if(t.getTeam().getTeamID()==away.teamID) {
					this.mediaReporter+=t.getN();
					n+=t.getN();
				}
			}
			if(n<X) {
				this.critici++;
			}
			
			switch(type) {
			case VITTORIA:
				double prob1V = Math.random();
				double prob2V = Math.random();
				if(prob1V<0.5) {
					for(TeamN t : this.teamN) {
						if(home.teamID==t.getTeam().teamID && t.getN()>0) {
							t.setN(t.getN()-1);
						}
					}
					double probC = Math.random()*this.getMigliori(home).size();
					if(probC==0) {
						for(TeamN t : this.teamN) {
							if(home.teamID==t.getTeam().teamID && t.getN()>0) {
								t.setN(t.getN()+1);
							}
						}
					}
					else {
						Team casuale = this.getMigliori(home).get((int) probC).getTeam();
						for(TeamN t : this.teamN) {
							if(casuale.teamID==t.getTeam().teamID) {
								t.setN(t.getN()+1);
							}
						}
					}
				}
				if(prob2V<0.2) {
					double bocciati = 0;
					for(TeamN t : this.teamN) {
						if(away.teamID==t.getTeam().teamID && t.getN()>0) {
							bocciati = Math.random()*t.getN();
							t.setN((int) (t.getN()-bocciati));
						}
					}
					double probC = Math.random()*this.getPeggiori(away).size();
					if(probC==0) {
						for(TeamN t : this.teamN) {
							if(away.teamID==t.getTeam().teamID && t.getN()>0) {
								bocciati = Math.random()*t.getN();
								t.setN((int) (t.getN()+bocciati));
							}
						}
					}
					else {
						Team casuale = this.getPeggiori(away).get((int) probC).getTeam();
						for(TeamN t : this.teamN) {
							if(casuale.teamID==t.getTeam().teamID) {
								t.setN((int) (t.getN()+bocciati));
							}
						}
					}
				}
				break;
				
			case SCONFITTA:
				double prob1S = Math.random();
				double prob2S = Math.random();
				if(prob1S<0.5) {
					for(TeamN t : this.teamN) {
						if(away.teamID==t.getTeam().teamID && t.getN()>0) {
							t.setN(t.getN()-1);
						}
					}
					double probC = Math.random()*this.getMigliori(away).size();
					if(probC==0) {
						for(TeamN t : this.teamN) {
							if(away.teamID==t.getTeam().teamID && t.getN()>0) {
								t.setN(t.getN()+1);
							}
						}
					}
					else {
						Team casuale = this.getMigliori(away).get((int) probC).getTeam();
						for(TeamN t : this.teamN) {
							if(casuale.teamID==t.getTeam().teamID) {
								t.setN(t.getN()+1);
							}
						}
					}
				}
				if(prob2S<0.2) {
					double bocciati = 0;
					for(TeamN t : this.teamN) {
						if(home.teamID==t.getTeam().teamID && t.getN()>0) {
							bocciati = Math.random()*t.getN();
							t.setN((int) (t.getN()-bocciati));
						}
					}
					double probC = Math.random()*this.getPeggiori(home).size();
					if(probC==0) {
						for(TeamN t : this.teamN) {
							if(home.teamID==t.getTeam().teamID && t.getN()>0) {
								bocciati = Math.random()*t.getN();
								t.setN((int) (t.getN()+bocciati));
							}
						}
					}
					else {
						Team casuale = this.getPeggiori(home).get((int) probC).getTeam();
						for(TeamN t : this.teamN) {
							if(casuale.teamID==t.getTeam().teamID) {
								t.setN((int) (t.getN()+bocciati));
							}
						}
					}
				}
				break;
			
			case PAREGGIO:
				break;
			}
		}
	}
	
	public List<TeamPunti> getMigliori(Team team) {
		TeamPunti tp = null;
		for(TeamPunti t : this.allTeamPunti) {
			if(t.getTeam().getTeamID()==team.getTeamID()) {
				tp=t;
			}
		}
		List<TeamPunti> l = new ArrayList<>();
		for(TeamPunti t : this.allTeamPunti) {
			if(t.getPunti()>tp.getPunti()) {
				l.add(new TeamPunti(t.getTeam(),t.getPunti()-tp.getPunti()));
			}
		}
		l.sort(null);
		return l;
	}
	
	public List<TeamPunti> getPeggiori(Team team) {
		TeamPunti tp = null;
		for(TeamPunti t : this.allTeamPunti) {
			if(t.getTeam().getTeamID()==team.getTeamID()) {
				tp=t;
			}
		}
		List<TeamPunti> l = new ArrayList<>();
		for(TeamPunti t : this.allTeamPunti) {
			if(t.getPunti()<tp.getPunti()) {
				l.add(new TeamPunti(t.getTeam(),tp.getPunti()-t.getPunti()));
			}
		}
		l.sort(null);
		return l;
	}
	
	public double getMedia() {
		double media = this.mediaReporter/this.allMatches.size();
		return media;
	}
	
	public int getCritici() {
		return this.critici;
	}
}
