package no.ntnu.fp.gui.objects;

import java.awt.Color;
import java.sql.Date;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import no.ntnu.fp.gui.Constants;
import no.ntnu.fp.model.Event;

public class EventLabel {
	
	private Color eventColor = Constants.EVENT_PENDING;
	private Color textColor = Constants.EVENT_TEXT_COLOR;
	private Event model;
	private int fromPx;
	private int toPx;
	
	public EventLabel(int fromPx, int toPx) {
		if(toPx-fromPx < Constants.HOUR_HEIGHT/2)
			toPx += Constants.HOUR_HEIGHT/2 - (toPx-fromPx);
		this.fromPx = calculatePixelLocation(fromPx);
		this.toPx = calculatePixelLocation(toPx);
	}
	
	private int calculatePixelLocation(int px) {
		int hour = px / Constants.HOUR_HEIGHT;
		int minute = px % Constants.HOUR_HEIGHT;
		int quarter = (minute / (Constants.HOUR_HEIGHT / 4));
		if(minute % (Constants.HOUR_HEIGHT/4) > Constants.HOUR_HEIGHT/8) 
			quarter++;
//		System.out.println("from: " + fromHour + " " + fromMinute);
//		System.out.println("to : " + toHour + " " + toMinute);
//		System.out.println("quarters : " + fromQuarter + " " + toQuarter);
		return hour*Constants.HOUR_HEIGHT + quarter * (Constants.HOUR_HEIGHT/4);
	}
	
	public int getFromPixel() {
		return fromPx;
	}
	
	public int getToPixel() {
		return toPx;
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
