package no.ntnu.fp.client.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class GuiConstants {
	
//	Bredde og hoyde til komponenter i CalendarDayPanel og CalendarDayBox
	public static final int HOURS = 24;
	public static final int HOUR_HEIGHT = 60;
	public static final int CANVAS_HEIGHT = HOURS*HOUR_HEIGHT;
	public static final int CANVAS_WIDTH = 120;
	public static final int NUM_VISIBLE_CELLS = 8;
	public static final int DAYBOX_HEIGHT = NUM_VISIBLE_CELLS*HOUR_HEIGHT;
	
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
	public static final Font EVENT_LABEL_TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
	public static final Font EVENT_LABEL_ROOM_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
	public static final Font NOTIFICATIONPANE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	
//	Colors
	public static final Color EVENT_TEXT_COLOR = Color.BLACK;
	public static final Color STD_BACKGROUND = Color.LIGHT_GRAY;
	public static final Color STD_FOREGROUND = Color.WHITE; //TODO: bedre navn paa variabel :P
	public static final Color EVENT_ACCEPTED = new Color(131, 240, 60, 200); //light green
	public static final Color EVENT_PENDING = new Color(255, 180, 64, 200); //light yellow
	public static final Color EVENT_DECLINED = new Color(251, 63, 81, 200); //light red
	public static final Color DRAG_NEW_EVENT = new Color(179, 209, 232); //light blue
	public static final Color SWING_FRAME_GRAY = new Color(238, 238, 238, 200); //The gray background from the Swing frames

	public enum EventType {
		PERSONAL, BUSNIESS, BUSINESS_ADMIN;
	}
	
//	Borders
	public static final Border EMPTY_BORDER_10 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	public static final Border EMPTY_BORDER_5 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	public static final Border EMPTY_BORDER_1 = BorderFactory.createEmptyBorder(1, 1, 1, 1);
	public static final Border EMPTY_BORDER_0 = BorderFactory.createEmptyBorder(0, 0, 0, 0);
	
//	Scroll-speed
	public static final int STD_SCROLL_SPEED = 16;
	
//	Drawable rectangle prefs
	public static final int RECT_CURVE = 10;
	public static final int RECT_WIDTH = CANVAS_WIDTH - 10;
}
