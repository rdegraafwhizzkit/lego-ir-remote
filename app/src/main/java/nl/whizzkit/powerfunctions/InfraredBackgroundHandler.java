package nl.whizzkit.powerfunctions;


import android.hardware.ConsumerIrManager;
import android.os.Handler;
import android.util.Log;

import java.util.concurrent.Executor;

public class InfraredBackgroundHandler {

    private final String TAG = "InfraredBackgroundHandler";
    private final Executor executor;
    private final ConsumerIrManager consumerIrManager;
//    private final Handler resultHandler;
//
//    public InfraredBackgroundHandler(Executor executor, Handler resultHandler) {
//        this.executor = executor;
//        this.resultHandler = resultHandler;
//    }

    public InfraredBackgroundHandler(Executor executor, ConsumerIrManager consumerIrManager) {
        this.executor = executor;
        this.consumerIrManager = consumerIrManager;
    }

    public void send(final PatternConverter patternConverter){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TransmitInfo transmitInfo=new TransmitInfo(
                        patternConverter.getFrequency(),
                        patternConverter.convertDataTo(PatternType.Intervals)
                );
                Log.i(TAG, transmitInfo.toString());
                consumerIrManager.transmit(transmitInfo.frequency, transmitInfo.pattern);
            }
        });
    }
}
