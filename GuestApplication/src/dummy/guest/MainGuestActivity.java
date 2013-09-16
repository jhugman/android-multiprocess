package dummy.guest;

import dummy.parasite.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainGuestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.process_id);
        tv.setText("" + Process.myPid());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void launchImplicit(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("guest://brains.net"));
        launchIntent(intent);
    }

    public void launchExplicit(View view) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("dummy.host", "dummy.host.MainHostActivity"));
        launchIntent(intent);
    }

    protected void launchIntent(Intent intent) {
        intent.putExtra("parasitePid", Process.myPid());

        startActivity(intent);
    }


    public void killMe(View view) {
        finish();
        Process.killProcess(Process.myPid());
    }
}
