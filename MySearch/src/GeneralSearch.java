import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class GeneralSearch<A> {
	/**
	 * Method to find a path to a node
	 * 
	 * @param startNode
	 *            The starting node
	 * @param predicate
	 *            The goal node
	 * @param general            
	 * 				parameter of type General<Node<A>> it could be either a stack or a queue
	 * @return The path or empty stack
	 */
	public Maybe<Stack<Node<A>>> findPath(Node<A> startNode, Predicate<A> predicate, General<Node<A>> general)
	{
		
 		LinkedHashMap<Node<A>, Node<A>> map = new LinkedHashMap<Node<A>,Node<A>>();
		Stack<Node<A>> visited = new Stack<Node<A>>();
		Stack<Node<A>> path = new Stack<Node<A>>();

		general.addItem(startNode);
		
		while (!general.Empty()) {
			Node<A> x = general.popItem();
			A content = x.contents();
			if (!alreadyVisited(x, visited)) {
				if (predicate.holds(content)) {
					Node<A> a = x;
					while(a != startNode)
					{
						path.push(a);
						a = map.get(a);
					}
					path.push(startNode);
					return new Just<Stack<Node<A>>>(path);
				}

				visited.push(x);
				for (Node<A> suc : x.successors()) {
					if (!(alreadyVisited(suc, visited))) {
						map.put(suc,  x);
						general.addItem(suc);
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
