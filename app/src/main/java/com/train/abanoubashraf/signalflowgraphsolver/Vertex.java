package com.train.abanoubashraf.signalflowgraphsolver;

public class Vertex<T> {
	public T label;
	public boolean visited = false;
	public Integer index = null;
	public Integer lowval = null;

	public Vertex(T n) {
		this.label = n;
	}

	@Override
	public String toString() {
		return "Vertex-" + label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + (visited ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Vertex<T> other = (Vertex<T>) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (visited != other.visited)
			return false;
		return true;
	}
}