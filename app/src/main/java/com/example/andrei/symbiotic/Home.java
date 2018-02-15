package com.example.andrei.symbiotic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import org.apache.http.client.HttpClient;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.microsoft.projectoxford.vision.VisionServiceClient;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;


public class Home extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mCameraPreview;

    private ArrayList<String> keywords;
    final private State check = new State();

    public static final String subscriptionKey = "559fe870a35d4aaa9162b0dcf8e59733";
    public static final String uriBase = "https://westeurope.api.cognitive.microsoft.com/vision/v1.0/analyze";
    public static final String aux = "visualFeatures=Description&language=en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        String userId = intent.getStringExtra("id");
        /*
        * TODO
        * GET THE BALANCE
        * Did I win?
        */

        this.keywords = new ArrayList<>();
        this.keywords.addAll(Arrays.asList("cup", "glass", "water", "bottle"));

         /* Buttons */
        final Button logoutButton = findViewById(R.id.button_logout);
        final Button recycleButton = findViewById(R.id.button_recycle);
        recycleButton.setText("Scan");

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mCamera = getCameraInstance();
        mCamera.setDisplayOrientation(90);
        mCameraPreview = new CameraPreview(getApplicationContext(), mCamera);
        final FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);
        findViewById(R.id.camera_preview).setVisibility(View.INVISIBLE);

        recycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.firstTime) {
                    recycleButton.setText("Recycle");
                    findViewById(R.id.camera_preview).setVisibility(View.VISIBLE);
                    findViewById(R.id.balance).setVisibility(View.INVISIBLE);
                    findViewById(R.id.textView).setVisibility(View.INVISIBLE);
                    findViewById(R.id.button_logout).setVisibility(View.INVISIBLE);
                    check.firstTime = false;
                    return;
                }
                findViewById(R.id.button_recycle).setVisibility(View.INVISIBLE);

                mCamera.takePicture(null, null, mPicture);
                Toast.makeText(getApplicationContext(), "wait...", Toast.LENGTH_LONG).show();
            }
        });

        /* END Buttons */
    }


    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }


    public void get(Bitmap img) {
        HttpClient httpclient = new DefaultHttpClient();
        try {

            HttpPost request = new HttpPost(uriBase + "?" + aux);

            // Request headers.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            byte[] imageBytes = baos.toByteArray();
            ByteArrayEntity be = new ByteArrayEntity(imageBytes);
            request.setEntity(be);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String jsonString = EntityUtils.toString(entity);
                final JSONObject json = new JSONObject(jsonString);

                JSONArray array = ((JSONObject) json.get("description")).getJSONArray("tags");
                int len = 4 > array.length() ? array.length() : 4;
                boolean found = false;
                for (int i = 0; i < len; i++) {
                    String val = array.getString(i);
                    if (this.keywords.contains(val)) {
                        found = true;
                        break;
                    }
                }

                final boolean decision = found;
                this.check.noBottleSoFar = found;

                if (decision) { // if it is a bottle:
                    final Button recycleButton = findViewById(R.id.button_recycle);
                    recycleButton.setText("Scan");

                    this.runOnUiThread(new Runnable() {
                        public void run() {
                            findViewById(R.id.camera_preview).setVisibility(View.INVISIBLE);
                            findViewById(R.id.balance).setVisibility(View.VISIBLE);
                            findViewById(R.id.textView).setVisibility(View.VISIBLE);
                            findViewById(R.id.button_logout).setVisibility(View.VISIBLE);
                        }
                    });

                    check.firstTime = true;
                }

                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), decision ? "OK" : "NOT OK", Toast.LENGTH_LONG).show();
                        findViewById(R.id.button_recycle).setVisibility(View.VISIBLE);
                    }
                });

                if (decision) { // if it is a bottle:

                    /* TODO UPDATE THE BALANCE */
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            img = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);

            MediaStore.Images.Media.insertImage(getContentResolver(), img, "Pic", "best");

            final Bitmap bm = img;

            new Thread(new Runnable() {
                public void run() {
                    get(bm);
                }
            }).start();
        }
    };

    class State {
        public boolean noBottleSoFar = true;
        public boolean firstTime = true;

    }
}
