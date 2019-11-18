package utils;

import java.io.Serializable;

public class Pair<F, S> implements Serializable {
	
	private F first;
	private S second;
	
	private static final long serialVersionUID = 1L;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public Pair(Pair<F, S> tuple) {
        this.first = tuple.getFirst();
        this.second = tuple.getSecond();
    }

    public F getFirst() {
    	return this.first;
    }
    
    public S getSecond() {
    	return this.second;
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
		Pair<F, S> other = (Pair<F, S>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}

}