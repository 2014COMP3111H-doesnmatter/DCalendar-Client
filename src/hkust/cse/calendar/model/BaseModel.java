package hkust.cse.calendar.model;

import hkust.cse.calendar.utils.EventSource;

public class BaseModel extends EventSource {
	protected long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}