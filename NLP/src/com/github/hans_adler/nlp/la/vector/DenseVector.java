package com.github.hans_adler.nlp.la.vector;

public class DenseVector {
	
//	protected double[] valueArray;
//	
//	public DenseDoubleVector(int dimension) {
//		this.valueArray = new double[dimension];
//	}
//
//	@Override
//	public void set(int index, double value) {
//		valueArray[index] = value;
//	}
//
//	@Override
//	public void add(int index, double value) {
//		valueArray[index] += value;
//	}
//
//	@Override
//	public double get(int index) {
//		return valueArray[index];
//	}
//
//	@Override
//	public double sum() {
//		double result = 0.0;
//		for (int index = 0; index < valueArray.length; index++) {
//			result += valueArray[index];
//		}
//		return result;
//	}
//
//	@Override
//	public void clear() {
//		Arrays.fill(valueArray, 0.0);
//	}
//
//	@Override
//	public Iterator iterator() {
//		return new Iterator();
//	}
//	
//	@Override
//	public SparseIterator sparseIterator() {
//		return new Iterator();
//	}
//
//	public class Iterator extends DoubleEntry.SparseIterator {
//		
//		public Iterator() {
//			assert valueArray != null;
//			entry.index = -1;
//			entry.value = Double.NaN;
//		}
//		
//		@Override
//		public boolean hasNext() {
//			return entry.index+1 < valueArray.length;
//		}
//
//		@Override
//		public DoubleEntry next() {
//			if ((entry.index+1) >= valueArray.length) throw new NoSuchElementException();
//			entry.index++;
//			entry.value = valueArray[entry.index];
//			return entry;
//		}
//
//		@Override
//		public double getDefault() {
//			return 0.0;
//		}
//
//	}
//
}
