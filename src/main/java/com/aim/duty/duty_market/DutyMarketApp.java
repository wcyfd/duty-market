package com.aim.duty.duty_market;

import com.aim.duty.duty_market.cache.MarketCache;

/**
 * Hello world!
 *
 */
public class DutyMarketApp 
{
    public static void main( String[] args )
    {
    	MarketCache.init();
        System.out.println( "Hello World!" );
    }
}
