
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Elevator implements ElevatorInterface{

	private Integer currentFloor,numberFloors =0;
	//TreeSet is used to have the destination in a sorted order.
	private TreeSet<Integer> destinationFloors= new TreeSet<Integer>();
	private Direction direction=Direction.NONE;
	private Status status=Status.IDLE;
	private Integer elevatorID;
	//TreeMap is used to have set of destinations corresponding to the pick up floor 
	private TreeMap<Integer, Set<Integer>> pickUpFloors= new TreeMap<Integer,Set<Integer>>();

	/*Constructor which gets number of floors and elevatorId 
	 *from the simulator class*/
	public Elevator(Integer numberFloors,Integer elevatorID)
	{
		Random r = new Random();
		this.currentFloor= r.nextInt(numberFloors)+1;
		this.numberFloors =numberFloors;
		this.elevatorID =  elevatorID;
		System.out.println("Current floor for elevator "+(elevatorID)+" is : "+currentFloor);
	}

	//Returns the status of the elevator
	public Status getStatus() {
		return status;

	}
	//Returns the current floor of the elevator
	public int getCurrentFloor()
	{
		return this.currentFloor;
	}

	//Returns the number of floors in the bulding
	public int getNumberOfFloors()
	{
		return this.numberFloors;
	}
	//Returns the direction of the elevator
	public Direction getDirection()
	{
		return direction;

	}
	// Returns the elevator ID 
	public Integer getElevatorID()
	{
		return this.elevatorID;
	}
    
	/*Checks if the current floor is less than the number of floors
	 * and moves the elevators upwards
	 */
	private void goUp()
	{
		if(currentFloor < numberFloors)
		{
			currentFloor++;
		}
	}
	/*Checks if the current floor is greater than 0
	 * and move the elevators downwards
	 */
	private void goDown()
	{
		if(currentFloor > 0)
		{
			currentFloor--;
		}
	}


	/*Based on the pick up floor determine the direction 
	 * of elevator. Also change the status of the elevator
	 * from IdLE to START
	 */
	public void pickUp(Integer pickUpFloor,Integer destinationFloor)
	{ 

		if(!destinationFloor.equals(pickUpFloor))
		{
			synchronized (pickUpFloors) {

				Set<Integer> currDestFloors = pickUpFloors.get(pickUpFloor);
				if (currDestFloors == null) {
					currDestFloors = new HashSet<Integer>();
				}
				currDestFloors.add(destinationFloor);
				pickUpFloors.put(pickUpFloor, currDestFloors);
			}

			if(status==Status.IDLE)
			{
				if(currentFloor<pickUpFloor)
				{

					direction =Direction.UP;

				}
				else if (currentFloor > pickUpFloor)
				{
					direction = Direction.DOWN;

				}
				else
				{
					direction=Direction.DOWN;
				}

				status=Status.START;
			}
		}
		else
		{
			System.out.println("Error: Pickup floor"+" "+pickUpFloor+" " +"and destination floor"+" "+destinationFloor+" is same");
		}
	}
	/*Based on the direction and the current floor number
	 * we pick up passengers and add the destination of the
	 * passenger to the Tree MAP
	 * */
	public void run()  
	{
		while(true)
		{
			while(status!=Status.IDLE)
			{
				synchronized (pickUpFloors) {
					if(pickUpFloors.containsKey(currentFloor))
					{
						removeFloors(currentFloor,true);
					}
				}

				if(destinationFloors.contains(currentFloor))
				{
					removeFloors(currentFloor,false);			
				}
				checkAndUpdateDirectionAndStatus();
			}

		}
	}

	/*This method will print the appropriate message for pick up for
	 * destination with a stop time 100 msec.
	 * It also removes the pick up and drop of requests
	 * from the TreeMap and TreeSet respectively.
	 * */

	private void removeFloors(Integer removeFloor,boolean pickUp)
	{
		status =Status.STOP;
		try {
			//to simulate stop
			Thread.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		if(pickUp){
			destinationFloors.addAll(pickUpFloors.get(removeFloor));
			pickUpFloors.remove(removeFloor);

			System.out.println("Picked up from floor: "+removeFloor+" by elevator"+" "+(elevatorID));
		}
		else
		{
			destinationFloors.remove(removeFloor);
			System.out.println("Dropped at floor: "+currentFloor+" by elevator"+" "+(elevatorID));
		}
	}
	/*Checks and updates the status and direction of the
	 * elevator
	 */
	private void checkAndUpdateDirectionAndStatus()
	{
		if(destinationFloors.isEmpty() && pickUpFloors.isEmpty())
		{
			status =Status.IDLE;
		}
		else
		{
			if(direction==Direction.DOWN){
				if(destinationFloors.lower(currentFloor)!=null)
				{
					goDown();
					status = Status.RUN;
				}

				else 
				{
					Integer lowerFloor = pickUpFloors.lowerKey(currentFloor);
					Integer higherFloor =destinationFloors.higher(currentFloor);

					if(lowerFloor!=null && higherFloor!=null)
					{ 
						int distDown = currentFloor - lowerFloor;
						int distUp = higherFloor - currentFloor;
						if(distDown <= distUp){
							goDown();
						}
						else {
							direction =Direction.UP;

						}
					}
					else if(lowerFloor!=null)
					{
						goDown();

					}
					else
					{
						direction =Direction.UP;

					}

					status =Status.RUN;
				}	
			}
			if(direction==Direction.UP){

				if(destinationFloors.higher(currentFloor)!=null)
				{
					goUp();
					status = Status.RUN;
				}
				else 
				{
					Integer lowerFloor = destinationFloors.lower(currentFloor);
					Integer higherFloor =pickUpFloors.higherKey(currentFloor);
					if(lowerFloor!=null && higherFloor!=null)
					{
						int distDown = currentFloor - lowerFloor;
						int distUp = higherFloor - currentFloor;
						if(distDown >= distUp){
							goUp();
						}
						else {
							direction =Direction.DOWN;							
						}
					}
					else if(higherFloor!=null)
					{
						goUp();
					}
					else
					{
						direction =Direction.DOWN;
					}
					status =Status.RUN;
				}

			}
		}

	}

}


