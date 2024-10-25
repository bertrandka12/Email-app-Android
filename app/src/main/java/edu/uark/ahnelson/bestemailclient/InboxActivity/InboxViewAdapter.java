package edu.uark.ahnelson.bestemailclient.InboxActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uark.ahnelson.bestemailclient.ComposeActivity.ComposeActivity;
import edu.uark.ahnelson.bestemailclient.R;
import edu.uark.ahnelson.bestemailclient.data.Message;
import edu.uark.ahnelson.bestemailclient.data.MessageContent;

public class InboxViewAdapter extends RecyclerView.Adapter<InboxViewAdapter.ViewHolder> {

    public void setLocalDataSet(List<Message> localDataSet) {
        this.localDataSet = localDataSet;
    }

    private List<Message> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvIcon;
        private final TextView tvSubject;
        private final TextView tvFrom;

        //from r
        //private final TextView dateView;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvIcon = (TextView) view.findViewById(R.id.tvIcon);
            tvSubject = (TextView) view.findViewById(R.id.tvSubject);
            tvFrom = (TextView) view.findViewById(R.id.tvFrom);

        }

        public TextView getTvIcon() {
            return tvIcon;
        }

        public TextView getTvSubject(){
            return tvSubject;
        }

        public TextView getTvFrom(){
            return tvFrom;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public InboxViewAdapter(List<Message> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_message_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.itemView.setTag(String.valueOf(localDataSet.get(position).getId()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer email_id = Integer.valueOf((String) view.getTag());
                Intent composeIntent = new Intent();
                composeIntent.setClass(view.getContext(), ComposeActivity.class); // when we click on the msgs this is why it takes us to the composeActivity page or class maybe change it???
                composeIntent.putExtra("email_id",email_id);
                view.getContext().startActivity(composeIntent);
//                Integer email_id = Integer.valueOf((String) view.getTag());
//                Intent rcvdIntent = new Intent();
//                rcvdIntent.setClass(view.getContext(), MessageContent.class); // when we click on the msgs this is why it takes us to the composeActivity page or class maybe change it???
//                rcvdIntent.putExtra("email_id",email_id);
//                view.getContext().startActivity(rcvdIntent);

            }
        });
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        String emailFrom = localDataSet.get(position).getFrom();
        viewHolder.getTvFrom().setText(emailFrom);
        viewHolder.getTvIcon().setText(String.valueOf(emailFrom.charAt(0)));
        String subject = localDataSet.get(position).getSubject();
        viewHolder.getTvSubject().setText(subject);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


