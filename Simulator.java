import java.util.ArrayList;
import java.util.Random;

public class Simulator {

	public static void main(String[] args)
	{
		final Integer MAX_FLOOR =100;
		final Integer NUMBER_OF_REQUEST =100;
		Integer pickUpFloor;
		Integer destinationFloor;
		Integer MAX_ELEVATOR=16;

		//Creating 16 instances for the elevator
		ArrayList<Elevator> elevators = new ArrayList<Elevator>();
		for(int i=0;i<MAX_ELEVATOR;i++)
		{
			Elevator e = new Elevator(MAX_FLOOR,i);
			elevators.add(e);
			new Thread(e).start();
		}
		for(int i=0;i<NUMBER_OF_REQUEST;i++)
		{
			Random r = new Random();
			pickUpFloor=r.nextInt(MAX_FLOOR)+1;
			destinationFloor=r.nextInt(MAX_FLOOR)+1;
			//To avoid pick up and destination duplicates
			while(destinationFloor.equals(pickUpFloor))
			{
				destinationFloor=r.nextInt(MAX_FLOOR)+1;
			}

			int j=(closestElevator(pickUpFloor,elevators));
			System.out.println("Pick up requested from floor: "+pickUpFloor+" Destination requested to floor: "+destinationFloor);
			elevators.get(j).pickUp(pickUpFloor,destinationFloor);
			System.out.println("Elevator Status of elevator  "+elevators.get(j).getElevatorID()+" "+elevators.get(j).getStatus());
		}
	}

	//TODO: can be improved using elevator direction and current destination list
	/*finding the closest elevator to the pick up floor */
	public static int closestElevator(Integer pickUp, ArrayList<Elevator> elevatorList) {
		Integer min = Integer.MAX_VALUE;
		Integer closest = pickUp;

		for (Elevator e : elevatorList) {
			final Integer diff = Math.abs(e.getCurrentFloor() - pickUp);

			if (diff < min) {
				min = diff;
				closest = e.getElevatorID();
			}
		}

		return closest;
	}
}
