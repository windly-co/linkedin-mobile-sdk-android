package com.linkedin.android.eventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class AttendeeAdapter extends ArrayAdapter<Person> {

  public static final String TAG = "com.linkedin.android.eventsapp.AttendeeAdapter";
  private final Context context;
  private final Person[] attendees;

  public AttendeeAdapter(Context context, int resource, Person[] attendees, boolean showImages) {
    super(context, resource, attendees);
    this.context = context;
    this.attendees = attendees;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.attendee_list_item, parent, false);
    TextView attendeeNameView = (TextView) rowView.findViewById(R.id.attendeeName);
    final TextView attendeeHeadlineView = (TextView) rowView.findViewById(R.id.attendeeHeadline);
    final ImageView attendeeImageView = (ImageView) rowView.findViewById(R.id.attendeeImage);

    Person attendee = attendees[position];
    attendeeNameView.setText(attendee.getFirstName() + " " + attendee.getLastName());

    boolean isAccessTokenValid = LISessionManager.getInstance(context).getSession().isValid();
    if (isAccessTokenValid) {
      String url = Constants.personByIdBaseUrl + attendee.getLinkedinId() + Constants.personProjection;
      //LISession liSession = LISessionManager.getInstance(context).getSession()
      APIHelper.getInstance(context).getRequest(context, url, new ApiListener() {
        @Override
        public void onApiSuccess(ApiResponse apiResponse) {
          try {
            JSONObject dataAsJson = apiResponse.getResponseDataAsJson();
            String headline = dataAsJson.has("headline") ? dataAsJson.getString("headline") : "";
            String pictureUrl = dataAsJson.has("pictureUrl") ? dataAsJson.getString("pictureUrl") : null;
            JSONObject location = dataAsJson.getJSONObject("location");

            attendeeHeadlineView.setText(headline);
            if (pictureUrl != null) {
              new com.linkedin.android.eventsapp.FetchImageTask(attendeeImageView).execute(pictureUrl);
            } else {
              attendeeImageView.setImageResource(R.drawable.ghost_person);
            }
          } catch (JSONException e) {

          }
        }

        @Override
        public void onApiError(LIApiError apiError) {

        }
      });
    } else {
      attendeeImageView.setImageResource(R.drawable.ghost_person);
    }

    return rowView;
  }
}
