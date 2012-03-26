package no.ntnu.fp.client.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
	public static final Font DATELABEL_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 12 );
    public static final Font DATELABEL_NORMAL = new Font(Font.SANS_SERIF, Font.PLAIN, 12 );

//	Colors
	public static final Color EVENT_TEXT_COLOR = Color.BLACK;
	public static final Color STD_BACKGROUND = /*new Color(255, 240, 165);*/Color.WHITE;
	public static final Color STD_FOREGROUND = Color.LIGHT_GRAY;//Color.WHITE; //TODO: bedre navn paa variabel :P
	public static final Color EVENT_ACCEPTED = new Color(70, 132, 102, 150); //light green
	public static final Color EVENT_ACCEPTED_BORDER = new Color(70, 132, 102);
	public static final Color EVENT_PENDING = new Color(255, 176, 59, 150); //light yellow
	public static final Color EVENT_PENDING_BORDER = new Color(255, 176, 59);
	public static final Color EVENT_DECLINED = new Color(182, 73, 38, 150); //light red
	public static final Color EVENT_DECLINED_BORDER = new Color(182, 73, 38);
	public static final Color DRAG_NEW_EVENT = new Color(179, 209, 232, 150); //light blue
	public static final Color DRAG_NEW_EVENT_BORDER = Color.BLUE; 
	public static final Color SWING_FRAME_GRAY = new Color(238, 238, 238, 150); //The gray background from the Swing frames


    public enum EventType {
        PERSONAL, BUSINESS, BUSINESS_ADMIN;
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
	
//	Icons
	public static final ImageIcon ACCEPT_ICON_BIG = new ImageIcon(new Object().getClass().getResource("/resources/icons/32x32/accept.png"));
	public static final ImageIcon DECLINE_ICON_BIG = new ImageIcon(new Object().getClass().getResource("/resources/icons/32x32/decline.png"));
	public static final ImageIcon INVITATION_ICON_BIG = new ImageIcon(new Object().getClass().getResource("/resources/icons/32x32/question.png"));
	public static final ImageIcon DELETE_ICON_BIG = new ImageIcon(new Object().getClass().getResource("/resources/icons/32x32/bin.png"));
	public static final ImageIcon CHANGE_ICON_BIG = new ImageIcon(new Object().getClass().getResource("/resources/icons/32x32/error.png"));
	public static final ImageIcon ACCEPT_ICON_SMALL = new ImageIcon(new Object().getClass().getResource("/resources/icons/accept.png"));
	public static final ImageIcon DECLINE_ICON_SMALL = new ImageIcon(new Object().getClass().getResource("/resources/icons/decline.png"));
	public static final ImageIcon INVITATION_ICON_SMALL = new ImageIcon(new Object().getClass().getResource("/resources/icons/question.png"));
	public static final ImageIcon DELETE_ICON_SMALL = new ImageIcon(new Object().getClass().getResource("/resources/icons/bin.png"));
	public static final ImageIcon CHANGE_ICON_SMALL = new ImageIcon(new Object().getClass().getResource("/resources/icons/error.png"));
	
}
