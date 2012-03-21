package no.ntnu.fp.gui.objects;

import java.awt.Color;
import java.sql.Date;

import no.ntnu.fp.gui.Constants;
import no.ntnu.fp.model.Event;

public class EventLabel {
	
	private Color eventColor = Constants.EVENT_PENDING;
	private Color textColor = Constants.STD_FOREGROUND;
	private Event model;
	private int from, fromHour;
	private int to, toHour;
	
	public EventLabel(int from, int to) {
		this.from = from;
		this.to = to;
		int fromHour = from / Constants.HOUR_HEIGHT;
		int fromMinute = from % Constants.HOUR_HEIGHT; 
//		Date dateFrom = new Date();
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
