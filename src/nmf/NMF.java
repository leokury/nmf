package nmf;

import java.util.Random;

import org.ejml.data.SimpleMatrix;
import org.ejml.ops.NormOps;

public class NMF {

	private static int maxInteracoes = 5000;
	
	
	/**
	 * Calcula as matrizes fatores da matrix D (n x m)
	 * @param d Matriz original
	 * @param r Número de características
	 * @return ResultadoNMF com matriz w ( n x r ) e H (r x m)
	 */
	public static ResultadoNMF nmf(SimpleMatrix d, int r) {
		int n = d.numRows();
		int m = d.numCols();
		SimpleMatrix w = initMatrizFator(n, r);
		SimpleMatrix h = initMatrizFator(r, m);

		for (int i = 0; i < maxInteracoes; i++) {
			// calcula o produto apenas 1 vez
			SimpleMatrix wh = w.mult(h);
			
			SimpleMatrix wt = w.transpose();
			// atualiza fator H
			for (int x = 0; x < r; x++) {
				for (int y = 0; y < m; y++) {
					double value = (wt.mult(d).get(x, y))
							/ (wt.mult(wh).get(x, y));
					h.set(x, y, h.get(x, y) * value);
				}
			}

			// atualiza fator W
			for (int x = 0; x < n; x++) {
				for (int y = 0; y < r; y++) {
					SimpleMatrix ht = h.transpose();
					double value = (d.mult(ht).get(x, y))
							/ (wh.mult(ht).get(x, y));
					w.set(x, y, w.get(x, y) * value);
				}
			}
			
			
			double erro = NormOps.normP2(d.minus(w.mult(h)).getMatrix());
			if( erro == 0) break;
	
		}
		
		return new ResultadoNMF(w, h);
	}
	
	

	/**
	 * Inicializa a matriz fator n x r
	 * @param n Linhas
	 * @param r Colunas
	 * @return
	 */
	private static SimpleMatrix initMatrizFator(int n, int r) {
		return initMatrizFatorRandomica(n, r);
	}

	/**
	 * Inicializa a matriz fator com valores aleatorios
	 * @param n Linhas
	 * @param r Colunas
	 * @return
	 */
	private static SimpleMatrix initMatrizFatorRandomica(int n, int r) {
		return SimpleMatrix.random(n, r, 0, 1, new Random());
	}

}
