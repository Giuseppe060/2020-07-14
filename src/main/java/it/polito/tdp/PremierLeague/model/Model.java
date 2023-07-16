package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	
	Graph<Team,DefaultWeightedEdge> grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	PremierLeagueDAO dao = new PremierLeagueDAO();
	
	
	public void creaGrafo() {
		
		List<Team> vertici = dao.listAllTeams();
		
		Graphs.addAllVertices(grafo, vertici);
		
		Map<Team,Integer> mappaPunti = new HashMap<>();
		for(Team t : dao.listAllTeams()) {
			int puntiTotali = PuntiInCasa(t.teamID)+PuntiInTrasferta(t.teamID);
			mappaPunti.put(t, puntiTotali);
		}
		
		for(Team t1 : grafo.vertexSet()) {
			for(Team t2 : grafo.vertexSet()) {
				
				if( !t1.equals(t2) ) {
					
					int peso = Math.abs(mappaPunti.get(t1)-mappaPunti.get(t2));
					
					if(peso != 0 && mappaPunti.get(t1)>mappaPunti.get(t2)) {
						Graphs.addEdge(grafo, t1, t2, peso);
					}else if( peso != 0 && mappaPunti.get(t2)>mappaPunti.get(t1)) {
						Graphs.addEdge(grafo, t2, t1, peso);
					}
				}
			}
		}
		
		
		
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	public int nArchi() {
		return grafo.edgeSet().size();
	}
	
	public int PuntiInCasa(int codSquadra) {
		int totale = 0;
		for(Integer i : dao.listaRisultatiInCasa(codSquadra)) {
			if(i==1) {
				totale+=3;
			}else if(i==0) {
				totale+=1;
			}else if(i== -1) {
				totale+=0;
			}
		}
		return totale;
	}
	
	public int PuntiInTrasferta(int codSquadra) {
		int totale = 0;
		for(Integer i : dao.listaRisultatiInTrasferta(codSquadra)) {
			if(i== -1) {
				totale+=3;
			}else if(i==0) {
				totale+=1;
			}else if(i == 1) {
				totale+=0;
			}
		}
		return totale;
	}
	
	public List<Arco> SquadrePeggiori(int codSquadra){
		
		int puntiS = PuntiInCasa(codSquadra)+PuntiInTrasferta(codSquadra);
		List<Arco> lista = new ArrayList<>();
		
		for(Team t : grafo.vertexSet()) {
			int puntiT = PuntiInCasa(t.getTeamID())+PuntiInTrasferta(t.getTeamID());
			if(puntiT < puntiS) {
				Arco a = new Arco (t,puntiS-puntiT);
				lista.add(a);
			}
		}
		return lista;
	}
	
    public List<Arco> SquadreMigliori(int codSquadra){
		
		int puntiS = PuntiInCasa(codSquadra)+PuntiInTrasferta(codSquadra);
		List<Arco> lista = new ArrayList<>();
		
		for(Team t : grafo.vertexSet()) {
			int puntiT = PuntiInCasa(t.getTeamID())+PuntiInTrasferta(t.getTeamID());
			if(puntiT > puntiS) {
				Arco a = new Arco (t,puntiT-puntiS);
				lista.add(a);
			}
		}
		return lista;
	}
	
}
