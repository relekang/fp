package no.ntnu.fp.client.gui.objects;

import java.awt.Color;
import java.awt.Graphics;

//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import no.ntnu.fp.client.gui.GuiConstants;
import no.ntnu.fp.common.model.Event;

public class EventLabel {
	
	private Color eventColor = GuiConstants.EVENT_PENDING;
	private Color textColor = GuiConstants.EVENT_TEXT_COLOR;
	private Event model;
	private int fromPx;
	private int toPx;
	
	public EventLabel(int fromPx, int toPx) {
		if(toPx-fromPx < GuiConstants.HOUR_HEIGHT/2)
			toPx += GuiConstants.HOUR_HEIGHT/2 - (toPx-fromPx);
		this.fromPx = calculatePixelLocation(fromPx);
		this.toPx = calculatePixelLocation(toPx);
	}
	
	public EventLabel(int fromPx, int toPx, Event event) {
		this(fromPx, toPx);
		this.model = event;
	}
	
	private int calculatePixelLocation(int px) {
		int hour = px / GuiConstants.HOUR_HEIGHT;
		int minute = px % GuiConstants.HOUR_HEIGHT;
		int quarter = (minute / (GuiConstants.HOUR_HEIGHT / 4));
		if(minute % (GuiConstants.HOUR_HEIGHT/4) > GuiConstants.HOUR_HEIGHT/8)
			quarter++;
		return hour* GuiConstants.HOUR_HEIGHT + quarter * (GuiConstants.HOUR_HEIGHT/4);
	}
	
	public void drawRepresentation(Graphics g) {
		g.setFont(GuiConstants.EVENT_LABEL_TITLE_FONT);
		int maxLines = (toPx - fromPx) / (GuiConstants.HOUR_HEIGHT / 4);
		int maxChars = 14;
		String[] titleWords = model.getTitle().split(" ");
		int line = 0;
		String text = "";
		for(int i = 0; i < titleWords.length; i++) {
			if(text.length() + titleWords[i].length() >= maxChars) {
				if(++line > maxLines)
					return;
				g.drawString(text, 0, getFromPixel()+line*13);
				text = "";
				i--;
			} else {
				text += titleWords[i]+ " ";
			}
		}
		g.drawString(text, 0, getFromPixel()+(++line)*13);

		if(++line > maxLines)
			return;
		g.setFont(GuiConstants.EVENT_LABEL_ROOM_FONT);
		String[] room = {model.getRoom().toString()};
		if(room[0].length() >= maxChars) {
			room = room[0].split(", ");
			g.drawString(room[0]+",", 0, getFromPixel()+line*13);
			g.drawString(room[1], 0, getFromPixel()+(line)*13+9);
		} else
			g.drawString(room[0], 0, getFromPixel()+line*13);
	}
	
	public String getRepresentation() {
		return model.getTitle() + "\n" + model.getRoom();
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
