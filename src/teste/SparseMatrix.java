package teste;

import gnu.trove.TLongDoubleHashMap;
import gnu.trove.TLongDoubleIterator;

import java.util.Random;
public class SparseMatrix {
        private int[] dim;
        public TLongDoubleHashMap vector = null;
        public SparseMatrix(){
                vector = new TLongDoubleHashMap(100,0.8f);
        }
        public SparseMatrix(int[] dim){
                this();
                this.dim = dim;
        }
        public void set(int[] indices, double val){
                long idx = getIdx(indices);
                set(idx,val);
        }
        public void set(long index, double value)       {
                vector.put(index, value);
        }
        public double elementAt(int[] indices)  {
                long idx = getIdx(indices);
                return vector.get(idx);
        }
        public double elementAt(long index)     {
                return vector.get(index);
        }
        public int[] size()     {
                return dim;
        }
        public SparseMatrix mutiplyMatrix(SparseMatrix a){
                int m = this.size()[0];
                int n = a.size()[1];
                int dim[]={m,n};
                SparseMatrix matrix = new SparseMatrix(dim);
                TLongDoubleIterator it = this.vector.iterator();
                TLongDoubleIterator ita = a.vector.iterator();
                for (int i = this.vector.size(); i-- > 0;)
                {
                        it.advance();
                        ita = a.vector.iterator();
                        for(int j = a.vector.size(); j-- > 0;)
                        {
                                ita.advance();
                                if(this.getIndices(it.key())[1]==a.getIndices(ita.key())[0])
                                {
                                        int []indices = {this.getIndices(it.key())[0],a.getIndices(ita.key())[1]};
                                        matrix.set(indices, matrix.elementAt(indices)+it.value()*ita.value());
                                }
                        }
                }
                return matrix;
        }
        public long getIdx(int[] indices){
                long idx=0;
                int i=indices.length-1;
                for(int j=0;i>0&&j<indices.length-1;i--,j++)
                        idx += indices[i]*dim[j];
                idx += indices[0];
                return idx;
        }
        public int[] getIndices(long idx)
        {
                int xIndices = (int)idx%this.size()[0];
                int yIndices = (int)(idx-xIndices)/this.size()[0];
                int []Indices = {xIndices,yIndices};
                return Indices;
        }
        public SparseMatrix clone(){
                SparseMatrix mat = new SparseMatrix();
                mat.dim = this.dim;
                mat.vector = (TLongDoubleHashMap) this.vector.clone();
                return mat;
        }
        public static void main(String[] args) {
        }
        public static SparseMatrix random(int[] dim) {
                Random r = new Random();
                SparseMatrix matrix = new SparseMatrix(dim);
                for(int i=0;i<dim[0];i++)
                        for(int j=0;j<dim[1];j++)
                        {
                                int []indices={i,j};
                                matrix.set(indices, r.nextDouble());
                        }
                return matrix;
        }
        public void minus(SparseMatrix mat) {
                TLongDoubleIterator it = mat.vector.iterator();
                for (int i = mat.vector.size(); i-- > 0;)
                {
                        it.advance();
                        vector.put(it.key(),vector.get(it.key()) - it.value());
                }
        }
        public void add(SparseMatrix mat) {
                TLongDoubleIterator it = mat.vector.iterator();
                for (int i = mat.vector.size(); i-- > 0;)
                {
                        it.advance();
                        vector.put(it.key(),vector.get(it.key()) + it.value());
                }
        }
        public double l1Norm()  {
                double norm = 0;
                TLongDoubleIterator it = vector.iterator();
                for (int i = vector.size(); i-- > 0;) {
                        it.advance();
                        norm += Math.abs(it.value());
                }
                return norm;
        }
        public double l2Norm()  {
                double norm = 0;
                TLongDoubleIterator it = vector.iterator();
                for (int i = vector.size(); i-- > 0;) {
                        it.advance();
                        norm += it.value()*it.value();
                }
                return Math.sqrt(norm);
        }
        public double infinityNorm()    {
                double norm = 0;
                TLongDoubleIterator it = vector.iterator();
                for (int i = vector.size(); i-- > 0;) {
                        it.advance();
                        if (Math.abs(it.value()) > norm)
                                norm = Math.abs(it.value());
                }
                it=null;
                return norm;
        }
        public SparseMatrix trans() {
                int []newdim = {dim[1],dim[0]};
                SparseMatrix newmat = new SparseMatrix(newdim);
                TLongDoubleIterator itW = vector.iterator();
                for (int i = vector.size(); i-- > 0;)
                {
                        itW.advance();
                        int x = getIndices(itW.key())[0];
                        int y = getIndices(itW.key())[1];
                        int []TranWIndices = {y,x};
                        newmat.set(TranWIndices,itW.value());
                }
                return newmat;
        }
}