package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph <String, DefaultWeightedEdge> grafo;

	public Model() {
		
		dao =  new NYCDao();
	}
	
	public List<String> getAllProvider(){
		return this.dao.getAllProvider();
	}
	
	public void creaGrafo(String p)
	{
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(p));
		
		for(Adiacenze a:this.dao.getAdiacenze(p))
		{
			Graphs.addEdgeWithVertices(this.grafo, a.getC1(), a.getC2(), a.getDistance());
		}
	}
	
	public int getNVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int getNArchi()
	{
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getQuartieri(String p)
	{
		return this.dao.getVertici(p);
	}
	
	public List<Vicini> getVicini(String c)
	{
		List<String> vicini = Graphs.neighborListOf(this.grafo, c);
		List<Vicini> result = new ArrayList<Vicini>();
		for(String s: vicini)
		{
			DefaultWeightedEdge de = this.grafo.getEdge(c, s);
			double peso = this.grafo.getEdgeWeight(de);
			result.add(new Vicini(s,peso));
		}
		
		Collections.sort(result, new Comparator<Vicini>()
		{

			@Override
			public int compare(Vicini o1, Vicini o2) 
			{
				return o1.getDistance().compareTo(o2.getDistance());
			}
			
		});
		
	
		return result;
		
	}
	
	
}
