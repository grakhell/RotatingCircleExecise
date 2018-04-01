package anull.rotatingringexercise;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimatedVectorDrawableCompat avd;
    int mCount;
    static final String STATE_COUNT = "isCount";

    boolean mIsAnim;
    static final String STATE_ANIM = "isAnim";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avd = AnimatedVectorDrawableCompat.create(getBaseContext(),R.drawable.ic_ring);

        ImageView ivMain = findViewById(R.id.ivMain);
        ivMain.setImageDrawable(avd);

        if (savedInstanceState != null) {
            mIsAnim = savedInstanceState.getBoolean(STATE_ANIM);
            if (mIsAnim) {
                avd.start();
            }
            mCount = savedInstanceState.getInt(STATE_COUNT);
        }
        else
        {
            mCount = 0;
        }

        final Handler rotateHandler = new Handler(Looper.getMainLooper());
        avd.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(final Drawable drawable) {
                rotateHandler.post( () -> {
                        mCount--;
                        if (mCount == 0)
                        {
                            mIsAnim = false;
                        }
                        else
                        {
                            avd.start();
                        }
                    });
            }
        });

        Button btnRotate = findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener( v -> {
            mCount++;
            if (!avd.isRunning()) {
                avd.start();
                mIsAnim = true;
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_ANIM, mIsAnim);
        outState.putInt(STATE_COUNT,mCount);
    }
}
