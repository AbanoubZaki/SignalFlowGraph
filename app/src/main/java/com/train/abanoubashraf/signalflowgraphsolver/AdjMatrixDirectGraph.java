package com.train.abanoubashraf.signalflowgraphsolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdjMatrixDirectGraph<T> extends Graph<T> implements IGraph<T> {
	public ArrayList<Vertex<T>> vertexes;
	public double[][] adjMatrix;

	public void addVertex(Vertex<T> n) {
		vertexes.add(n);
	}

	public AdjMatrixDirectGraph () {
		vertexes = new ArrayList<Vertex<T>>();
	}
	public boolean isVertexExist(Vertex<T> vertex) {
		return (vertexes.indexOf(vertex) >= 0);
	}

	public List<Vertex<T>> getVertexes() {
		return vertexes;
	}

	// utility function to check if current 
	// vertex is already present in path 
	public boolean isNotVisited(Vertex<T> x, ArrayList<Vertex<T>> path) 
	{ 
	    int size = path.size(); 
	    for (int i = 0; i < size; i++)  
	        if (path.get(i).equals(x))  
	            return false;  
	    return true; 
	} 
	
	// utility function for finding paths in graph
	// from source to destination
	public ArrayList< ArrayList<Vertex<T>>> findpaths(Vertex<T> start, Vertex<T> end) {
		ArrayList< ArrayList<Vertex<T>>> forwardPaths = new ArrayList<>();
		ArrayList< ArrayList<Vertex<T>>> q = new ArrayList<>();
		ArrayList<Vertex<T>> path = new ArrayList<>();
		path.add(start);
		q.add(path);
		while (!q.isEmpty()) {
			path = q.get(0);
			q.remove(0);
			Vertex<T> last = path.get(path.size()-1);
			
			if (last.equals(end)) {
				forwardPaths.add(path);
			}
			
			for (int i = 0; i < getNeighbors(last).size(); i++) { 
	            if (isNotVisited(getNeighbors(last).get(i), path)) { 
	            	ArrayList<Vertex<T>> newPath = new ArrayList<>();
	            	for (int j = 0; j < path.size(); j++) {
	            		newPath.add(path.get(j));
	            	}
	                newPath.add(getNeighbors(last).get(i)); 
	                q.add(newPath); 
	            } 
	        }
		}
		return forwardPaths;
	}

	// This method will be called to make connect two nodes
	public void addEdge(Vertex<T> start, Vertex<T> end, double weight) {
		if (!isVertexExist(start))
			addVertex(start);
		if (!isVertexExist(end))
			addVertex(end);

		if (initAdjMatrix()) {
			int startIndex = vertexes.indexOf(start);
			int endIndex = vertexes.indexOf(end);
			if (startIndex >= 0 && endIndex >= 0) {
				adjMatrix[startIndex][endIndex] = weight;
			}
		}
	}

	public boolean removeEdge(Vertex<T> start, Vertex<T> end) {
        if (!isVertexExist(start))
            return false;
        if (!isVertexExist(end))
            return false;

        int startIndex = vertexes.indexOf(start);
        int endIndex = vertexes.indexOf(end);
        if (startIndex >= 0 && endIndex >= 0) {
            adjMatrix[startIndex][endIndex] = 0;
            return true;
        }
        return false;
	}

	public boolean clearGraph () {
	    vertexes.clear();
	    return initAdjMatrix();
    }

	public boolean initAdjMatrix() {
		if (vertexes.size() <= 0)
			return false;
		adjMatrix = buildAdjMatrix(adjMatrix, vertexes.size());
		return true;
	}

	private double[][] buildAdjMatrix(double[][] prevMatrix, int newSize) {
		if (newSize == 0)
			return null;
		double[][] matrix = new double[newSize][newSize];

		for (int i = 0; i < newSize; i++)
			for (int j = 0; j < newSize; j++)
				matrix[i][j] = 0;

		if (prevMatrix != null) {
			for (int i = 0; i < prevMatrix.length; i++)
				for (int j = 0; j < prevMatrix[i].length; j++)
					matrix[i][j] = prevMatrix[i][j];
		}
		return matrix;
	}

	public List<Vertex<T>> getNeighbors(Vertex<T> n) {
		List<Vertex<T>> list = new ArrayList<Vertex<T>>();
		int index = vertexes.indexOf(n);
		for (int j = 0; j < vertexes.size(); j++) {
			if (adjMatrix[index][j] != 0) {
				list.add((Vertex<T>) vertexes.get(j));
			}
		}
		return list;
	}

	public Vertex<T> getUnvisitedChildren(Vertex<T> n) {

		int index = vertexes.indexOf(n);
		int j = 0;
		while (j < vertexes.size()) {
			if (adjMatrix[index][j] != 0 && vertexes.get(j).visited == false) {
				return (Vertex<T>) vertexes.get(j);
			}
			j++;
		}
		return null;
	}

	public List<WeightedEdge<T>> getEdges(Vertex<T> n) {

		List<WeightedEdge<T>> edges = new LinkedList<WeightedEdge<T>>();
		int index = vertexes.indexOf(n);
		int j = 0;
		while (j < vertexes.size()) {
			if (adjMatrix[index][j] != 0) {
				edges.add(new WeightedEdge<T>(n, vertexes.get(j), adjMatrix[index][j]));
			}
			j++;
		}
		return edges;
	}

	public List<WeightedEdge<T>> getEdges() {
		List<WeightedEdge<T>> edges = new LinkedList<WeightedEdge<T>>();
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				if (adjMatrix[i][j] != 0) {
					edges.add(new WeightedEdge<T>(vertexes.get(i), vertexes.get(j), adjMatrix[i][j]));
				}
			}
		}
		return edges;
	}

	public void printVertexes() {
		for (int i = 0; i < vertexes.size(); i++) {
			System.out.print(vertexes.get(i) + " ");
		}
		System.out.println();
	}

	public double getEdgeGain (Vertex<T> a, Vertex<T> b) {
		return adjMatrix[vertexes.indexOf(a)][vertexes.indexOf(b)];
		
	}
	
	// Utility methods for clearing visited property of node
	public void clearNodes() {
		int i = 0;
		while (i < vertexes.size()) {
			vertexes.get(i).visited = false;
			i++;
		}
	}

}