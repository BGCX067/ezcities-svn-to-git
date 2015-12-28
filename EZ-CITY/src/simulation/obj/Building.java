package simulation.obj;

import java.util.ArrayList;

import draw.Geometry.Poly2DEx;

public class Building extends AbstractObject {

	private Poly2DEx footprint = null;
	private int nfloor;
	private float height;
	private int function;
	
	
	
	public Building() {
		ID = INVALID_VALUE;
		Type = BUILDING;
	}

	public void setId(int _id) {
		ID = _id;
	}

	public void setFootprint(Poly2DEx _fps) {
		footprint = _fps;
	}

	public Poly2DEx  getFootprint() {
		return this.footprint;
	}
	
	
	public void setHeight(float _height) {
		height = _height;
	}

	public double  getHeight() {
		return this.height;
	}

	public void setNfloor(int n) {
		nfloor = n;
	}

	public double getNfloor() {
		return nfloor;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		if (ID == INVALID_VALUE) {
			return false;
		}
		return true;
	}

	@Override
	public void setInvalid() {
		// TODO Auto-generated method stub

	}

}
