package com.train.abanoubashraf.signalflowgraphsolver;

import java.util.*;

/**
 * Find all simple cycles in a directed graph using Tarjan's algorithm.
 *
 * Space complexity - O(E + V + S) where S is length of all cycles Time
 * complexity - O(E*V(C+1) where C is total number of cycles
 *
 * Reference - https://ecommons.cornell.edu/handle/1813/5941
 * https://github.com/jgrapht/jgrapht/tree/master/jgrapht-core/src/main/java/org/jgrapht/alg/cycle
 */
public class AllCyclesInDirectedGraphTarjan {

	private Set<Vertex<String>> visited;
	private Deque<Vertex<String>> pointStack;
	private Deque<Vertex<String>> markedStack;
	private Set<Vertex<String>> markedSet;

	public AllCyclesInDirectedGraphTarjan() {
		reset();
	}

	private void reset() {
		visited = new HashSet<>();
		pointStack = new LinkedList<>();
		markedStack = new LinkedList<>();
		markedSet = new HashSet<>();
	}

	public List<ArrayList<Vertex<String>>> findAllSimpleCycles(AdjMatrixDirectGraph<String> graph) {
		reset();
		List<ArrayList<Vertex<String>>> result = new ArrayList<>();
		for (Vertex<String> vertex : graph.getVertexes()) {
			findAllSimpleCycles(graph, vertex, vertex, result);
			visited.add(vertex);
			while (!markedStack.isEmpty()) {
				markedSet.remove(markedStack.pollFirst());
			}
		}
		return result;
	}

	private boolean findAllSimpleCycles(AdjMatrixDirectGraph<String> graph, Vertex<String> start, Vertex<String> vertex,
			List<ArrayList<Vertex<String>>> result) {
		boolean hasCycle = false;
		pointStack.offerFirst(vertex);
		markedSet.add(vertex);
		markedStack.offerFirst(vertex);

		for (Vertex<String> w : graph.getNeighbors(vertex)) {
			if (visited.contains(w)) {
				continue;
			} else if (w.equals(start)) {
				hasCycle = true;
				pointStack.offerFirst(w);
				ArrayList<Vertex<String>> cycle = new ArrayList<>();
				Iterator<Vertex<String>> itr = pointStack.descendingIterator();
				while (itr.hasNext()) {
					cycle.add(itr.next());
				}
				pointStack.pollFirst();
				result.add(cycle);
			} else if (!markedSet.contains(w)) {
				hasCycle = findAllSimpleCycles(graph, start, w, result) || hasCycle;
			}
		}

		if (hasCycle) {
			while (!markedStack.peekFirst().equals(vertex)) {
				markedSet.remove(markedStack.pollFirst());
			}
			markedSet.remove(markedStack.pollFirst());
		}

		pointStack.pollFirst();
		return hasCycle;
	}
	
}