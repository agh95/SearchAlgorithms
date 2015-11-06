import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;

public class DepthFirst<A> {

	public Maybe<Node<A>> Search(Node<A> startNode, Predicate<A> predicate) {
		Stack<Node<A>> stack = new Stack<Node<A>>();
		ArrayList<Node<A>> visited = new ArrayList<Node<A>>();
		stack.push(startNode);
		while (!stack.isEmpty()) {
			Node<A> x = stack.pop();
			A content = x.contents();
			if (!alreadyVisited(x, visited)) {
				if (predicate.holds(content)) {
					return new Just<Node<A>>(x);
				}

				visited.add(x);

				for (Node<A> suc : x.successors()) {
					if (!(alreadyVisited(suc, visited))) {
						stack.push(suc);
					}
				}
			}

		}
		return new Nothing<Node<A>>();
	}
		public Stack<Node<A>> FindPath(Node<A> startNode, Predicate<A> predicate) {
			Stack<Node<A>> stack = new Stack<Node<A>>();
			Stack<Node<A>> pathnodes = new Stack<Node<A>>();
			ArrayList<Node<A>> visited = new ArrayList<Node<A>>();

			stack.push(startNode);
			while (!stack.isEmpty()) {
				Node<A> x = stack.pop();
				pathnodes.push(x);
				A content = x.contents();

				if (!alreadyVisited(x, visited)) {
					if (predicate.holds(content)) {

						return pathnodes;
					}
					visited.add(x);

					if (x.successors().isEmpty()) {
						pathnodes.pop();
						Node<A> copy = pathnodes.pop();
						while (visitedAll(copy.successors(), visited)) {
							copy = pathnodes.pop();
						}
						pathnodes.push(copy);
					}

					for (Node<A> suc : x.successors()) {
						if (!(alreadyVisited(suc, visited))) {
							stack.push(suc);
						}

					}
				}

			}
			return new Stack<Node<A>>();
		}
	

	private boolean alreadyVisited(Node<A> x, ArrayList<Node<A>> visited) {

		if (visited.contains(x)) {
			return true;
		}
		return false;
	}
	
	private boolean visitedAll(Set<Node<A>> successors,
			ArrayList<Node<A>> visited) {
		for (Node<A> suc : successors) {
			if (!alreadyVisited(suc, visited)) {
				return false;
			}
		}
		return true;
	}

}
