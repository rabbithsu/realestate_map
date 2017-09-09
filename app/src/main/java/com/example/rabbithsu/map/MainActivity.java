package com.example.rabbithsu.map;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et_address ;
    private Button search;
    private Button Loc;
    public final static String EXTRA_MESSAGE = "com.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_address = (EditText) this.findViewById(R.id.ask);
        search = (Button) this.findViewById(R.id.search);
        Loc = (Button) this.findViewById(R.id.Loc);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMap = new Intent(MainActivity.this, MapsActivity.class);
                /*Bundle bundle = new Bundle();
                bundle.putString("EXTRA_MESSAGE", et_address.getText().toString().trim());
                intentMap.putExtras(bundle);*/
                intentMap.putExtra("EXTRA_MESSAGE", et_address.getText().toString().trim());
                et_address.setText("");
                // 啟動地圖元件
                startActivity(intentMap);

            }
        });
        Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMap = new Intent(MainActivity.this, MapsActivity.class);
                /*Bundle bundle = new Bundle();
                bundle.putString("EXTRA_MESSAGE", "台北市內湖區江南街71巷16弄7號");
                intentMap.putExtras(bundle);*/
                intentMap.putExtra("EXTRA_MESSAGE", "臺北市文山區指南路二段64號");
                // 啟動地圖元件
                startActivity(intentMap);

            }
        });

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
            Intent intentMap = new Intent(this, MapsActivity.class);
            // 啟動地圖元件
            startActivity(intentMap);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
