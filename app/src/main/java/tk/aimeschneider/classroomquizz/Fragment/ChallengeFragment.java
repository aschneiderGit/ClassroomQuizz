package tk.aimeschneider.classroomquizz.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import tk.aimeschneider.classroomquizz.Activity.QuizzActivity;
import tk.aimeschneider.classroomquizz.ModelOnly.Controller;
import tk.aimeschneider.classroomquizz.ModelOnly.Friend;
import tk.aimeschneider.classroomquizz.MyElements.ButtonFriend;
import tk.aimeschneider.classroomquizz.R;

import static android.support.constraint.Constraints.TAG;
import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.SERVER_KEY;
import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.WEB_FRIENDS_IMG;
import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.WEB_FRIENDS_REQUEST;
import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.myFriends;

public class ChallengeFragment extends Fragment {

    private ProgressDialog pDialog;
    private Context ctx;

    public ChallengeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        ctx = this.getContext();
        LoadFriends();
        return view;
    }

    private void LoadFriends() {
        if (Controller.checkConnection(ctx)) {
            pDialog = new ProgressDialog(this.getContext());
            pDialog.setMessage("Getting list of friends...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            String url = WEB_FRIENDS_REQUEST+ "?key=" + SERVER_KEY;

            Ion.with(this)
                    .load(url)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            pDialog.dismiss();
                            if (result == null)
                                Log.d(TAG, "No response from the server!!!");
                            else {
                                try {
                                    myFriends = new ArrayList<Friend>();
                                    JSONArray amisJson = new JSONArray(result);
                                    for (int i = 0; i < amisJson.length(); i++) {
                                        myFriends.add(Friend.JsonToFriend(amisJson.getJSONObject(i)));
                                    }
                                    if (myFriends.size() != 0) {
                                        setGridViewFriends();
                                    }
                                } catch (JSONException e1) {
                                    Toast.makeText(ctx, "Erreur lors de l'accès aux amis", Toast.LENGTH_SHORT).show();
                                }
                            }
                            pDialog.dismiss();

                        }
                    });
        }
    }

    private void setGridViewFriends()
    {
        //GridView
        GridView gridview = getView().findViewById(R.id.gvChallenge);
        gridview.setAdapter(new FriendAdapter(ctx));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(ctx, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Friend Adapter
    public class FriendAdapter extends BaseAdapter {
        private Context mContext;

        public FriendAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return myFriends.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                view = inflater.inflate(R.layout.friend_square, null);

            } else {
                view = (View) convertView;
            }


            for(int index=0; index<((ViewGroup)view).getChildCount(); ++index)
            {
                View nextChild = ((ViewGroup)view).getChildAt(index);
                for(int subIndex=0; subIndex<((ViewGroup)nextChild).getChildCount(); ++subIndex)
                {
                    boolean haveImage = (!myFriends.get(position).getPhotoPath().isEmpty() && myFriends.get(position).getPhotoPath()!= "null");
                    View subNextChild = ((ViewGroup)nextChild).getChildAt(subIndex);
                    switch (subNextChild.getId())
                    {
                        case R.id.imgAvatarFriends: {
                            CircleImageView img = (CircleImageView) subNextChild;
                            //img.setImageURI(Uri.parse("https://media.wired.com/photos/5a99c6108e71085604e0271b/master/pass/google_diversity_lawsuit-FINAL.jpg"))
                            if (haveImage )
                                Picasso.with(img.getContext()).load(WEB_FRIENDS_IMG  + myFriends.get(position).getPhotoPath()).into(img);
                            else
                            {
                                Drawable color = new ColorDrawable(Color.parseColor(myFriends.get(position).getRandomColor()));
                                img.setImageDrawable(color);
                            }
                            break;
                        }
                        case R.id.tvLetter:
                        {
                            TextView tvLetter = (TextView) subNextChild;
                            if (haveImage)
                                tvLetter.setVisibility(View.GONE);
                            else
                            {
                                tvLetter.setText(myFriends.get(position).getPrenom().charAt(0)+"");
                                tvLetter.setVisibility(View.VISIBLE);
                            }
                            break;
                        }
                        case R.id.imgConnectFriend:
                        {
                            if ((myFriends.get(position).isPresent()))
                                subNextChild.setBackgroundResource(R.drawable.border_true);
                            else
                                subNextChild.setBackgroundResource(R.drawable.border_false);
                            break;
                        }
                        case  R.id.txtLastSoreFriend:
                        {
                            TextView txt =(TextView)subNextChild;
                            txt.setText((getResources().getString(R.string.last_score) + Integer.toString(   myFriends.get(position).getLastScore())));
                            break;
                        }
                        case R.id.rlNomPrenomFriends:
                        {
                            for(int subSubIndex=0; subSubIndex<((ViewGroup)subNextChild).getChildCount(); ++subSubIndex)
                            {
                                View subSubView = ((ViewGroup)subNextChild).getChildAt(subSubIndex);
                                switch (subSubView.getId()) {
                                    case R.id.txtPrenomFriend: {
                                        TextView txt = (TextView) subSubView;
                                        txt.setText(myFriends.get(position).getPrenom());
                                        break;
                                    }
                                    case R.id.txtNomFriend: {
                                        TextView txt = (TextView) subSubView;
                                        txt.setText(myFriends.get(position).getName());
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case  R.id.btnFriendSquare:
                        {
                            ButtonFriend btnFriend = (ButtonFriend) subNextChild;
                            btnFriend.setFriend(myFriends.get(position));
                            btnFriend.setOnClickListener(VerifReponseListener);
                            break;
                        }
                    }
                }
            }

            return view;
        }
    }

    private View.OnClickListener VerifReponseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StartQuizz((ButtonFriend)v);
        }
    };

    private void StartQuizz(ButtonFriend v) {
        Intent intent = new Intent(getActivity(), QuizzActivity.class);
        Bundle b = new Bundle();
        b.putInt("friend", myFriends.indexOf(v.getFriend()) ); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        getActivity().finish();
        startActivity(intent);
    }
}