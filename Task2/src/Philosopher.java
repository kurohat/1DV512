import java.util.Random;

public class Philosopher implements Runnable {

	private int id;
	private final ChopStick leftChopStick;
	private final ChopStick rightChopStick;
	private State state;
	private Random randomGenerator = new Random();
	private boolean DEBUG = false;

	private int numberOfEatingTurns = 0;
	private int numberOfThinkingTurns = 0;
	private int numberOfHungryTurns = 0;

	// total time
	private double totalThinkingTime = 0;
	private double totalEatingTime = 0;
	private double totalHungryTime = 0;
	// timer
	private double thinkingTimer = 0;
	private double eatingTimer = 0;
	private double hungryTimer = 0;

	private enum State {
		THINKING, HUNGRY, EATING
	}

	public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick, int seed) {
		this.id = id;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;

		/*
		 * set the seed for this philosopher. To differentiate the seed from the other
		 * philosophers, we add the philosopher id to the seed. the seed makes sure that
		 * the random numbers are the same every time the application is executed the
		 * random number is not the same between multiple calls within the same program
		 * execution
		 * 
		 * NOTE In order to get the same average values use the seed 100, and set the id
		 * of the philosopher starting from 0 to 4 (0,1,2,3,4). Each philosopher sets
		 * the seed to the random number generator as seed+id. The seed for each
		 * philosopher is as follows: P0.seed = 100 + P0.id = 100 + 0 = 100 P1.seed =
		 * 100 + P1.id = 100 + 1 = 101 P2.seed = 100 + P2.id = 100 + 2 = 102 P3.seed =
		 * 100 + P3.id = 100 + 3 = 103 P4.seed = 100 + P4.id = 100 + 4 = 104 Therefore,
		 * if the ids of the philosophers are not 0,1,2,3,4 then different random
		 * numbers will be generated.
		 */

		randomGenerator.setSeed(id + seed);
	}

	public int getId() {
		return id;
	}

	public double getAverageThinkingTime() {
		return totalThinkingTime / thinkingTimer;
	}

	public double getAverageEatingTime() {
		return totalEatingTime / eatingTimer;
	}

	public double getAverageHungryTime() {
		return totalHungryTime / hungryTimer;
	}

	public int getNumberOfThinkingTurns() {
		return numberOfThinkingTurns;
	}

	public int getNumberOfEatingTurns() {
		return numberOfEatingTurns;
	}

	public int getNumberOfHungryTurns() {
		return numberOfHungryTurns;
	}

	public double getTotalThinkingTime() {
		return totalThinkingTime;
	}

	public double getTotalEatingTime() {
		return totalEatingTime;
	}

	public double getTotalHungryTime() {
		return totalHungryTime;
	}

	@Override
	public void run() {
		/*
		 * TODO Think, Hungry, Eat, Repeat until thread is interrupted Increment the
		 * thinking/eating turns after thinking/eating process has finished. Add
		 * comprehensive comments to explain your implementation, including deadlock
		 * prevention/detection
		 */
//		try {
//			
//		}
//		catch (InterruptedException ie){
//			ie.printStackTrace();
//		}

	}

	/*
	 * generate random sleeping time between 0-1000
	 * @return random sleeping time between 0-1000
	 */
	private int genarateSleepTime() {
		// .nextInt(upperBound - lowerBound) + lowerBound;
		return randomGenerator.nextInt(1000 - 0) + 0;
	}

	/*
	 * thinking -> thinking to much -> need to eat
	 */
	private void thinking() throws InterruptedException {
		state = State.THINKING;
		System.out.println("Philosopher " + getId() + " is " + state);
		int rnThinkingTime = genarateSleepTime();
		Thread.sleep(rnThinkingTime);
		totalThinkingTime = rnThinkingTime + thinkingTimer;
		thinkingTimer++;
		hungry();
	}

	private void eating() throws InterruptedException {
		state = State.EATING;
		System.out.println("Philosopher " + getId() + " is " + state);
		int rnEatingTime = genarateSleepTime();
		Thread.sleep(rnEatingTime);
		totalEatingTime = rnEatingTime + eatingTimer;
		eatingTimer++;
	}

	private void hungry() {
		state = State.HUNGRY;
		hungryTimer++;
	}
	
	private void pickUpLeft() {
		leftChopStick.getlock().lock();
	}
	
	private void pickUpRight() {
		rightChopStick.getlock().lock();
	}
	
	private void putDown() {
		leftChopStick.getlock().unlock();
		rightChopStick.getlock().unlock();
		System.out.println("Philosopher " + id + " has put down both chopsticks.");
	}
}
