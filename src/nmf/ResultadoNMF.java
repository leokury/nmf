package nmf;

import org.ejml.data.SimpleMatrix;

public class ResultadoNMF {
	
	SimpleMatrix w, h;

	public ResultadoNMF(SimpleMatrix w, SimpleMatrix h) {
		super();
		this.w = w;
		this.h = h;
	}

	public SimpleMatrix getW() {
		return w;
	}

	public void setW(SimpleMatrix w) {
		this.w = w;
	}

	public SimpleMatrix getH() {
		return h;
	}

	public void setH(SimpleMatrix h) {
		this.h = h;
	}
	
	public SimpleMatrix produto(){
		return w.mult(h);
	}

}
