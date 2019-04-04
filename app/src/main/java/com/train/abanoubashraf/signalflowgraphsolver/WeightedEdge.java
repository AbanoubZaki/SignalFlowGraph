package com.train.abanoubashraf.signalflowgraphsolver;

class WeightedEdge<T> extends Edge<T> {
		public double weight;
		public boolean visited = false;

		public WeightedEdge(Vertex<T> start, Vertex<T> end, double weight) {
			super(start, end);
			this.weight = weight;
		}

		public int compareTo(WeightedEdge<T> that) {
			if (this.weight < that.weight)
				return -1;
			else if (this.weight > that.weight)
				return +1;
			else
				return 0;
		}
		
		public double getWeight() {
			return weight;
		}

		@Override
		public String toString() {
			return "WeightedEdge [start=" + start + ", end=" + end + ", weight=" + weight + ", visited=" + visited
					+ "]";
		}
	}
