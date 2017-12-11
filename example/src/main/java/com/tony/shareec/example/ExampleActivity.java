package com.tony.shareec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tony.brown.activities.ProxyActivity;
import com.tony.brown.app.Brown;
import com.tony.brown.delegates.BrownDelegate;

public class ExampleActivity extends ProxyActivity {


    @Override
    public BrownDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
