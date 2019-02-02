package tk.aimeschneider.classroomquizz.MyElements;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tk.aimeschneider.classroomquizz.ModelOnly.Message;
import tk.aimeschneider.classroomquizz.R;


public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView txtName;
    TextView txtMessage;

    public MessageViewHolder(View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.name);
        txtMessage = itemView.findViewById(R.id.message);
    }

    public void bind(Message message) {
        txtName.setText(message.getSource().getName());
        txtMessage.setText(message.getMessage());
    }

}