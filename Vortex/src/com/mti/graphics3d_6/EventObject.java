package com.mti.graphics3d_6;

public class EventObject {

	double bhXLocation;
	double bhYLocation;
	

	public EventObject(double bhXLocation, double bhYLocation){
		this.bhXLocation = bhXLocation;
		this.bhYLocation = bhYLocation;
	}
	
	public void setBHXLocation(double BHXLocation) {
		this.bhXLocation = BHXLocation;
	}
	
	public void setBHYLocation(double BHYLocation) {
		this.bhYLocation = BHYLocation;
	}	

	public double getBHXLocation() {
		return this.bhXLocation;
	}
	
	public double getBHYLocation() {
		return this.bhYLocation;
	}	
	
}
