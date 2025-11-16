package Packages;

/**
 * BILLREPORT: Abstract base class for order/bill information
 * 
 * Extended by: Information class
 * 
 * Purpose: Provides abstract method sendInfo() that is overridden
 * by Information to handle order information dialog/display.
 */
public abstract class BillReport
{
	/**
	 * Abstract method to send/display order information.
	 * Implemented by Information class to show order details dialog.
	 */
	public abstract void sendInfo();
}
