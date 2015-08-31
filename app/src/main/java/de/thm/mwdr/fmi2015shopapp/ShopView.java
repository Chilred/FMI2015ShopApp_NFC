package de.thm.mwdr.fmi2015shopapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShopView extends AppCompatActivity implements View.OnClickListener {
    private TextView rootview;
    private ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_view, menu);
        rootview = (TextView)findViewById(R.id.textView);
        btn = (ImageButton) rootview.findViewById(R.id.btnProduct1);
        findViewById(R.id.btnProduct1).setOnClickListener(this);
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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnProduct1:
                Intent intent = new Intent(this,ProductView.class);
                startActivity(intent);
                break;
            case R.id.btnProduct2:
                Intent intent1 = new Intent(this,ProductView.class);
                startActivity(intent1);
                break;
            case R.id.btnProduct3:
                Intent intent2 = new Intent(this,ProductView.class);
                startActivity(intent2);
                break;
        }

    }
}
