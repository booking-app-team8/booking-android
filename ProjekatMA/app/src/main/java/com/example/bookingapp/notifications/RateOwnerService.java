package com.example.bookingapp.notifications;

import android.app.Service;
import android.content.Intent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

public class RateOwnerService extends Service {


    public static String RESULT_CODE = "RESULT_CODE";

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public RateOwnerService() { }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String ownerId = intent.getStringExtra("ownerId");

        executor.execute(new Runnable() {
            @Override
            public void run() {
//                Ride ride = RideService.getRideDetails(Long.parseLong(rideId));
//                System.out.println("paseeeeeeeeeeeeeeeeeeengeeeeeeeeeeeeeeeeeeeeeeer");
//                System.out.println(ride);
//                if (ride.getStatus().equals("ACCEPTED")) {
//                    System.out.println("prihvacenooooo");
//                    Intent ints = new Intent(PassengerMainActivity.ACCEPTED_DATA);
//                    int intsStatus = 1; // true
//                    ints.putExtra(RESULT_CODE, intsStatus); // salje se u main activity rezultat
//                    getApplicationContext().sendBroadcast(ints);
//                }

                handler.postDelayed(this, 10000);
            }
        });

        // if for some reason the os kills the service, do not create a new one
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
//        handler.removeCallbacksAndMessages(null);
    }
}

