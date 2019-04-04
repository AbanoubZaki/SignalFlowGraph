package com.train.abanoubashraf.signalflowgraphsolver;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class Graph<T> implements IGraph<T> {

		// DFS traversal of a tree is performed by the dfs() function
		public void dfs(Vertex<T> start) {
			// DFS uses Stack data structure
			Stack<Vertex<T>> stack = new Stack<Vertex<T>>();
			start.visited = true;
			stack.push(start);
			printNode(start);
			while (!stack.isEmpty()) {
				Vertex<T> n = stack.peek();
				Vertex<T> child = getUnvisitedChildren(n);
				if (child != null) {
					child.visited = true;
					stack.push(child);
					printNode(child);
				} else {
					stack.pop();
				}
			}
		}

		// BFS traversal of a graph is performed by the bfs() function
		public void bfs(Vertex<T> start) {
			// BFS uses Queue data structure
			Queue<Vertex<T>> que = new LinkedList<Vertex<T>>();
			start.visited = true;
			que.add(start);
			printNode(start);

			while (!que.isEmpty()) {
				Vertex<T> n = (Vertex<T>) que.remove();
				Vertex<T> child = null;
				while ((child = getUnvisitedChildren(n)) != null) {
					child.visited = true;
					que.add(child);
					printNode(child);
				}
			}
		}

		public void printNode(Vertex<T> n) {
			System.out.print(n.label + " ");
		}
	}