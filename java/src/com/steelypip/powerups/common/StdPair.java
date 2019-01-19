package com.steelypip.powerups.common;

import java.util.Map;

public class StdPair< T, U > implements Pair< T, U >, Map.Entry< T, U > {

	private T first;
	private U second;
	
	public StdPair( T field1, U field2 ) {
		super();
		this.first = field1;
		this.second = field2;
	}

	/* (non-Javadoc)
	 * @see com.steelypip.powerups.common.PairView#getFirst()
	 */
	public T getFirst() {
		return this.first;
	}

	/* (non-Javadoc)
	 * @see com.steelypip.powerups.common.PairView#setFirst(T)
	 */
	public void setFirst( T first ) {
		this.first = first;
	}

	/* (non-Javadoc)
	 * @see com.steelypip.powerups.common.PairView#getSecond()
	 */
	public U getSecond() {
		return this.second;
	}

	/* (non-Javadoc)
	 * @see com.steelypip.powerups.common.PairView#setSecond(U)
	 */
	public void setSecond( U second ) {
		this.second = second;
	}

	@Override
	public T getKey() {
		return this.first;
	}

	@Override
	public U getValue() {
		return this.second;
	}

	@Override
	public U setValue( U value ) {
		U old = this.second;
		this.second = value;
		return old;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof Pair ) {
			T t1 = this.first;
			@SuppressWarnings("rawtypes")
			Object t2 = ((Pair)obj).getFirst();
			
			if ( t1 == null && t2 == null ) return true;
			if ( t1 == null || t2 == null ) return false;
			
			if ( ! t1.equals( t2 ) ) return false;
			
			U u1 = this.second;
			@SuppressWarnings("rawtypes")
			Object u2 = ((Pair)obj).getSecond();
			
			if ( u1 == null && u2 == null ) return true;
			if ( u1 == null || u2 == null ) return false;
			
			return u1.equals( u2 );
			
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.getFirst().hashCode() ^ this.getSecond().hashCode();
	}
		
	
}

