package hans_adler.linear_algebra.matrix;

/**
 * Sparse matrix with fixed sparseness structure and fixed dimensions.
 */
public class SparseMatrix extends OpenMatrix {

	public SparseMatrix(int idim, int jdim,
			double offset,
			boolean hasRows, boolean hasCols) {
		super(idim, jdim, offset, hasRows, hasCols);
	}
	public SparseMatrix(int idim, int jdim, double offset) {
		this(idim, jdim, offset, true, true);
	}
	public SparseMatrix(int idim, int jdim) {
		this(idim, jdim, 0.0, true, true);
	}
	
}
