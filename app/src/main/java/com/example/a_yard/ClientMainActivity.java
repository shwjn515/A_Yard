package com.example.a_yard;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a_yard.databinding.ClientMainBinding;

public class ClientMainActivity extends AppCompatActivity {

    private ClientMainBinding binding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ClientMainBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home2, R.id.navigation_dashboard2, R.id.navigation_notifications2)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_client_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding2.navView, navController);
    }
}