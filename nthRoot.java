package nthRoot;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/*
	NOTE: Always compile with '-d .' 
		And always run with <package-name>.<class-name> format
*/
public class nthRoot{
	private static final String DEFAULT_EXPONENT = "2";
    private static final String DEFAULT_GUESS_FACTOR = "4";
    private static final int DEFAULT_PRECISION = 2;
	
	public static void main( String... parameterList ){
		String value = parameterList[ 0 ];

		String exponent = DEFAULT_EXPONENT;
		if( parameterList.length >= 2 ){
            exponent = parameterList[ 1 ];
		}

        String guessFactor = DEFAULT_GUESS_FACTOR;
        if( parameterList.length >= 3 ){
            guessFactor = parameterList[ 2 ];
        }

        int precision = DEFAULT_PRECISION;
        if( parameterList.length == 4 ){
            try{
                precision = Integer.parseInt( parameterList[ 3 ] );
            }catch( Exception exception ){
                System.err.print( exception.getMessage( ) );
                return;
            }
        }

		BigDecimal root = nthRoot( value, exponent, guessFactor, precision );

		System.out.print( root.toString( ) );
	}

	public static final BigDecimal nthRoot( String value, String exponent, String guessFactor, int precision ){
		BigDecimal rootExponent = new BigDecimal( exponent );
		BigDecimal baseValue = new BigDecimal( value );
		BigDecimal guessRoot = baseValue.divide( new BigDecimal( guessFactor ) );

		/*
			The base precision is the length of numbers after the decimal point.
			This will be used to validate the guess root.
		*/
        int basePrecision = 1;
        if( baseValue.toString( ).split( "\\." ).length > 1 ){
            basePrecision = baseValue.toString( ).split( "\\." )[ 1 ].length( );
        }

        /*
        	This is based on Newton's Iteration Method

        		guessRoot_index = ( 1 / exponent ) * ( ( ( exponent - 1 ) * guessRoot_index ) + ( baseValue / ( guessRoot_index ^ exponent - 1 ) ) )

        	Phase A involves this equation: ( ( exponent - 1 ) * guessRoot_index )
        	Phase B involves this equation: ( baseValue / ( guessRoot_index ^ exponent - 1 ) )
        	Phase C involves this equation: ( 1 / exponent )

        	guessRoot at the specific index is therefore equal to C * ( A + B )
        */
		do{
			BigDecimal phaseA = rootExponent.subtract( BigDecimal.ONE ).multiply( guessRoot );

			BigDecimal phaseB = baseValue.divide( guessRoot.pow( rootExponent.subtract( BigDecimal.ONE ).intValue( ) ), precision, RoundingMode.HALF_UP );

			BigDecimal phaseC = BigDecimal.ONE.divide( rootExponent, precision, RoundingMode.HALF_UP );

			guessRoot = phaseC.multiply( phaseA.add( phaseB ) ).setScale( precision, RoundingMode.HALF_UP );

			//We increase the precision to match the evaluation.
			//NOTE: Increasing the precision means increasing the number of digits after the dot.
			precision++;

		}while( guessRoot.pow( rootExponent.intValue( ) ).setScale( basePrecision, RoundingMode.HALF_UP ).compareTo( baseValue ) != 0 );

		return guessRoot;
	}
}