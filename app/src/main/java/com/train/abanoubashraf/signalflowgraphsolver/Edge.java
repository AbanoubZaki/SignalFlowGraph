package com.train.abanoubashraf.signalflowgraphsolver;

public class Edge<T> {
		public Vertex<T> start;
		public Vertex<T> end;
		public boolean visited = false;

		public Edge(Vertex<T> start, Vertex<T> end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public String toString() {
			return "Edge [start=" + start + ", end=" + end + ", visited=" + visited + "]";
		}

	}