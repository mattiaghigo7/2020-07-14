package it.polito.tdp.PremierLeague.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	PremierLeagueDAO dao;
	
	private List<Team> allTeam;
	private Map<Integer,Team> teamMap;
	private List<TeamPunti> allTeamPunti;
	private List<Match> allMatches;
	
	private Graph<Team,DefaultWeightedEdge> grafo;
	private List<Coppia> archi;
	
	private Simulatore sim;
	
	public Model() {
		this.dao=new PremierLeagueDAO();
		this.allTeam=new ArrayList<>(dao.listAllTeams());
		this.teamMap=new HashMap<>();
		for(Team t : this.allTeam) {
			this.teamMap.put(t.getTeamID(), t);
		}
		this.allTeamPunti=new ArrayList<>();
		this.allMatches=new ArrayList<>(dao.listAllMatches());
		for(Team t : allTeam) {
			int i = 0;
			for(Match m : allMatches) {
				if(t.getTeamID()==m.getTeamHomeID() && m.getResultOfTeamHome()==1) {
					i+=3;
				}
				else if(t.getTeamID()==m.getTeamAwayID() && m.getResultOfTeamHome()==-1) {
					i+=3;
				}
				else if((t.getTeamID()==m.getTeamHomeID() || t.getTeamID()==m.getTeamAwayID()) && m.getResultOfTeamHome()==0) {
					i++;
				}
			}
			this.allTeamPunti.add(new TeamPunti(t,i));
		}
	}
	
	public void creaGrafo() {
		this.grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.allTeam);
//		System.out.println(this.grafo.vertexSet().size());

		this.archi=new ArrayList<>();
		for(TeamPunti t1 : this.allTeamPunti) {
//			System.out.println(t1.getTeam()+" "+t1.getPunti());
			for(TeamPunti t2 : this.allTeamPunti) {
				if(t1.getPunti()>t2.getPunti()) {
					this.archi.add(new Coppia(t1.getTeam(),t2.getTeam(),t1.getPunti()-t2.getPunti()));
				}
				else if(t1.getPunti()<t2.getPunti()) {
					this.archi.add(new Coppia(t2.getTeam(),t1.getTeam(),t2.getPunti()-t1.getPunti()));
				}
			}
		}
		for(Coppia c : this.archi) {
//			System.out.println(c);
			Graphs.addEdgeWithVertices(this.grafo, c.getT1(), c.getT2(), c.getN());
		}
//		System.out.println(this.grafo.edgeSet().size());
	}
	
	public Integer getVerticiSize() {
		return grafo.vertexSet().size();
	}
	
	public Integer getArchiSize() {
		return grafo.edgeSet().size();
	}

	public List<Team> getAllTeam() {
		this.allTeam.sort(null);
		return allTeam;
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
	
	public TeamPunti getTP(Team team) {
		TeamPunti tp = null;
		for(TeamPunti t : this.allTeamPunti) {
			if(t.getTeam().getTeamID()==team.getTeamID()) {
				tp=t;
			}
		}
		return tp;
	}
	
	public void simula(int N, int X){
		sim = new Simulatore(this.allTeam, this.allMatches, N, X, this.teamMap, this.allTeamPunti);
		sim.initialize();
		sim.run();
	}
	
	public double getMedia() {
		return sim.getMedia();
	}
	
	public int getCritici() {
		return sim.getCritici();
	}
}
