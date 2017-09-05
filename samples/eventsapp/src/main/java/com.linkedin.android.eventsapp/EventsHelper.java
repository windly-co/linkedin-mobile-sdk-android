package com.linkedin.android.eventsapp;

import android.content.Context;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventsHelper {

  private static String[] eventNames = { "CONFERENCE 1", "CONFERENCE 2", "CONFERENCE 3", "CONFERENCE 4" };
  private static String[] eventLocations =
      { "Davos, Switzerland", "Bogota, Colombia", "San Francisco, CA", "Las Vegas, NV" };
  private static String eventImageName = "conference";

  public static ArrayList<Event> getEvents(Context ctx) {
    ArrayList<Event> events = new ArrayList<Event>();
    for (int i = 0; i < eventNames.length; i++) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, i);
      Date date = cal.getTime();
      String eventName = eventNames[i];
      List<Person> attendeeList = getAttendees().subList(0, i + 1);
      events.add(new Event(eventName,
          eventLocations[i],
          getImageResourceId(ctx.getApplicationContext(), eventImageName),
          date.getTime(),
          attendeeList.toArray(new Person[attendeeList.size()])));
    }
    return events;
  }

  private static List<Person> getAttendees() {

    ArrayList<Person> attendees = new ArrayList<Person>();

    attendees.add(new Person("NsaybfPB5b", "Ann", "Lapin", "Engineering Manager at Company X"));
    attendees.add(new Person("NY9TRnSn03", "John", "Smith", "Senior Product Manager at Company Y"));
    attendees.add(new Person("r0ZrPxaZh4", "Jane", "Chang", "Software Engineer at Company Z"));
    attendees.add(new Person("1LpF8o6N3A", "Norman", "Miller", "Web Developer at Company M"));

    return attendees;
  }

  private static int getImageResourceId(Context ctx, String imageResourceName) {
    return ctx.getResources().getIdentifier(imageResourceName, "drawable", ctx.getPackageName());
  }
}
