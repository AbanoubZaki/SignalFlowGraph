package com.train.abanoubashraf.signalflowgraphsolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DirectedGraph<T> {
		List<Edge<T>> edges = new ArrayList<Edge<T>>();
		List<Vertex<T>> vetexes = new ArrayList<Vertex<T>>();

		public DirectedGraph<T> addVetex(Vertex<T> n) {
			vetexes.add(n);
			return this;
		}

		public boolean isVetexExist(Vertex<T> vetex) {
			return (vetexes.indexOf(vetex) >= 0);
		}

		public boolean isAllEdgeVisited() {
			for (Edge<T> e : edges) {
				if (!e.visited)
					return false;
			}
			return true;
		}

		// This method will be called to make connect two nodes
		public DirectedGraph<T> addEdge(Vertex<T> start, Vertex<T> end, int weight) {
			if (!isVetexExist(start))
				addVetex(start);
			if (!isVetexExist(end))
				addVetex(end);

			Edge<T> edge = new Edge<T>(start, end);
			edges.add(edge);
			return this;
		}

		// This method will be called to make connect two nodes
		public DirectedGraph<T> addEdge(Vertex<T> start, Vertex<T> end) {
			if (!isVetexExist(start))
				addVetex(start);
			if (!isVetexExist(end))
				addVetex(end);

			Edge<T> edge = new Edge<T>(start, end);
			edges.add(edge);
			return this;
		}

		public Vertex<T> getUnvisitedChildren(Vertex<T> n) {
			for (Edge<T> e : edges) {
				if (e.visited == false && e.start.equals(n)) {
					e.visited = true;
					return e.end;
				}
			}
			return null;
		}

		public boolean hasUnvisitedEdge(Vertex<T> n) {
			for (Edge<T> e : edges) {
				if (e.visited == false && e.start.equals(n)) {
					return true;
				}
			}
			return false;
		}

		public Edge<T> getUnvisitedEdge(Vertex<T> n) {
			for (Edge<T> e : edges) {
				if (e.visited == false && e.start.equals(n)) {
					e.visited = true;
					return e;
				}
			}
			return null;
		}

		public void clearNodes() {
			for (Edge<T> e : edges)
				e.visited = false;

			for (Vertex<T> v : vetexes)
				v.visited = false;

		}
	}