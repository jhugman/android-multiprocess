package dummy.host;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainHostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);

        TextView tv = (TextView) findViewById(R.id.process_id);
        int parasitePid = getIntent().getIntExtra("parasitePid", 0);

        int hostPid = android.os.Process.myPid();
        tv.setText("" + hostPid + ": " + (hostPid == parasitePid));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_host, menu);
        return true;
    }

    public void killMe(View view) {
        finish();
        Process.killProcess(Process.myPid());
    }

}
