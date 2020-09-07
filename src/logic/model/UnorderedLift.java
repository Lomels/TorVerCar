package logic.model;

public class UnorderedLift implements Comparable<UnorderedLift> {

	private Lift lift;
	private Integer comparator;

	public UnorderedLift(Lift lift, Integer deltaDuration, Integer id, Integer maxPossibleElements) {
		this.lift = lift;
		this.comparator = deltaDuration * (maxPossibleElements + 1) + id;
	}

	@Override
	public int compareTo(UnorderedLift o) {
		return this.comparator - o.comparator;
	}

	public Lift getLift() {
		return this.lift;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}