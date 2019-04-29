public class ClimbPerformance {

	private static final double[][] timeFuelDistanceToClimb = {{74,730,0,0,0}, {73,695,1,0.4,2}, {73,655,3,0.8,4}, {73,620,4,1.2,6}, {73,600,6,1.5,8}, {73,550,8,1.9,10}, {73,505,10,2.2,13},
	{73,455,12,2.6,16}, {72,410,14,3.0,19}, {72,360,17,3.4,22}, {72,315,20,3.9,27}, {72,265,24,4.4,32}, {72,220,28,5.0,38}};
	private int rawStartingAlt;
	private int rawEndingAlt;
	private int startingAltitude;
	private int endingAltitude;
	private int climbSpeed;
	private double climbRate;
	private double timeToClimb;
	private double fuelUsedToClimb;
	private double distanceToClimb;

	public ClimbPerformance(int startingAltitude, int endingAltitude) {
		//Takes the altitudes, divides them by 1000 and rounds, and uses that number as the index for performance calculations
		this.rawStartingAlt = startingAltitude;
		this.rawEndingAlt = endingAltitude;
		this.startingAltitude = (int)(((double)(startingAltitude)/1000) + .5);
		this.endingAltitude = (int)(((double)(endingAltitude)/1000) + .5);
		this.climbSpeed = (int)((timeFuelDistanceToClimb[this.endingAltitude][0] + timeFuelDistanceToClimb[this.startingAltitude][0]) / 2);
		this.climbRate = (timeFuelDistanceToClimb[this.endingAltitude][1] + timeFuelDistanceToClimb[this.startingAltitude][1]) / 2;
		this.timeToClimb = timeFuelDistanceToClimb[this.endingAltitude][2] - timeFuelDistanceToClimb[this.startingAltitude][2];
		this.fuelUsedToClimb = timeFuelDistanceToClimb[this.endingAltitude][3] - timeFuelDistanceToClimb[this.startingAltitude][3];
		this.fuelUsedToClimb = (double)((int)(this.fuelUsedToClimb * 100 + .5)) / 100;
		this.distanceToClimb = timeFuelDistanceToClimb[this.endingAltitude][4] - timeFuelDistanceToClimb[this.startingAltitude][4];
	}

	public int getStartingAltitude() {return rawStartingAlt;}
	public int getEndingAltitude() {return rawEndingAlt;}
	public int getClimbSpeed() {return climbSpeed;}
	public double getClimbRate() {return climbRate;}
	public double getTimeToClimb() {return timeToClimb;}
	public double getFuelUsedToClimb() {return fuelUsedToClimb;}
	public double getDistanceToClimb() {return distanceToClimb;}

	public static void main(String[] args) {
		System.out.println("Creating: ClimbPerformance(1000, 2000)");
		ClimbPerformance perf = new ClimbPerformance(1000, 2000);
		System.out.println("Starting Alt: " + perf.getStartingAltitude());
		System.out.println("Ending Alt: " + perf.getEndingAltitude());
		System.out.println("Climb Speed: " + perf.getClimbSpeed());
		System.out.println("Climb Rate: " + perf.getClimbRate());
		System.out.println("Time To Climb: " + perf.getTimeToClimb());
		System.out.println("Fuel used to Climb: " + perf.getFuelUsedToClimb());
		System.out.println("Distance Used to Climb: " + perf.getDistanceToClimb());
	}
}