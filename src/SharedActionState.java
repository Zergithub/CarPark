
import java.net.*;
import java.io.*;

public class SharedActionState{
	
	private SharedActionState mySharedObj;
	private String myThreadName;
	//private double mySharedVariable;
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers
    private int CheckSpace = 5;
     private int maxCars= 5; // max car inn park is 5
     private int cars= 0; // max car inn park is 5 

// Constructor	
	
	SharedActionState(int SharedVariable) {
		CheckSpace = SharedVariable;
	}

//Attempt to aquire a lock
	
	
    Thread me = Thread.currentThread(); // get a ref to the current thread

	public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

	public synchronized String processInput(String myThreadName, String theInput) {
    		System.out.println(myThreadName + " received "+ theInput);
    		String theOutput = null;
			  Thread currentThread = Thread.currentThread();

    		switch (theInput.toLowerCase()) {
    		case "checkspace":
    			  Thread me = Thread.currentThread();
    			  
    	        if (me.getName().equals("Thread-0") || me.getName().equals("Thread-1")|| me.getName().equals("Thread-2")|| me.getName().equals("Thread-3")) {
    	            if (CheckSpace > 0) {
    	                System.out.println("Parking is available");
    	                int space = maxCars - CheckSpace;
    	                System.out.println(space);
    	            } else {
    	                System.out.println("Sorry, parking is not available");
    	            }
    	            theOutput = "current avaibale spaces :  " + CheckSpace + " threadname: " + me.getName() + " cars in the car park: " + cars;
    	        }
    	        break;

    	    case "addcar":
  			  Thread me2 = Thread.currentThread();
  			if (me2.getName().equals("Thread-2") || me2.getName().equals("Thread-3")) {
    	        theOutput = "You can't add a car from the exit ";

  			}else
    	        if (me2.getName().equals("Thread-0") || me2.getName().equals("Thread-1")) {
    	            if (CheckSpace > 0) {
    	                CheckSpace--;
    	                cars++;
    	               // System.out.println("Car added. Number of cars in the car park gg: " + cars);
    	    	        theOutput = "current spaces available:  " + CheckSpace + " threadname: " + me2.getName()+" cars in the car park: "+cars;

    	            } else {
    	    	        theOutput = "Sorry, parking is not available " + " threadname: " + me2.getName();

    	              //  System.out.println("Sorry, parking is not available");
    	            }
    	        }
    	       // theOutput = "current spaces available:  " + CheckSpace + " threadname: " + me2.getName()+" cars in the car park: "+cars;
    	        break;

    	    case "removecar":
    			  Thread me3 = Thread.currentThread();
    			  if (me3.getName().equals("Thread-0") || me3.getName().equals("Thread-1")) {
    	    	        theOutput = "You can't remove a car from the entrance ";

    	  			}else
    	    	        
    	        if (me3.getName().equals("Thread-2") || me3.getName().equals("Thread-3")) {
    	            if (CheckSpace < 5) {
    	                CheckSpace++;
    	                cars--;
    	                //System.out.println("Car removed. Number of cars in the car park: " + cars);
        	            theOutput = "current spaces available:   " + CheckSpace + " threadname: " + me3.getName() +" cars in the car park: "+cars;

    	            } else {
    	             //   System.out.println("Carpark is already empty");
        	            theOutput = "Carpark is already empty:   " + " threadname: " + me3.getName();

    	            }
    	           // theOutput = "current spaces available:   " + CheckSpace + " threadname: " + me3.getName() +" cars in the car park: "+cars;
    	        }
    	        break;

    	    default:
    	        theOutput = "current spaces available:    " + CheckSpace + " threadname: " + currentThread.getName()+" cars in the car park: "+cars;
    	}
			return theOutput;	
}
}

