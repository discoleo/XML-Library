package data;

public class BoolPair<T> {
	public T _E = null;
	public boolean isE = false;
	
	public BoolPair() {}
	public BoolPair(final T _E, final boolean isE) {
		this._E  = _E;
		this.isE = isE;
	}
}
