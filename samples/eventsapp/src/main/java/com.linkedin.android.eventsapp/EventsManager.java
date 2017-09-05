package com.linkedin.android.eventsapp;

import android.content.Context;
import java.util.ArrayList;

public class EventsManager {

  private static EventsManager sEventsManager;
  private Context mAppContext;
  private ArrayList<Event> mEvents;

  private EventsManager(Context ctx, ArrayList<Event> events) {
    mAppContext = ctx;
    mEvents = events;
  }

  public static EventsManager getInstance(Context ctx) {
    if (sEventsManager == null) {
      sEventsManager = new EventsManager(ctx.getApplicationContext(), EventsHelper.getEvents(ctx));
    }
    return sEventsManager;
  }

  public ArrayList<Event> getEvents() {
    return mEvents;
  }

  public void setEvents(ArrayList<Event> events) {
    mEvents.clear();
    mEvents.addAll(events);
  }

  public Event getEvent(String eventName) {
    for (Event p : mEvents) {
      if (p.getEventName().equals(eventName)) {
        return p;
      }
    }
    return null;
  }
}
