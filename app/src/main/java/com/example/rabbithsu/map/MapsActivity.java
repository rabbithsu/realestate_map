package com.example.rabbithsu.map;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.view.View;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import java.util.HashMap;
import java.util.Locale;
import android.location.Geocoder;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;

import android.util.Log;
import android.location.Address;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import android.content.Intent;
import android.widget.Toast;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String SendAddress;
    private boolean isInnerVisible = false;

    private FragmentManager fragmentManager = getFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    public String houseinfo = null;
    public boolean ready = false;

    //public ArrayList<String> address_list = new ArrayList<>();
    public static Map<String, String> address_list = new HashMap<String, String>();

    private ProgressDialog dialog;

    public static LatLng c = null;
    public static JSONArray jsonArray = null;
    public static String infoText = null;

    //final FrameLayout frameLayout = (FrameLayout) ;
    //private Fragment innerFragment = new InfoFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = this.getIntent();
        //Bundle bundle = intent.getExtras();
        SendAddress = intent.getStringExtra("EXTRA_MESSAGE");//bundle.getString("EXTRA_MESSAGE");//
        Toast.makeText(this, SendAddress, Toast.LENGTH_LONG).show();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {



        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        //Geocoder gc = new Geocoder(this.getApplication(), Locale.TRADITIONAL_CHINESE);

        //LatLng place = new LatLng(25.033408, 121.564099);
        //double[] ll = getLocationInfo("台北市內湖區");
        //place = new LatLng(ll[0], ll[1]);
        //addMarker(new LatLng(24.99876, 121.54000), "","");
        //addMarker(new LatLng(25.00013, 121.53948), "", "");
        //addMarker(new LatLng(25, 121.53957), "","");
        //getLatLngByAddr("台北市文山區羅斯福路六段50號");




        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                infoText = address_list.get(marker.getId());

                //Toast.makeText(MapsActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                showHideInnerFragment();
                return false;
            }


        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (isInnerVisible)
                    HideInnerFragment();
            }
        });

        ShowInfo();
        //moveMap(place);

        // 移動地圖
        //moveMap(place);

        // 加入地圖標記
        //addMarker(place, "Hello!", " Taipei 101");
    }

    private void moveMap(LatLng place) {
        // 建立地圖攝影機的位置物件
        CameraPosition cameraPosition;
        try{
            cameraPosition =
                    new CameraPosition.Builder()
                            .target(place)
                            .zoom(17)
                            .build();
        }catch (Exception e){
            e.printStackTrace();
            cameraPosition =
                    new CameraPosition.Builder()
                            .target(new LatLng(24.987497, 121.576053))
                            .zoom(17)
                            .build();
        }

        // 使用動畫的效果移動地圖
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void addMarker(LatLng place, String title, String snippet, String info) {
        BitmapDescriptor icon =
                BitmapDescriptorFactory.fromResource(R.mipmap.pic);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(place)
                .title(title)
                .snippet(snippet)
                .icon(icon);
        Marker tmp = mMap.addMarker(markerOptions);
        address_list.put(tmp.getId(), info);
    }


    private LatLng getLatLngByAddr(String et_address) {

        Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE); // 地區:台灣

        try {
            List<Address> addresses = gc.getFromLocationName(et_address
                    .trim(), 1);

            if (addresses != null && !addresses.isEmpty()) {
                double latitude = ((Address) addresses.get(0)).getLatitude();
                double longitude = ((Address) addresses.get(0)).getLongitude();
                String addrline = ((Address) addresses.get(0))
                        .getAddressLine(0);

                if (addrline != null) {
                    LatLng info = new LatLng(latitude, longitude);
                    return info;
                    //addMarker(info, "台北市文山區羅斯福路六段31~60號", "交易單價 約: 209,712(元/坪)\t建物型態: 公寓(5樓含以下無電梯)");
                    //moveMap(info);
                    //setMapMarker(et_address.getText().toString().trim());
                }
                else{
                    return null;
                }
            }else{
                return null;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }



    private void showHideInnerFragment() {
        //variables are class members...
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment innerFragment = new InfoFragment();
        if (!isInnerVisible) {
            fragmentTransaction.replace(R.id.inner_fragment, innerFragment);
            findViewById(R.id.inner_fragment).setVisibility(View.VISIBLE);
            isInnerVisible = !isInnerVisible;
        } else {
            //fragmentTransaction.remove(innerFragment);
            fragmentTransaction.replace(R.id.inner_fragment, innerFragment);
            //findViewById(R.id.inner_fragment).setVisibility(View.GONE);
            //isInnerVisible = !isInnerVisible;
        }
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void HideInnerFragment() {
        //variables are class members...
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment innerFragment = new InfoFragment();

        fragmentTransaction.remove(innerFragment);
        findViewById(R.id.inner_fragment).setVisibility(View.GONE);
        isInnerVisible = !isInnerVisible;

        fragmentTransaction.commit();
    }

    /*private Runnable getinfoThread = new Runnable() {
        public void run() {
            try {
                houseinfo = DBConnector.executeQuery("SELECT * FROM house");
                ready = true;


            } catch (Exception e) {
                Log.e("log_tag", e.toString());
                ready = true;
            }finally {

                dialog.dismiss();

            }

        }
    };*/
    private Runnable getinfoThread = new Runnable() {
        public void run() {

            String res =  "" ;
            try {

                //得到資源中的Raw數據流
                InputStream in = getResources().openRawResource(R.raw.house_utf8_transfer);

                //得到數據的大小
                int  length = in.available();

                byte  [] buffer =  new byte [length];

                //讀取數據
                in.read(buffer);

                //依test.txt的編碼類型選擇合適的編碼，如果不調整會亂碼
                res = EncodingUtils.getString(buffer, "utf-8");

                //關閉
                in.close();
                Thread.sleep(10);

                houseinfo = res;
                ready = true;

            } catch (Exception e){
                e.printStackTrace();
                ready = true;


        }finally {

                dialog.dismiss();

            }

        }
    };


    public void ShowInfo(){
        try{
            if(houseinfo == null){
                dialog = ProgressDialog.show(this , "讀取中", "Loading...", true);
                Thread thread = new Thread(getinfoThread);
                thread.start();
                //Thread.sleep(5000);
                thread.join();
            }
            //dialog = ProgressDialog.show(getApplication(), "讀取中", "Loading...", true);
        }catch (Exception e){
            e.printStackTrace();

        }

        Log.d("Maker", "AddMaker.");
        try{
            for(String n  : houseinfo.split("\n")){
                String[] c = n.split(",");
                addMarker(
                        new LatLng(Double.parseDouble(c[11]),Double.parseDouble(c[12])),
                        c[0],
                        c[1],
                        "土地移轉總面積(平方公尺):"+c[2]+"\n交易年月:"+c[3]+"\n"+"建物移轉總面積(平方公尺):"+c[4]+"\n總價(元):"+c[5]+"\n單價(元/平方公尺):"+c[6]
                                +"\n車位移轉總面積(平方公尺):" +c[7]+ "\n車位總價(元):"+c[8]

                );

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        /*try {

            jsonArray = new JSONArray(houseinfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                if(i == 0){
                    continue;
                }
                JSONObject jsonData = jsonArray.getJSONObject(i);
                Log.d("Count: ", ""+i);
                addMarker(
                        new LatLng(Double.parseDouble(jsonData.getString("x")),Double.parseDouble(jsonData.getString("y"))),
                        jsonData.getString("dist"),
                        jsonData.getString("addr"),
                        jsonData.getString("info")
                );
                //Log.d("GETINFO", jsonData.getString("dist"));
                //Log.d("GETINFO", jsonData.getString("addr"));
                //Log.d("GETINFO", jsonData.getString("info"));

                //Log.d("GETINFO", jsonData.getString("x"));
                //Log.d("GETINFO", jsonData.getString("y"));

                //address_list.add(new LatLng(Double.parseDouble(jsonData.getString("x")),Double.parseDouble(jsonData.getString("y"))));
                        /*if(i == 30){
                            break;
                        }*/

                        /*
                        renewtime.setText(jsonData.getString("dist"));
                        renewtime.setText(jsonData.getString("addr"));
                        renewtime.setText(jsonData.getString("info"));
                        renewtime.setText(jsonData.getString("x"));
                        renewtime.setText(jsonData.getString("y"));*/

    /*        }
        } catch (Exception e) {
            Log.e("Get maps: ", e.toString());
        }*/


        /*for(LatLng n : address_list){
            if(n == null){
                continue;
            }else{
                c = n;
                Log.d("Transfer: ", n + "  to " + c);

                if (c==null)
                    continue;
                else{
                    addMarker(c, "", "");
                }
                //addMarker(c, "", n);

            }
        }*/

        Log.d("MAP", "Move...");

        if(getLatLngByAddr(SendAddress) != null)
            moveMap(getLatLngByAddr(SendAddress));
        else
            moveMap(getLatLngByAddr("臺北市文山區指南路二段64號"));

    }




}
