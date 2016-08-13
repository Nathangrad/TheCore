package com.above.interfaces;

/**
 * IDataProvider API interface for obtaining and setting data
 * 
 * @author NathanGrad
 * 
 * @usage Abstract classes not to be used implementing this
 *
 * @param <i>
 *            Data type of input required by API functions
 * @param <o>
 *            Data type of output provided by API functions
 */
public interface IDataProvider<i, o> {

	/**
	 * Get the value o using the API
	 * 
	 * @param input
	 * @return
	 */
	o get(i input);

	/**
	 * Set the value o for type i using the API
	 * 
	 * @param input
	 * @param setterVariable
	 */
	void set(i input, o setterVariable);

	/**
	 * Update the value o for type i using the API
	 * 
	 * @param input
	 * @param updaterVariable
	 */
	void update(i input, o updaterVariable);

	/**
	 * Set the content for type i with default value of o using the API
	 * 
	 * @param input
	 * @param setterVariable
	 */
	void setup(i input, o setterVariable);

}
