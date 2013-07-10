package models.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Rest Response class
 * 
 * @author dbusser
 *         Will be serialized by Play
 * 
 */
public class RestResponse {

	// by default nothing goes wrong
	public boolean error = false;

	/**
	 * holds the actual REST result objects
	 * 
	 * @param objects
	 */
	public RestResponse(List<AbstractRestResource> objects) {
		for (AbstractRestResource ro : objects) {
			this.objects.add(ro);
		}
	}

	/**
	 * adds a rest resource to objects
	 * 
	 * @param object
	 */
	public RestResponse(AbstractRestResource object) {
		this.objects.add(object);
	}

	// multiple result objects
	public List<AbstractRestResource> objects = new ArrayList<AbstractRestResource>();

	/**
	 * If something goes wrong, call this method and set back an error
	 * 
	 * @return
	 */
	public RestResponse error() {
		this.error = true;
		this.objects = null; // don't send anything in case of errors
		return this;
	}
}
