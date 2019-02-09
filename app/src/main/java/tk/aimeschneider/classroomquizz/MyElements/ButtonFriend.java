package tk.aimeschneider.classroomquizz.MyElements;
import android.content.Context;
import android.util.AttributeSet;

import tk.aimeschneider.classroomquizz.ModelOnly.Friend;
import tk.aimeschneider.classroomquizz.ModelOnly.QuestionArrondissement;


public class ButtonFriend extends android.support.v7.widget.AppCompatButton {

    private Friend friend;

    public ButtonFriend(Context context) {
        super(context);
    }

    public ButtonFriend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonFriend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public Friend getFriend() {
        return friend;
    }
    public void setFriend (Friend f)
    {
        friend = f;
    }
}
