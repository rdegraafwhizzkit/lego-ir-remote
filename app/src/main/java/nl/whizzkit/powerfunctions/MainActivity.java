package nl.whizzkit.powerfunctions;

import android.app.Application;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

//import nl.whizzkit.powerfunctions.R;
//import nl.whizzkit.powerfunctions.LegoRcProtocol;
//import nl.whizzkit.powerfunctions.PatternConverter;
//import nl.whizzkit.powerfunctions.PatternType;
//import nl.whizzkit.powerfunctions.TransmitInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class MainActivity
//extends Application
        extends AppCompatActivity
        implements View.OnTouchListener, View.OnClickListener {

    //    private TransmitInfo[] patterns;
//    private final String TAG = "PowerFunctions";
//    private int currentPattern = 0;
    private ConsumerIrManager consumerIrManager;

    // Thread for each channel?
    ExecutorService executorService = Executors.newFixedThreadPool(4);
//    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
//    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

//    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
//            1,       // Initial pool size
//            4,       // Max pool size
//            2,
//            TimeUnit.SECONDS
//            workQueue
//    );

    InfraredBackgroundHandler infraredBackgroundHandler;

    Button fwdButtonRed;
    Button revButtonRed;
    Button brkButtonRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        consumerIrManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
        infraredBackgroundHandler = new InfraredBackgroundHandler(
                executorService,
                consumerIrManager
        );
        fwdButtonRed = this.findViewById(R.id.fwd_button_red);
        revButtonRed = this.findViewById(R.id.rev_button_red);
        brkButtonRed = this.findViewById(R.id.brk_button_red);

        fwdButtonRed.setOnClickListener(this);
        revButtonRed.setOnClickListener(this);
        brkButtonRed.setOnClickListener(this);

//        transmitButton.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {

        int codeRed = -1;
        int codeBlue = -1;
        if (v == fwdButtonRed) {
            codeRed = LegoRcProtocol.RED_FWD;
            codeBlue = LegoRcProtocol.BLUE_FLT;
        } else if (v == revButtonRed) {
            codeRed = LegoRcProtocol.RED_REV;
            codeBlue = LegoRcProtocol.BLUE_FLT;
        } else if (v == brkButtonRed) {
            codeRed = LegoRcProtocol.RED_BRK;
            codeBlue = LegoRcProtocol.BLUE_FLT;
        }
        if (codeRed + codeBlue > 0) {
            infraredBackgroundHandler.send(new PatternConverter(
                    PatternType.Cycles,
                    LegoRcProtocol.FREQUENCY,
                    LegoRcProtocol.generatePattern(LegoRcProtocol.sendComboDirect(
                            LegoRcProtocol.CH1,
                            codeRed,
                            codeBlue
                            )
                    )));
        }
    }

    @Override
    public boolean onTouch(View yourButton, MotionEvent theMotion) {
        switch (theMotion.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        yourButton.performClick();
        return true;
    }

}
