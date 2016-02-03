package br.com.bfs.alertviewapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private AlertView alertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlert();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAlert(){
        alertView = AlertView.with(this, "itle")
                .setMessage("Messagem")
                .setContainerLayout(R.layout.alert_custom)
                .addButtonOk("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAlertLoad();
                    }
                });
        alertView.show();
    }

    private void setAlertLoad(){

        alertView.change(AlertView.AlertType.LOAD,"Loading..",-1);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertView.change(AlertView.AlertType.DEFAULT,"Title",-1)
                        .setContainerColor(R.color.colorWhite)
                        .setMessage("Messagem mais texto mais texto mais texto").addButtonOk("Cancel", null);
            }
        }, 2000);
    }
}
