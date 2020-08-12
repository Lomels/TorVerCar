package logic.utilities;

import java.util.ArrayList;
import java.util.List;

public class ListCombiner {

	private List<UnfinishedList> lastCompletedIteration = new ArrayList<>();
	private List<UnfinishedList> workingIteration = new ArrayList<>();

	private class UnfinishedList {
		
		private List<Object> actualList;
		private Integer lastAddition;

		public UnfinishedList(List<?> actualList, Integer lastAddition) {
			this.actualList = new ArrayList<>(actualList);
			this.lastAddition = lastAddition;
		}

		public UnfinishedList(List<?> actualList, Integer lastAddition, Object addedElement) {
			this.actualList = new ArrayList<>(actualList);
			this.actualList.add(lastAddition, addedElement);
			this.lastAddition = lastAddition;
		}

		protected List<?> getActualList() {
			return actualList;
		}

		protected Integer getLastAddition() {
			return lastAddition;
		}

		protected Integer size() {
			return this.actualList.size();
		}

	}
	
	public List<List<?>> combine(List<?> startList, List<?> addList) {

		UnfinishedList first = new UnfinishedList(startList, 0);
		lastCompletedIteration.add(first);

		for (Object stopToAdd : addList) {
			this.singleCombine(stopToAdd);
		}

		List<List<?>> result = new ArrayList<List<?>>();
		for (UnfinishedList unfinished : this.lastCompletedIteration) {
			result.add(unfinished.getActualList());
		}
		return result;
	}

	private void singleCombine(Object elementToAdd) {
		for (UnfinishedList unfinished : this.lastCompletedIteration) {
			for (Integer index = unfinished.getLastAddition() + 1; index < unfinished.size(); index++) {
				workingIteration.add(new UnfinishedList(unfinished.getActualList(), index, elementToAdd));
			}
		}
		this.lastCompletedIteration = new ArrayList<UnfinishedList>(workingIteration);
		this.workingIteration = new ArrayList<UnfinishedList>();
	}

}
