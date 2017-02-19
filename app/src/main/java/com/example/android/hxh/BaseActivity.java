package com.example.android.hxh;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BaseActivity extends AppCompatActivity {
    /**
     * Go to main
     *
     * @param view
     */
    public void goToMain(View view) {goTo(MainActivity.class);}

    /**
     * Go to chatroom
     */

    public void goToChatroom(View view) {
        goTo(ChatActivity.class);
    }

    /**
     * Go to profile tab
     *
     * @param view
     */
    public void goToProfile(View view) {
        goTo(ProfileActivity.class);
    }

    /**
     * Go to library tab
     *
     * @param view
     */
    public void goToLibrary(View view) {
        goTo(LibraryActivity.class);
    }
/**Go to Poll
    *
            * @param view
    */
    public void goToPoll(View view) {goTo(PollActivity.class);}

    private void goTo(Class className) {
        Intent intent = new Intent(getApplicationContext(), className);
        startActivity(intent);
        finish();
    }
    }

