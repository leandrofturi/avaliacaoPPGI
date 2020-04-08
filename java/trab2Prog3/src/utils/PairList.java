package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class PairList<F, S> implements Iterable<Pair<F, S>>, Serializable {
	
	private ArrayList<Pair<F, S>> list = new ArrayList<Pair<F, S>>();
	
	private static final long serialVersionUID = 1L;
	
	public PairList() { }
	
	public int size() {
		return this.list.size();
	}

	public boolean isEmpty() {
		return this.list.size() == 0;
	}

	public boolean contains(Object obj) {
		return list.contains(obj);
	}
	
	public boolean contains(F first, S second) {
		for(Pair<F, S> p : list) {
			if(p.getFirst().equals(first) && p.getSecond().equals(second))
				return true;
		}
		return false;
	}
	
	public boolean contains(Pair<F, S> pair) {
		return this.contains(pair.getFirst(), pair.getSecond());
	}
	
	public Pair<F, S> begin() {
		return this.list.get(0);
	}
	
	public Pair<F, S> end() {
		return this.list.get(this.size());
	}
	
	public void put(F first, S second) {
		Pair<F, S> pair = new Pair<F, S> (first, second);
		put(pair);
	}
	
	public void put(Pair<F, S> pair) {
		if(!this.contains(pair))
			list.add(pair);
	}
	
	public void take(F first, S second) {
		Pair<F, S> pair = new Pair<F, S> (first, second);
		take(pair);
	}
	
	public void take(Pair<F, S> pair) {
		list.remove(pair);
	}
	
	public void clean() {
		list.clear();
	}
	
	public Pair<F, S> findByFirst(F first) {
		for(Pair<F, S> p : this) {
			if(p.getFirst().equals(first))
				return p;
		}
		return null;
	}
	
	public Pair<F, S> findBySecond(S second) {
		for(Pair<F, S> p : this) {
			if(p.getSecond().equals(second))
				return p;
		}
		return null;
	}
	
	public Pair<F, S> get(int i) {
		return this.list.get(i);
	}

	public boolean equals(PairList<F, S> obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (list == null) {
			if (obj.list != null)
				return false;
		} else if (!list.equals(obj.list))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PairList [" + list + "]";
	}
	
	public Iterator<Pair<F, S>> iterator() {
		return new PairIterator();
	}
	
	class PairIterator implements Iterator<Pair<F, S>> {
		
		private int index = 0;
		
		public boolean hasNext() {
	        return index < size();
	    }

	    public Pair<F, S> next() {
	        return get(index++);
	    }
   }
	
}