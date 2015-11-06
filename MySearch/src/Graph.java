import java.util.*;



// We represent a graph as a set of nodes. 
// This is a minimal class so that a graph can be created.

public class Graph<A> {

  // Keep the implementation of sets open, by using the Set interface:
  private Set<Node<A>> nodes;       

  // Constructs the empty graph:
  public Graph() {
    // Choose any implementation of sets you please, but you need to
    // choose one.
    nodes = new LinkedHashSet<Node<A>>(); 
  }

  // Get method:
  public Set<Node<A>> nodes() {
    return nodes;
  }

  // Finds or else creates a node with a given contents c:
  public Node<A> nodeWith(A c) { 
    for (Node<A> node : nodes) {  // Inefficient for large graph.
      if (node.contentsEquals(c))
        return node; // Found.
    }
    // Not found, hence create it:
    Node<A> node = new Node<A>(c);
    nodes.add(node);
    return node;
  }

  // Builds sample graph for testing:
  public static void main(String args []) {
    int [] [] nick = {
      {0,0,1,0,0,1}, 
      {0,1,0,0,1,1,0,2}, 
      {0,2,0,3,0,1}, 
      {0,3,0,2,0,4}, 
      {0,4,0,3,0,5}, 
      {0,5,0,6,1,5,0,4}, 
      {0,6,1,6,0,5}, 
      {1,0,0,0,1,1,2,0}, 
      {1,1,1,2,2,1,1,0,0,1}, 
      {1,2,2,2,1,1,1,3}, 
      {1,3,1,2,1,4,2,3}, 
      {1,4,2,4,1,5,1,3}, 
      {1,5,1,4,2,5,1,6,0,5}, 
      {1,6,0,6,1,5,2,6}, 
      {2,0,3,0,2,1,1,0}, 
      {2,1,2,2,1,1,2,0,3,1}, 
      {2,2,1,2,2,1,2,3,3,2}, 
      {2,3,2,2,2,4,3,3,1,3}, 
      {2,4,1,4,2,5,2,3,3,4}, 
      {2,5,2,4,1,5,2,6,3,5}, 
      {2,6,3,6,2,5,1,6}, 
      {3,0,2,0,3,1}, 
      {3,1,3,0,4,1,2,1,3,2}, 
      {3,2,2,2,4,2,3,1}, 
      {3,3,2,3,3,4}, 
      {3,4,2,4,3,3}, 
      {3,5,3,6,2,5,4,5}, 
      {3,6,2,6,3,5}, 
      {4,0}, 
      {4,1,4,2,5,1,3,1}, 
      {4,2,4,1,5,2,3,2}, 
      {4,3}, 
      {4,4}, 
      {4,5,5,5,3,5}, 
      {4,6}, 
      {5,0}, 
      {5,1,4,1,5,2,6,1}, 
      {5,2,4,2,5,1,6,2}, 
      {5,3}, 
      {5,4}, 
      {5,5,4,5,6,5}, 
      {5,6}, 
      {6,0,7,0,6,1}, 
      {6,1,6,0,5,1,6,2,7,1}, 
      {6,2,5,2,6,1,7,2}, 
      {6,3,7,3,6,4}, 
      {6,4,6,3,7,4}, 
      {6,5,5,5,6,6,7,5}, 
      {6,6,7,6,6,5}, 
      {7,0,6,0,7,1,8,0}, 
      {7,1,8,1,7,0,6,1,7,2}, 
      {7,2,7,3,8,2,6,2,7,1}, 
      {7,3,6,3,7,2,7,4,8,3}, 
      {7,4,7,3,8,4,6,4,7,5}, 
      {7,5,8,5,7,6,7,4,6,5}, 
      {7,6,6,6,7,5,8,6}, 
      {8,0,8,1,7,0,9,0}, 
      {8,1,8,2,9,1,7,1,8,0}, 
      {8,2,8,1,7,2,8,3}, 
      {8,3,8,2,7,3,8,4}, 
      {8,4,8,5,8,3,7,4}, 
      {8,5,9,5,8,4,7,5,8,6}, 
      {8,6,8,5,7,6,9,6}, 
      {9,0,9,1,8,0}, 
      {9,1,8,1,9,2,9,0}, 
      {9,2,9,1,9,3}, 
      {9,3,9,2,9,4}, 
      {9,4,9,5,9,3}, 
      {9,5,8,5,9,4,9,6}, 
      {9,6,9,5,8,6} 
    };
    Graph<Coordinate> nicksGraph = new Graph<Coordinate>();

    for (int i = 0; i < nick.length; i++) {
      // What we are going to do relies on the two following facts
      // about nick:
      assert(nick[i].length >= 2);       // (1)
      assert(nick[i].length % 2 == 0);   // (2)

      int x = nick[i][0]; // Can't get array out of bounds 
      int y = nick[i][1]; // because of assertion (1).
      Coordinate c = new Coordinate(x, y);
      Node<Coordinate> node = nicksGraph.nodeWith(c);

      // And next we add its successors. We rely on assertion (2)
      // again to avoid array out of bounds. Now we start from
      // position 2, as positions 0 and 1 have already been looked at
      // (they are x and y). Notice that we need to increment by 2.

      for (int j = 2; j < nick[i].length; j=j+2) {
        int sx = nick[i][j];   
        int sy = nick[i][j+1]; 
        Coordinate sc = new Coordinate(sx, sy);
        Node<Coordinate> s = nicksGraph.nodeWith(sc);
        node.addSuccessor(s);
      }
    }
    Function2<Node<Coordinate>,Node<Coordinate>, Integer> h = new Function2<Node<Coordinate>,Node<Coordinate>,Integer>()
    		{

				@Override
				public Integer apply(Node<Coordinate> a, Node<Coordinate> b) {
					Integer c;
					c = (int) Math.abs(Math.sqrt(Math.pow((a.contents().x -b.contents().x),2)+Math.pow((a.contents().y -b.contents().y),2)));
					return c;
				}
    	
    		};
   	Function2<Node<Coordinate>,Node<Coordinate>, Integer> d = new Function2<Node<Coordinate>,Node<Coordinate>, Integer>()
    	    {

    			@Override
    			public Integer apply(Node<Coordinate> a, Node<Coordinate> b) {
    					Integer c;
    					c =  Math.abs((a.contents().x -b.contents().x)+(a.contents().y -b.contents().y));
    					return c ;
    				}
    	    
    	    	};		
	Predicate<Coordinate> p = new Predicate<Coordinate>()
			{
				public boolean holds(Coordinate x) {
					return (x.equals(new Coordinate(3,1)));
					
				}
			};
	DepthFirst<Coordinate> search = new DepthFirst<Coordinate>();
	BreathFirst<Coordinate> searchB = new BreathFirst<Coordinate>();
	AStar<Coordinate> AStarSearch = new AStar<Coordinate>();
	GeneralSearch<Coordinate> GeneralSearch = new GeneralSearch<Coordinate>();
    System.out.println("The nodes represented as coordinates:");
    for (Node<Coordinate> node : nicksGraph.nodes()) {
    	System.out.print("(" + node.contents().x + "," + node.contents().y + "): ");
    	for(Node<Coordinate> s : node.successors()) {
    		System.out.print("(" + s.contents().x + "," + s.contents().y + ")");
    	}
    	System.out.println();
    }	
   Coordinate c = new Coordinate(0,0);
   Coordinate goal = new Coordinate(3,1);
	Node<Coordinate> startnode = nicksGraph.nodeWith(c);
	Node<Coordinate> goalnode = nicksGraph.nodeWith(goal);
	Maybe<Node<Coordinate>> node = search.Search(startnode, p);
	Maybe<Node<Coordinate>> node2 = searchB.Search(startnode, p);
	System.out.println("Finding the node using BFS:");	
	try{
	System.out.println("("+node2.fromMaybe().contents().x+", "+node2.fromMaybe().contents().y+")");
	}
	catch(RuntimeException e)
	{
		System.out.println("Not found");
	}
	System.out.println("Finding the node using DFS:");	
	
	try{
	System.out.println("("+node.fromMaybe().contents().x+ ", "+node.fromMaybe().contents().y+")");
	}
	catch(RuntimeException e)
	{
		System.out.println("Not found");
	}
	System.out.println("Finding a path using DFS:");	
	Stack<Node<Coordinate>> pathDFS = search.FindPath(startnode, p);
		for(int i=0; i<pathDFS.size(); i++)
		{
			System.out.print("(" + pathDFS.get(i).contents().x +", "+ pathDFS.get(i).contents().y + ") ");		
		}
		System.out.println("");
	System.out.println("Finding a path using BFS:");	
	Stack<Node<Coordinate>> path = searchB.findPath(startnode, p);
		for(int i=0; i<path.size(); i++)
		{
			System.out.print("(" + path.get(i).contents().x +", "+ path.get(i).contents().y + ") ");		
		}
		System.out.println("");
	System.out.println("Finding a path using Astar:");	
	Maybe<Stack<Node<Coordinate>>> pathAstar = AStarSearch.Search(startnode,goalnode, h, d);
	if(!pathAstar.isNothing())
	{
		for(int i=0; i<pathAstar.fromMaybe().size(); i++)
		{
			System.out.print("(" + pathAstar.fromMaybe().get(i).contents().x +", "+ pathAstar.fromMaybe().get(i).contents().y + ") ");		
		}
	}
	else {
		System.out.println(pathAstar);
	}
	Stack<Node<Coordinate>> stack = new Stack<Node<Coordinate>>();
	General<Node<Coordinate>> nodes = new General<Node<Coordinate>>(stack);
	Maybe<Stack<Node<Coordinate>>> pathGen =  GeneralSearch.findPath(startnode, p, nodes);
	System.out.println(" ");
	System.out.println("General DFS:");
	for(int i=0; i<pathGen.fromMaybe().size(); i++)
	{
		System.out.print("(" + pathGen.fromMaybe().get(i).contents().x +", "+ pathGen.fromMaybe().get(i).contents().y + ") ");		
	}
	System.out.println("");
	System.out.println("General BFS:");
	Queue<Node<Coordinate>> queue = new LinkedList<Node<Coordinate>>();
	General<Node<Coordinate>> nodesqueue = new General<Node<Coordinate>>(queue);
	Maybe<Stack<Node<Coordinate>>> pathGen2 = GeneralSearch.findPath(startnode, p, nodesqueue);
	
	for(int i=0; i<pathGen2.fromMaybe().size(); i++)
	{
		System.out.print("(" + pathGen2.fromMaybe().get(i).contents().x +", "+ pathGen2.fromMaybe().get(i).contents().y + ") ");		
	}
  
  }
}
