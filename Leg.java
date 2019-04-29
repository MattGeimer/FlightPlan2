public class Leg {

	//Declare variables
	private String startPoint;
	private String endPoint;
	private int trueCourse;
	private int trueAirSpeed;
	private int windDirection;
	private int windVelocity;
	private int magneticVariation;
	private boolean isClimbingLeg;
	private int altitude;
	private ClimbPerformance climbPerformance;
	private double groundSpeed;
	private double trueHeading;
	private double magneticHeading;
	private double courseHeading;
	private double distance;
	private double estimatedTimeEnroute;
	private double estimatedFuelConsumption;

	public Leg() {
		this.trueCourse = 0;
		this.trueAirSpeed = 0;
		this.altitude = 0;
		this.distance = 0;
	}

	public Leg(int trueCourse, int trueAirSpeed, int altitude, int distance) {
		this.trueCourse = trueCourse;
		this.trueAirSpeed = trueAirSpeed;
		this.altitude = altitude;
		this.distance = distance;
	}
}