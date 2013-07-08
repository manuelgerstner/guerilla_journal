package models.rest;

import java.util.ArrayList;
import java.util.List;

public class RestResponse {

	public RestResponse(List<AbstractRestResource> objects) {
		for (AbstractRestResource ro : objects) {
			this.objects.add(ro);
		}
	}

	public RestResponse(AbstractRestResource object) {
		this.objects.add(object);
	}

	public boolean error = false; // default

	// multiple
	public List<AbstractRestResource> objects = new ArrayList<AbstractRestResource>();

	public RestResponse error() {
		this.error = true;
		return this;
	}
}
