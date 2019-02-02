package tk.aimeschneider.classroomquizz.ModelOnly;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import tk.aimeschneider.classroomquizz.Activity.ArrondissementActivity;
import tk.aimeschneider.classroomquizz.Activity.FriendActivity;
import tk.aimeschneider.classroomquizz.Activity.MainActivity;
import tk.aimeschneider.classroomquizz.Activity.QuizzActivity;
import tk.aimeschneider.classroomquizz.R;

public class Navigation {


    public static void onNavigationItemSelected(MenuItem item, Context ctx)
    {

        switch (item.getItemId())
        {
            case R.id.nav_home:
            {
                Intent intent = new Intent(ctx, MainActivity.class);
                ctx.startActivity(intent);
                break;
            }
            case R.id.nav_quizz:
            {
                if(ctx.getClass() != QuizzActivity.class)
                {
                    Intent intent = new Intent(ctx, QuizzActivity.class);
                    ctx.startActivity(intent);
                }
                break;
            }
            case R.id.nav_arondissement:
            {
                if(ctx.getClass() != ArrondissementActivity.class)
                {
                    Intent intent = new Intent(ctx, ArrondissementActivity.class);
                    ctx.startActivity(intent);
                }
                break;
            }
            case R.id.nav_friend:
            {
                if (ctx.getClass() != FriendActivity.class)
                {
                    Intent intent = new Intent(ctx, FriendActivity.class);
                    ctx.startActivity(intent);
                }
                break;
            }
        }
    }
}
