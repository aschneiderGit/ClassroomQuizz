package tk.aimeschneider.classroomquizz.ModelOnly;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tk.aimeschneider.classroomquizz.Fragment.CameraFragment;
import tk.aimeschneider.classroomquizz.Fragment.LightFragment;
import tk.aimeschneider.classroomquizz.Fragment.ScannerFragment;
import tk.aimeschneider.classroomquizz.Fragment.TempFragment;
import tk.aimeschneider.classroomquizz.R;

public class MyPageAdapter extends FragmentPagerAdapter
{
    private Context ctx ;
    private static int COUNT = 3;
    public MyPageAdapter (Context ctx , FragmentManager fm )
    {
        super (fm);
        this.ctx = ctx;
    }
    @ Override public Fragment getItem (int position)
    {
        Fragment result = new Fragment();
        switch (position)
        {
            case 0: {result =  new ScannerFragment();break;}
            case 1:  {result =  new CameraFragment(); break;}
            case 2:  {result =  new TempFragment();break;}
        }
        return result;
    }
    @ Override public CharSequence getPageTitle ( int position)
    {   String result = "";
        switch (position)
        {
            case 0: {result = ctx.getString( R.string.scanner ); break;}
            case 1:  {result =  ctx.getString( R.string.camera ); break;}
            case 2:   {result =  ctx.getString( R.string.temperature ); break;}

        }
        return result;
    }

    @ Override public int getCount ()
    {
        return COUNT ;
    }
}
