package utils;

import java.util.ArrayList;

public class PairList<F, S> {
	private ArrayList<Pair<F, S>> list = new ArrayList<Pair<F, S>>();
	
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
		for(Pair<F, S> p : list) {
			if(p.getFirst().equals(first))
				return p;
		}
		return null;
	}
	
	public Pair<F, S> findBySecond(S second) {
		for(Pair<F, S> p : list) {
			if(p.getSecond().equals(second))
				return p;
		}
		return null;
	}
}