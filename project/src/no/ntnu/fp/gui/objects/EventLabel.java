package no.ntnu.fp.gui.objects;

import java.awt.Color;
import java.sql.Date;

import no.ntnu.fp.gui.Constants;
import no.ntnu.fp.model.Event;

public class EventLabel {
	
	private Color eventColor = Constants.EVENT_PENDING;
	private Color textColor = Constants.STD_FOREGROUND;
	private Event model;
	private int from;
	private int to;
	
	public EventLabel(int from, int to) {
//		TODO: fix dette!!
		int fromHour = from / Constants.HOUR_HEIGHT;
		int fromMinute = (from % Constants.HOUR_HEIGHT)/(Constants.HOUR_HEIGHT/4);
		int toHour = 0;
		int toMinute = 0;
		if(from == to) { 
			toHour = fromHour + 1;
			
		} else {
			toHour = to / Constants.HOUR_HEIGHT;
			toMinute = (to % Constants.HOUR_HEIGHT)/(Constants.HOUR_HEIGHT/4);
			if(fromHour == toHour && (toMinute == 1 || toMinute == 0)) 
				toMinute = 2;
		}
		this.from = fromHour*Constants.HOUR_HEIGHT + fromMinute*(Constants.HOUR_HEIGHT/4);
		this.to = toHour*Constants.HOUR_HEIGHT + toMinute*(Constants.HOUR_HEIGHT/4);
	}
	
	public int getFromPixel() {
		return from;
	}
	
	public int getToPixel() {
		return to;
	}

	public Color getEventColor() {
		return eventColor;
	}

	public void setEventColor(Color eventColor) {
		this.eventColor = eventColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Event getModel() {
		return model;
	}

	public void setModel(Event model) {
		this.model = model;
	}
	
}
