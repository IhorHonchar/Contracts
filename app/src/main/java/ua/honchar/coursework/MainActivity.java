package ua.honchar.coursework;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ua.honchar.coursework.fragments.dialogs.AddBranchDialogFragment;
import ua.honchar.coursework.fragments.dialogs.AddClientOperatorDialog;
import ua.honchar.coursework.fragments.dialogs.AddContractDialogFragment;
import ua.honchar.coursework.fragments.BranchesFragment;
import ua.honchar.coursework.fragments.ClientsFragment;
import ua.honchar.coursework.fragments.ContractsFragment;
import ua.honchar.coursework.fragments.OperatorsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle(R.string.contract);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, ContractsFragment.newInstance())
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()){
            case R.id.nav_clients:
                fragment = ClientsFragment.newInstance();
                toolbar.setTitle(R.string.clients);
                break;
            case R.id.nav_operators:
                fragment = OperatorsFragment.newInstance();
                toolbar.setTitle(R.string.operators);
                break;
            case R.id.nav_branches:
                fragment = BranchesFragment.newInstance();
                toolbar.setTitle(R.string.branches);
                break;
            default:
                fragment = ContractsFragment.newInstance();
                toolbar.setTitle(R.string.contracts);

        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_contract:
                DialogFragment create_contract = new AddContractDialogFragment();
                create_contract.show(getSupportFragmentManager(), "Create contract");
                break;

            case R.id.add_client:
                DialogFragment add_client = AddClientOperatorDialog.newInstance(AddClientOperatorDialog.CLIENTS);
                add_client.show(getSupportFragmentManager(), "Add client");
                break;

            case R.id.add_operator:
                DialogFragment add_operator = AddClientOperatorDialog.newInstance(AddClientOperatorDialog.OPERATORS);
                add_operator.show(getSupportFragmentManager(), "Add operator");
                break;

            case R.id.add_branch:
                DialogFragment add_branch = new AddBranchDialogFragment();
                add_branch.show(getSupportFragmentManager(), "Add branch");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}