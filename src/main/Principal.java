package main;

import java.util.Random;

import nmf.NMF;

import org.ejml.data.SimpleMatrix;

public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleMatrix sm = SimpleMatrix.random(5, 5, 0, 1, new Random());
		sm.set(3, 3, 0);
		sm.print();
		
		System.out.println("--------Resultado-----------");
		NMF.nmf(sm, 3).produto().print();
	}

}
