package org.baobabhealthtrust.cvr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import usbserial.driver.UsbSerialDriver;
import usbserial.driver.UsbSerialPort;
import usbserial.driver.UsbSerialProber;
import usbserial.util.SerialInputOutputManager;

public class Home extends Activity {

    private final String TAG = Home.class.getSimpleName();

    private static UsbSerialPort sPort = null;

    private UsbDevice mDevice;
    private Byte[] bytes;
    private static int TIMEOUT = 0;
    private boolean forceClaim = true;
    private UsbManager mUsbManager;
    private UsbInterface mIntf;

    private UsbDeviceConnection mConnection;

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private SerialInputOutputManager mSerialIoManager;

    public WebView myWebView;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    mDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (mDevice != null) {
                            //call method to set up device communication
                            mIntf = mDevice.getInterface(0);
                            UsbEndpoint endpoint = mIntf.getEndpoint(0);
                            mConnection = mUsbManager.openDevice(mDevice);
                            mConnection.claimInterface(mIntf, forceClaim);

                        }
                    } else {
                        Log.d("INFO", "permission denied for device " + mDevice);
                    }
                }
            }

            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                // UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (mDevice != null) {
                    // call your method that cleans up and closes communication with the device
                    mConnection.releaseInterface(mIntf);
                }
            }

        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File direct = new File(Environment.getExternalStorageDirectory()
                + "/BackupFolder");

        if (!direct.exists()) {
            if (direct.mkdir()) {
                // directory is created;
            }

        }
        exportDB();
        // importDB();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        setContentView(R.layout.activity_home);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSaveFormData(true);

        myWebView.setWebViewClient(new WebViewClient());

        myWebView.addJavascriptInterface(new WebAppInterface(this, this),
                "Android");

        myWebView.loadUrl("file:///android_asset/login.html");

        // use this to start and trigger a service
        // Intent i = new Intent(this, CVRSync.class);
        // potentially add data to the intent
        // i.putExtra("KEY1", "Value to be used by the service");
        // this.startService(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    public void showMsg(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(msg);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sPort != null) {
            try {
                sPort.close();
            } catch (IOException e) {
                // Ignore.
            }
            sPort = null;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("android_asset")) {
                // This is my web site, so do not override; let my WebView load
                // the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch
            // another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    // importing database
    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "org.baobabhealthtrust.cvr"
                        + "//databases//" + "userDemographics";
                String backupDBPath = "/BackupFolder/userDemographics.db";
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

    // exporting database
    private void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                // User DB
                String currentDBPath = "//data//" + "org.baobabhealthtrust.cvr"
                        + "//databases//" + "userDemographics";
                String backupDBPath = "/BackupFolder/userDemographics.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                // General DB

                currentDBPath = "//data//" + "org.baobabhealthtrust.cvr"
                        + "//databases//" + "cvr";
                backupDBPath = "/BackupFolder/cvr.db";
                currentDB = new File(data, currentDBPath);
                backupDB = new File(sd, backupDBPath);

                src = new FileInputStream(currentDB).getChannel();
                dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

    /**
     * Starts the activity, using the supplied driver instance.
     *
     * @param context // @param driver
     */
    static void show(Context context, UsbSerialPort port) {
        sPort = port;
        final Intent intent = new Intent(context, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "Resumed, port=" + sPort);
        if (sPort == null) {
            // mTitleTextView.setText("No serial device.");
        } else {
            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

            mConnection = usbManager.openDevice(sPort.getDriver().getDevice());
            if (mConnection == null) {
                // mTitleTextView.setText("Opening device failed");
                return;
            }

            try {
                sPort.open(mConnection);
                sPort.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            } catch (IOException e) {
                Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
                // mTitleTextView.setText("Error opening device: " + e.getMessage());
                try {
                    sPort.close();
                } catch (IOException e2) {
                    // Ignore.
                }
                sPort = null;
                return;
            }
            // mTitleTextView.setText("Serial device: " + sPort.getClass().getSimpleName());
        }
    }

    public void printBarcode(String name, String npid, String dob, String gender, String address) {

        if (sPort == null) {

            checkPrinterSettings();

            return;

        }

        if (sPort != null) {

            String addressl1 = "";

            String addressl2 = "";

            if(address.trim().length() > 0 && address.trim().length() > 30){

                addressl1 = address.substring(0, 30);

                addressl2 = address.substring(30, (address.trim().length() > 60 ? 60 : address.trim().length()));

            } else {
                addressl1 = address.trim();
            }

            String command =
                    "N\n" +
                            "q801\n" +
                            "Q329,026\n" +
                            "ZT\n" +
                            "B50,198,0,1,4,15,80,N,\"" + npid + "\"\n" +
                            "A35,25,0,2,2,2,N,\"" + name + "\"\n" +
                            "A35,71,0,2,2,2,N,\"" + npid + " " + dob + "(" + gender + ")\"\n" +
                            "A35,117,0,2,2,2,N,\"" + addressl1 + "\"\n" +
                            "A35,163,0,2,2,2,N,\"" + addressl2 + "\"\n" +
                            "P1\n";

            byte[] data;

            data = command.getBytes();

            try {

                sPort.write(data, data.length * 1000);

            } catch (IOException e) {

                Log.d(this.getClass().getSimpleName(), "Timeout error!");

            }

            command = null;

            data = null;

        }

    }

    public void checkPrinterSettings(){

        final List<UsbSerialDriver> drivers =
                UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);

        if(drivers.size() == 1) {

            UsbSerialPort port = drivers.get(0).getPorts().get(0);

            sPort = port;

            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

            mConnection = usbManager.openDevice(sPort.getDriver().getDevice());
            if (mConnection == null) {
                // mTitleTextView.setText("Opening device failed");
                return;
            }

            try {
                sPort.open(mConnection);
                sPort.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            } catch (IOException e) {
                Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
                // mTitleTextView.setText("Error opening device: " + e.getMessage());
                try {
                    sPort.close();
                } catch (IOException e2) {
                    // Ignore.
                }
                sPort = null;
                return;
            }

        } else {

            DeviceListActivity.show(this);

        }

    }

}
