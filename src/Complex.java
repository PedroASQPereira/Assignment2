

public class Complex {

	double re;
	double im;
	
	public Complex(double re, double im) {
		
		this.re =re;
		this.im=im;
	}
	
	public Complex buildComplex(double mag, double angle) {
		Complex complex ;
		double re, im;
		
		re = mag*Math.cos((angle*Math.PI)/180);
		im = mag*Math.sin((angle*Math.PI)/180);
		
		complex = new Complex(re,im);
		
		return complex;
	}
	
	public Complex subtraction(Complex a){
		
		this.re=a.re-this.re;
		this.im=a.im-this.im;
		return this;
	}
	
	public double getmagnitude(Complex x) {
		double mag=0;
		
		mag = Math.sqrt( Math.pow(x.im,2) + Math.pow(x.re,2) );

		return mag;
	}
		

}







