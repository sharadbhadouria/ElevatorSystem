# ElevatorSystem
Elevator Control System

Data Structure used:
TreeMap for PickupFloors
TreeSet for Destination floors

This solution consists of two classes, Elevator.java and Simulator.java.

The Elevator class handles all the following operations involving the elevator:
1.pickUp : That decides the direction of elevator based on pickup floor.
2.Run : In this operation we check if the current floor is the pickup floor. If it is, we add the corresponding destination floor to a Treeset.
And check if the current floor is destination floor.
3.removeFloors: Removing the visited pick up floor and destination floor.4
4.checkAndupdateDirectionAndStatus:Checks and updates the status and direction of the elevator.

The simulator class is the driver class which creates 16 instances of the
Elevator class.It also decides the closest elevator depending on the pickup floor. 


Algorithm:
-If the current floor is the pickup floor, remove the pickup floor from the TreeMAp and add the corresponding destination floor in the TreeSet.
-If the current floor if the destination floor remove the destination floor from the TreeSet.
-While going Down:
-if there is destination floors lower than current floor keep going down
-if there is pick up floor lower than current floor keep going down
-else go up.
-While going UP:
-if there is a destination floors higher than current floor keep going UP.  
-if there is a pick up floor higher than current floor keep going up
-else go Down.
-Above the steps are repeated until status becomes idle.


This algorithm is better than FCFS because, based on the distance, either pickup or
 drop of operation is performed which is made easier by using the collection
framework used in this solution which stores the requests in a sorted order.

Running the project:

Load the project and run the simulator class, that generates 100 pickup and destination requests.







