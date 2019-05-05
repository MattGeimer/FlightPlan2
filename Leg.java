import java.lang.Math;

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
	private double windCorrectionAngle;
	private double trueHeading;
	private double magneticHeading;
	private double deviation;
	private double courseHeading;
	private double distance;
	private double estimatedTimeEnroute;
	private double estimatedFuelConsumption;
	private double cruiseFuelConsumption;

	public Leg(String startPoint, String endPoint, int trueCourse, int trueAirSpeed, int altitude, int distance, int windDirection, int windVelocity, int magneticVariation, double deviation, double cruiseFuelConsumption, boolean isClimbingLeg) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.trueCourse = trueCourse;
		this.trueAirSpeed = trueAirSpeed;
		this.altitude = altitude;
		this.distance = distance;
		this.windDirection = windDirection;
		this.windVelocity = windVelocity;
		this.magneticVariation = magneticVariation;
		this.deviation = deviation;
		this.cruiseFuelConsumption = cruiseFuelConsumption;
		this.isClimbingLeg = isClimbingLeg;
	}

	//Returns distance to climb so that the calling program can reassign distance value for the non-climbing leg
	private double calculatePerformance(int startAltitude, int endAltitude) {
		climbPerformance = new ClimbPerformance(startAltitude, endAltitude);
		this.trueAirSpeed = climbPerformance.getClimbSpeed();
		this.estimatedFuelConsumption = climbPerformance.getFuelUsedToClimb();
		this.estimatedTimeEnroute = climbPerformance.getTimeToClimb();
		windCorrectionCalculation();
		magneticHeadingCalculation();
		courseHeadingCalculation(deviation);
		distance = distance - (groundSpeed * estimatedTimeEnroute / 60);
		return distance;
	}

	private void calculatePerformance() {
		windCorrectionCalculation();
		magneticHeadingCalculation();
		courseHeadingCalculation(deviation);
		this.estimatedTimeEnroute = (double)((int)(distance / groundSpeed * 60) * 100) / 100;
		this.estimatedFuelConsumption = cruiseFuelConsumption * estimatedTimeEnroute / 60;
	}

	private void windCorrectionCalculation() {
		int windVector;
		//Find the vector of wind b/c wind is given in from format instead of to format (eg "winds out of xxx @ xx")
		if(windDirection <= 180) {
			windVector = windDirection + 180;
		} else {
			windVector = windDirection - 180;
		}

		//Find the angle between trueCourse and windVector
		double windAngle;
		if(trueCourse == windVector) {windAngle = 0.0;}
		else if(trueCourse > windVector) {
			windAngle = trueCourse - windVector;
		} else {
			windAngle = windVector - trueCourse;
		}
		windAngle = Math.toRadians(windAngle);
		double windCorrectionForce = windVelocity * Math.sin(windAngle); //Find the y component of the wind vector
		double windCorrectionSpeed = windVelocity * Math.cos(windAngle); //Find the x component of the wind vector
		double groundSpeed = windCorrectionSpeed + trueAirSpeed; //Find groundspeed by adding wind and true airspeed components
		groundSpeed = (double)((int)(groundSpeed * 100)) / 100; //Round groundspeed
		double windCorrectionAngle = Math.atan(windCorrectionForce / groundSpeed); //Find wind correction angle as arctan(windCorrectionForce / groundSpeed)
		windCorrectionAngle = Math.toDegrees(windCorrectionAngle); //Convert wind correction angle to degrees
		windCorrectionAngle = (double)((int)(windCorrectionAngle * 100)) / 100; //Round wind correction angle
		this.groundSpeed = groundSpeed; //Set leg's groundspeed
		this.windCorrectionAngle = windCorrectionAngle; //Set leg's wind correction angle
		trueHeading = trueCourse + windCorrectionAngle; //Set leg's true heading
	}

	private void magneticHeadingCalculation() {
		magneticHeading = trueHeading + magneticVariation;
	}

	private void courseHeadingCalculation(double deviation) {
		this.deviation = deviation;
		this.courseHeading = magneticHeading + deviation;
	}

	public String getStartPoint() {return startPoint;}
	public String getEndPoint() {return endPoint;}
	public int getTrueCourse() {return trueCourse;}
	public int getTrueAirspeed() {return trueAirSpeed;}
	public int getWindDirection() {return windDirection;}
	public int getWindVelocity() {return windVelocity;}
	public int getMagneticVariation() {return magneticVariation;}
	public boolean getIsClimbingLeg() {return isClimbingLeg;}
	public int getAltitude() {return altitude;}
	public ClimbPerformance getClimbPerformance() {return climbPerformance;}
	public double getGroundSpeed() {return groundSpeed;}
	public double getWindCorrectionAngle() {return windCorrectionAngle;}
	public double getTrueHeading() {return trueHeading;}
	public double getMagneticHeading() {return magneticHeading;}
	public double getDeviation() {return deviation;}
	public double getCourseHeading() {return courseHeading;}
	public double getDistance() {return distance;}
	public double getEstimatedTimeEnroute() {return estimatedTimeEnroute;}
	public double getEstimatedFuelConsumption() {return estimatedFuelConsumption;}

	public String toString() {
		if(!isClimbingLeg) {
			return "Start Point: " + this.getStartPoint() + "\n" + "End Point: " + this.getEndPoint() + "\n" + 
			"ALT: " + this.getAltitude() + "\n" + "Wind Direction: " + this.getWindDirection() + "\n" +
			"Wind Velocity: " + this.getWindVelocity() + "\n" + "TAS: " + this.getTrueAirspeed() + "\n" + 
			"TC: " + this.getTrueCourse() + "\n" + "WCA: " + this.getWindCorrectionAngle() + "\n" + 
			"TH: " + this.getTrueHeading() + "\n" + "Var: " + this.getMagneticVariation() + "\n" + 
			"MH: " + this.getMagneticHeading() + "\n" + "Dev: " + this.getDeviation() + "\n" +
			"CH: " + this.getCourseHeading() + "\n" + "GS: " + this.getGroundSpeed() + "\n" +
			"Dist: " + this.getDistance() + "\n" + "ETE: " + this.getEstimatedTimeEnroute() + "\n" + 
			"Fuel: " + this.getEstimatedFuelConsumption();
		} else {
			return "Start Point: " + this.getStartPoint() + "\n" + "End Point: " + this.getEndPoint() + "\n" + 
			"ALT: " + "CLIMB" + "\n" + "Wind Direction: " + this.getWindDirection() + "\n" +
			"Wind Velocity: " + this.getWindVelocity() + "\n" + "TAS: " + this.getTrueAirspeed() + "\n" + 
			"TC: " + this.getTrueCourse() + "\n" + "WCA: " + this.getWindCorrectionAngle() + "\n" + 
			"TH: " + this.getTrueHeading() + "\n" + "Var: " + this.getMagneticVariation() + "\n" +
			"MH: " + this.getMagneticHeading() + "\n" + "Dev: " + this.getDeviation() + "\n" +
			"CH: " + this.getCourseHeading() + "\n" + "GS: " + this.getGroundSpeed() + "\n" +
			"Dist: " + this.getDistance() + "\n" + "ETE: " + this.getEstimatedTimeEnroute() + "\n" + 
			"Fuel: " + this.getEstimatedFuelConsumption();
		}
	}
	public static void main(String[] args) {
		Leg leg1 = new Leg("Lake in the hills", "Marengo", 287, 117, 4500, 13, 220, 26, 3, 0, 9.9, false);
		leg1.calculatePerformance();
		System.out.println(leg1);

		Leg leg2 = new Leg("Bangs Lake", "TOC", 315, 119, 2500, 8, 220, 26, 3, 0, 9.9, true);
		System.out.println("Distance to TOC: " + leg2.calculatePerformance(leg2.getAltitude(), 4500));
		System.out.println(leg2);
	}
}