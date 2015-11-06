import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

public class AStar<A> {
/**
 * Method to find path using A*
 * @param startnode the node from which we start searching
 * @param goalnode  the node we are looking for
 * @param h the heuristics
 * @param d the distance form one node to another
 * @return
 */
	public Maybe<Stack<Node<A>>> Search(Node<A> startnode, Node<A> goalnode,
			Function2<Node<A>, Node<A>, Integer> h,
			Function2<Node<A>, Node<A>, Integer> d) {
		Stack<Node<A>> path = new Stack<Node<A>>();
		Stack<Node<A>> visited = new Stack<Node<A>>();
		Set<Node<A>> pending = new LinkedHashSet<Node<A>>();
		LinkedHashMap<Node<A>, Node<A>> pred = new LinkedHashMap<Node<A>, Node<A>>();
		//LinkedHashMap<Node<A>, Integer> D = new LinkedHashMap<Node<A>, Integer>();
		LinkedHashMap<Node<A>, Integer> H = new LinkedHashMap<Node<A>, Integer>();
		LinkedHashMap<Node<A>, Integer> F = new LinkedHashMap<Node<A>, Integer>();
		LinkedHashMap<Node<A>, Integer> BestPath = new LinkedHashMap<Node<A>, Integer>();
		pending.add(startnode);
		BestPath.put(startnode, 0);
		H.put(startnode, h.apply(startnode, goalnode));
		F.put(startnode, startnode.f(H.get(startnode), BestPath.get(startnode)));
		

		while (!pending.isEmpty()) {
			Iterator<Node<A>> iter = pending.iterator();
			Node<A> mins = iter.next();
			while (iter.hasNext()) {
				Node<A> min2 = iter.next();
				if (F.get(mins) > F.get(min2)) {
					mins = min2;
				}
			}

			pending.remove(mins);
			if (!alreadyVisited(mins, visited)) {
				if (mins.contentsEquals(goalnode.contents())) {
					Node<A> a = mins;
					while (!a.equals(startnode)) {
						path.push(a);
						a = pred.get(a);
					}
					path.push(startnode);
					return new Just<Stack<Node<A>>>(path);
				}
				visited.push(mins);
				for (Node<A> suc : mins.successors()) {
					
					if (!(alreadyVisited(suc, visited))) {
						int cost = BestPath.get(mins) + d.apply(mins, suc);
						if((!pending.contains(suc)) || cost<BestPath.get(suc) )
								{
								pred.put(suc, mins);
								H.put(suc, h.apply(suc, goalnode));
								BestPath.put(suc, cost);
								pending.add(suc);
								F.put(suc, suc.f(H.get(suc), d.apply(mins, suc)));
								}
					}
				}
			}

		}
		return new Nothing<Stack<Node<A>>>();
	}
	/**
	 * Method to check if a node was already visited
	 * 
	 * @param something
	 *            The node
	 * @param visited
	 *            The stack that saves the visited nodes
	 * @return True or false
	 */
	private boolean alreadyVisited(Node<A> something, Stack<Node<A>> visited) {

		if (visited.contains(something)) {
			return true;
		}
		return false;
	}

}
