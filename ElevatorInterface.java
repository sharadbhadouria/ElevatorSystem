
public interface ElevatorInterface extends Runnable{

	 void pickUp(Integer pickupFloor,Integer destinationFloor);
	 Status getStatus();
	 Direction getDirection();
	 void run();
}
