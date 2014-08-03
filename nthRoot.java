package nthRoot;

import java.math.BigDecimal;
import java.math.BigInteger;

/*
	NOTE: Always compile with '-d .' 
		And always run with <package-name>.<class-name> format
*/
public class nthRoot{
	private static final String DEFAULT_EXPONENT = "2";
	
	public static void main( String... parameterList ){
		String value = parameterList[ 0 ];

		String exponent = DEFAULT_EXPONENT;

		if( parameterList.length == 2 ){
			try{
				exponent = parameterList[ 1 ];
			}catch( Exception exception ){
				System.err.print( exception.getMessage( ) );
				return;
			}
		}

		BigDecimal root = nthRoot( value, exponent );
		System.out.print( root.toString( ) );
	}

	public static final BigDecimal nthRoot( String value, String exponent ){
		BigDecimal rootExponent = new BigDecimal( exponent );
		BigDecimal baseValue = new BigDecimal( value );
		BigDecimal guessRoot = baseValue.divide( new BigDecimal( "2" ) );
		
		BigDecimal previousGuessRoot = BigDecimal.ZERO;
		do{
			previousGuessRoot = new BigDecimal( guessRoot.toString( ) );

			BigDecimal phaseA = rootExponent.subtract( BigDecimal.ONE ).multiply( guessRoot );

			BigDecimal phaseB = baseValue.divide( guessRoot.pow( rootExponent.subtract( BigDecimal.ONE ) ) );

			BigDecimal phaseC = BigDecimal.ONE.divide( rootExponent );

			guessRoot = phaseC.multiply( phaseA.add( phaseB ) );

		}while( guessRoot.compareTo( previousGuessRoot ) == 0 );
		
		return guessRoot;		
	}
}