package com.example.todoapp_material.app;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements AddToDoFragment.onClickSubmit{

    private Main_Fragment mMainFragment;
    private AddToDoFragment mAddToDoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if(!isInTwoPaneMode())
        {
            //Main fragment
            mMainFragment = new Main_Fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container,mMainFragment);
            //transaction.addToBackStack(null);
            transaction.commit();

        }
        else
        {
            mMainFragment = (Main_Fragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        }




    }


    public boolean isInTwoPaneMode()
    {
        return findViewById(R.id.fragment_container) == null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnSubmit(Bundle data) {

        if(mMainFragment==null)
            mMainFragment = (Main_Fragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_large);


        if(!isInTwoPaneMode())
        {

            getSupportFragmentManager().popBackStackImmediate();


        }

        mMainFragment.updateData(data);


    }
}
