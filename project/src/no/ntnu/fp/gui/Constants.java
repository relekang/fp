package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.Font;

public class Constants {
	
//	Bredde og hoyde til komponenter i CalendarDayPanel og CalendarDayBox
	public static final int HOURS = 24;
	public static final int HOUR_HEIGHT = 50;
	public static final int HEIGHT_CANVAS = HOURS*HOUR_HEIGHT;
	public static final int NUM_VISIBLE_CELLS = 8;
	public static final int DAYBOX_HEIHGT = NUM_VISIBLE_CELLS*HOUR_HEIGHT;
	public static final int WIDTH_CANVAS = 120;
	
//	Strings
	public static final String MONDAY = "Monday";
	public static final String TUESDAY = "Tuesday";
	public static final String WEDNESDAY = "Wednesday";
	public static final String THURSDAY = "Thursday";
	public static final String FRIDAY = "Friday";
	public static final String SATURDAY = "Saturday";
	public static final String SUNDAY = "Sunday";
	public static final String[] DAYS = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
	
//	Fonts
	public static final Font WEEKDAY_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Font HOUR_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
	
//	Colors
	public static final Color STD_BACKGROUND = Color.LIGHT_GRAY;
	public static final Color STD_FOREGROUND = Color.WHITE; //TODO: bedre navn paa variabel :P
	public static final Color EVENT_ACCEPTED = new Color(131, 240, 60); //light green
	public static final Color EVENT_PENDING = new Color(255, 180, 64); //light yellow
	public static final Color EVENT_DECLINED = new Color(251, 63, 81); //light red
	public static final Color DRAG_NEW_EVENT = new Color(179, 209, 232); //light blue
	
	public enum EventType {
		PERSONAL, BUSNIESS, BUSINESS_ADMIN;
	}
	
}
