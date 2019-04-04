package com.train.abanoubashraf.signalflowgraphsolver;

public interface IGraph<T> {
		public void addVertex(Vertex<T> n);

		public boolean isVertexExist(Vertex<T> vertex);

		public void addEdge(Vertex<T> start, Vertex<T> end, double weight);

		public Vertex<T> getUnvisitedChildren(Vertex<T> n);

	}