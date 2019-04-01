package myapp.net.inspire.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import myapp.net.inspire.R;
import myapp.net.inspire.refill.RefillFragment;

/**
 * Created by deadlydragger on 11/5/18.
 */

public class ReportGraphActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragmennt, new RefillFragment()).commit();
    }


}
