package com.nuomi.root.tesla_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by root on 15-7-25.
 */
public class TeslaViewActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;
    private long exitTime = 0;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer teslaDrawer = null;

    private IProfile vvProfile;
    private IProfile lihuaProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tesla_home);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);

        //create vv's tesla profile

        vvProfile = new ProfileDrawerItem()
                .withIdentifier(1)
                .withName("赵玮炜")
                .withEmail("vvzvv@outlook.com")
                .withIcon(getResources().getDrawable(R.drawable.qq));
        lihuaProfile = new ProfileDrawerItem()
                .withIdentifier(2)
                .withName("陈丽华")
                .withEmail("519469410@qq.com")
                .withIcon(getResources().getDrawable(R.drawable.lihua));

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        teslaDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("vv test Primery Drawer")

                )
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        System.out.println("Test click an item");
                        TeslaViewActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            System.out.println("Item ID:"+drawerItem.getIdentifier()+" Clicked");
                        }
                        return false;
                    }
                })
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName("vv Sticky Drawer Test")
                                .withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10)
                )
                .withAnimateDrawerItems(true)
                .withSavedInstance(savedInstanceState)
                .build();

    }


    /*
    * Buil Account header
    * this will be used to replace the header of the drawer with a compact/normal header
    * */
    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.carbackgroud)
                .withCompactStyle(compact)
                .addProfiles(
                        vvProfile,
                        lihuaProfile
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        switch (profile.getIdentifier()) {
                            case 1: {
                                Toast.makeText(TeslaViewActivity.this, "vv profile clicked!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 2: {
                                Toast.makeText(TeslaViewActivity.this, "lihua profile clicked!", Toast.LENGTH_SHORT).show();
                                break;
                            }

                            default:
                                break;
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();


    }

    @Override
    public void onBackPressed() {
        if(teslaDrawer != null && teslaDrawer.isDrawerOpen()){
            teslaDrawer.closeDrawer();
        }else
        if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState = teslaDrawer.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
