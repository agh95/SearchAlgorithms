
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Class which generalise a queue and a stack
 */
public class General<A> implements Generalization<A>{

	private Stack<A> mystack;
	private Queue<A> myqueue;
	
	 /**
	  * Constructor in case it is stack
	  * @param stack
	  */
	public General(Stack<A> mystack)
	{
		this.mystack=mystack;
		
	}
	 /**
	  * Constructor in case it is queue
	  * @param queue
	  */
	public General(Queue<A> myqueue)
	{
		this.myqueue=myqueue;
		
	}
	
	@Override
	public void addItem(A item) {
	
		if (mystack == null)
		{
			myqueue.add(item);
		}
		else
		{
			mystack.add(item);
		}
		
	}
	@Override
	public int sizeGeneral() {
		
		if (mystack == null)
		{
			return myqueue.size();
		}
		else
		{
			return mystack.size();
		}
		
		
	}
	@Override
	public boolean Empty() {
		
		if (mystack == null)
		{
			return myqueue.isEmpty();
		}
		else
		{
			return mystack.empty();
		}
		
	}
	@Override
	public A popItem() {
		
		if (mystack == null)
		{
			return myqueue.peek();
		}
		else
		{
			return mystack.pop();
		}
	}

	
	}
