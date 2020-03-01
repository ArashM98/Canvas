package com.ArashTorDev.tablo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.skydoves.elasticviews.ElasticButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_Main extends AppCompatActivity {

    //variables
    RecyclerView recyclerView_Styles, recyclerView_popularProducts, recyclerView_ArtistProfile;
    ElasticButton btnMenu, btnShoppingCart, btnOrder, btnAccount, btnSearch, btnSecondMenu, btnSetting, btnTermOfUse, btnAboutUs;
    TextView tvShoppingCart, tvOrder, tvAccount, tvSearch, tvAboutUs, tvSetting, tvTermOfUse, tvSecondMenu;
    String menuTag, closeMenuTag;

    Adapter_Styles_List_Main_Activity adapter_Styles_listMainActivity;
    Adapter_Popular_Arts_Main_Activity adapter_PopularProducts_list;
    Adapter_list_Artist_profile adapter_list_artist_profile;
    Adapter_ViewPager adapter_imageSlider_top;

    List<Model_list_1st_2nd> model_Styles_list;
    List<Model_list_1st_2nd> model_PopularProducts_list;
    List<Model_list_artists_profile> Model_list_artists_profiles;
    Model_list_1st_2nd model_1st_2nd;

    Uri uri = Uri.parse("R.drawable.no_image_billboard");
    String uriString = uri.toString();
    String[] imageUrls = {uriString, uriString, uriString, uriString};
    ViewPager imageSlider_top;
    View.OnClickListener onItemClickListener;

    ParseUser currentUserPU;

    Dao dao;
    Database database;

    Z_BMP_to_String_to_BMP z_bmp_to_string_to_bmp;

    // upgrade database version variables

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ArtsDatabase ADD COLUMN artInfo TEXT");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize variables
        recyclerView_Styles = findViewById(R.id.recyclerView_Styles);
        recyclerView_popularProducts = findViewById(R.id.recyclerView_PopularProducts);
        recyclerView_ArtistProfile = findViewById(R.id.recyclerView_ArtistProfile);
        btnMenu = findViewById(R.id.btnMenu);
        btnShoppingCart = findViewById(R.id.btnShoppingCart);
        tvShoppingCart = findViewById(R.id.tvShoppingCart);
        btnOrder = findViewById(R.id.btnOrder);
        btnSecondMenu = findViewById(R.id.btnSecondMenu);
        btnAboutUs = findViewById(R.id.btnAboutUs);
        btnTermOfUse = findViewById(R.id.btnTermOfUse);
        btnSetting = findViewById(R.id.btnSetting);
        tvOrder = findViewById(R.id.tvOrder);
        btnAccount = findViewById(R.id.btnAccount);
        tvAccount = findViewById(R.id.tvAccount);
        btnSearch = findViewById(R.id.btnSearch);
        tvSearch = findViewById(R.id.tvSearch);
        tvAboutUs = findViewById(R.id.tvAboutUs);
        tvSetting = findViewById(R.id.tvSetting);
        tvSecondMenu = findViewById(R.id.tvSecondMenu);
        tvTermOfUse = findViewById(R.id.tvTermOfUse);
        model_Styles_list = new ArrayList<>();
        model_PopularProducts_list = new ArrayList<>();
        Model_list_artists_profiles = new ArrayList<>();
        fillLists();
        adapter_Styles_listMainActivity = new Adapter_Styles_List_Main_Activity(model_Styles_list);
        adapter_PopularProducts_list = new Adapter_Popular_Arts_Main_Activity(model_PopularProducts_list);
        adapter_list_artist_profile = new Adapter_list_Artist_profile(Model_list_artists_profiles);
        adapter_imageSlider_top = new Adapter_ViewPager(this, imageUrls);
        imageSlider_top = findViewById(R.id.imageSlider_top);
        database = Room.databaseBuilder(this, Database.class, "ArtsDatabase").allowMainThreadQueries().build();
        dao = database.getDao();
        z_bmp_to_string_to_bmp = new Z_BMP_to_String_to_BMP();

        // start downloading database service
        Intent downloadingService = new Intent(Activity_Main.this, Service_getArtsDatabase.class);
        downloadingService.putExtra("databaseClassName", getResources().getString(R.string.Database_Server_Arts_className));
        downloadingService.putExtra("ArtsType", getResources().getString(R.string.artsType));
        startService(downloadingService);

        //create layout manager for recycler views
        LinearLayoutManager manager0 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        LinearLayoutManager manager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);

        // set layout manager and countryListAdapter for list_1st_recyclerView and list_2nd_recyclerView
        recyclerView_ArtistProfile.setLayoutManager(manager0);
        recyclerView_ArtistProfile.setAdapter(adapter_list_artist_profile);
        recyclerView_Styles.setLayoutManager(manager1);
        recyclerView_Styles.setAdapter(adapter_Styles_listMainActivity);
        recyclerView_popularProducts.setLayoutManager(manager2);
        recyclerView_popularProducts.setAdapter(adapter_PopularProducts_list);
        imageSlider_top.setAdapter(adapter_imageSlider_top);

        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder selected_item = (RecyclerView.ViewHolder) view.getTag();
                int position = selected_item.getAdapterPosition();
                model_1st_2nd = model_Styles_list.get(position);
                Intent intent = new Intent(Activity_Main.this, Activity_Styles.class);
                intent.putExtra("whatStyle?", model_1st_2nd.getItem_title());
                startActivity(intent);
            }
        };
    }

    public Database getDatabase() {
        return database;
    }

    public void openMenu(View view) {
        openCloseMenu();
    }

    public void openCloseMenu() {
        // moving button like a drawer menu
        menuTag = getResources().getString(R.string.btn_menu_main_activity);
        closeMenuTag = getResources().getString(R.string.btn_close_menu_main_activity);
        String btnMenuTag = btnMenu.getTag().toString();

        if (btnMenuTag.equals(menuTag)) {
            ObjectAnimator animatorOrderBtn = ObjectAnimator.ofFloat(btnOrder, "translationY", -115F);
            animatorOrderBtn.setDuration(100).start();
            tvOrder.animate().alpha(1).setDuration(2000).start();
            ObjectAnimator animatorOrderTv = ObjectAnimator.ofFloat(tvOrder, "translationY", -115F);
            animatorOrderTv.setDuration(100).start();

            ObjectAnimator animatorSearchBtn = ObjectAnimator.ofFloat(btnSearch, "translationY", -230F);
            animatorSearchBtn.setDuration(300).start();
            tvSearch.animate().alpha(1).setDuration(2000).start();
            ObjectAnimator animatorSearchTv = ObjectAnimator.ofFloat(tvSearch, "translationY", -230F);
            animatorSearchTv.setDuration(300).start();

            ObjectAnimator animatorAccountBtn = ObjectAnimator.ofFloat(btnAccount, "translationY", -345F);
            animatorAccountBtn.setDuration(500).start();
            tvAccount.animate().alpha(1).setDuration(2000).start();
            ObjectAnimator animatorAccountTv = ObjectAnimator.ofFloat(tvAccount, "translationY", -345F);
            animatorAccountTv.setDuration(500).start();

            ObjectAnimator animatorShoppingCartBtn = ObjectAnimator.ofFloat(btnShoppingCart, "translationY", -460F);
            animatorShoppingCartBtn.setDuration(700).start();
            tvShoppingCart.animate().alpha(1).setDuration(2000).start();
            ObjectAnimator animatorShoppingCartTv = ObjectAnimator.ofFloat(tvShoppingCart, "translationY", -460F);
            animatorShoppingCartTv.setDuration(700).start();

            SecondMenuBtnMoving(menuTag);


//              commands below will rotate the menu button - but it stuck in loop and execute for ever
//            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
//            btnMenu.startAnimation(animation);

            //changing tag to arrow down
            btnMenu.setTag(closeMenuTag);

        } else if (btnMenuTag.equals(closeMenuTag)) {

            ObjectAnimator animatorOrderBtn = ObjectAnimator.ofFloat(btnOrder, "translationY", 0);
            animatorOrderBtn.setDuration(100).start();
            tvOrder.animate().alpha(0).setDuration(500).start();
            ObjectAnimator animatorOrderTv = ObjectAnimator.ofFloat(tvOrder, "translationY", 0);
            animatorOrderTv.setDuration(100).start();

            ObjectAnimator animatorSearchBtn = ObjectAnimator.ofFloat(btnSearch, "translationY", 0);
            animatorSearchBtn.setDuration(300).start();
            tvSearch.animate().alpha(0).setDuration(500).start();
            ObjectAnimator animatorSearchTv = ObjectAnimator.ofFloat(tvSearch, "translationY", 0);
            animatorSearchTv.setDuration(300).start();

            ObjectAnimator animatorAccountBtn = ObjectAnimator.ofFloat(btnAccount, "translationY", 0);
            animatorAccountBtn.setDuration(500).start();
            tvAccount.animate().alpha(0).setDuration(500).start();
            ObjectAnimator animatorAccountTv = ObjectAnimator.ofFloat(tvAccount, "translationY", 0);
            animatorAccountTv.setDuration(500).start();

            ObjectAnimator animatorShoppingCartBtn = ObjectAnimator.ofFloat(btnShoppingCart, "translationY", 0);
            animatorShoppingCartBtn.setDuration(700).start();
            tvShoppingCart.animate().alpha(0).setDuration(500).start();
            ObjectAnimator animatorShoppingCartTv = ObjectAnimator.ofFloat(tvShoppingCart, "translationY", 0);
            animatorShoppingCartTv.setDuration(700).start();

            SecondMenuBtnMoving(closeMenuTag);
            closeSecondMenu();

//              commands below will rotate the menu button - but it stuck in loop and execute for ever
//            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
//            btnMenu.startAnimation(animation);

            // changing tag to arrow up
            btnMenu.setTag(menuTag);
        }
    }

    public void closeSecondMenu() {

        ObjectAnimator animatorAboutUsBtn = ObjectAnimator.ofFloat(btnAboutUs, "translationY", 0);
        ObjectAnimator animatorAboutUsBtn2 = ObjectAnimator.ofFloat(btnAboutUs, "translationX", 0);
        animatorAboutUsBtn.setDuration(700).start();
        animatorAboutUsBtn2.setDuration(700).start();
        tvAboutUs.animate().alpha(0).setDuration(500).start();
        ObjectAnimator animatorAboutUsTv = ObjectAnimator.ofFloat(tvAboutUs, "translationY", 0);
        ObjectAnimator animatorAboutUsTv2 = ObjectAnimator.ofFloat(tvAboutUs, "translationX", 0);
        animatorAboutUsTv.setDuration(700).start();
        animatorAboutUsTv2.setDuration(700).start();

        ObjectAnimator animatorTermOfUse = ObjectAnimator.ofFloat(btnTermOfUse, "translationX", 0);
        animatorTermOfUse.setDuration(700).start();
        tvTermOfUse.animate().alpha(0).setDuration(500).start();
        ObjectAnimator animatorTermOdUseTv = ObjectAnimator.ofFloat(tvAboutUs, "translationX", 0);
        animatorTermOdUseTv.setDuration(700).start();

        ObjectAnimator animatorSettingBtn = ObjectAnimator.ofFloat(btnSetting, "translationY", 0);
        ObjectAnimator animatorSettingBtn2 = ObjectAnimator.ofFloat(btnSetting, "translationX", 0);
        animatorSettingBtn.setDuration(700).start();
        animatorSettingBtn2.setDuration(700).start();
        tvAboutUs.animate().alpha(0).setDuration(500).start();
        ObjectAnimator animatorSettingTv = ObjectAnimator.ofFloat(tvSetting, "translationY", 0);
        ObjectAnimator animatorSettingTv2 = ObjectAnimator.ofFloat(tvSetting, "translationX", 0);
        animatorSettingTv.setDuration(700).start();
        animatorSettingTv2.setDuration(700).start();
    }

    public void openSecondMenu(View view) {
        String tag = btnSecondMenu.getTag().toString();
        if (tag.equals("btnSecondMenuOpen")) {

            ObjectAnimator animatorAboutUsBtn = ObjectAnimator.ofFloat(btnAboutUs, "translationY", -690F);
            ObjectAnimator animatorAboutUsBtn2 = ObjectAnimator.ofFloat(btnAboutUs, "translationX", -115F);
            animatorAboutUsBtn.setDuration(350).start();
            animatorAboutUsBtn2.setDuration(350).start();
            tvAboutUs.animate().alpha(1).setDuration(500).start();
            ObjectAnimator animatorAboutUsTv = ObjectAnimator.ofFloat(tvAboutUs, "translationY", -690F);
            ObjectAnimator animatorAboutUsTv2 = ObjectAnimator.ofFloat(tvAboutUs, "translationX", -115F);
            animatorAboutUsTv.setDuration(350).start();
            animatorAboutUsTv2.setDuration(350).start();

            ObjectAnimator animatorTermOfUse = ObjectAnimator.ofFloat(btnTermOfUse, "translationX", -115F);
            animatorTermOfUse.setDuration(350).start();
            tvTermOfUse.animate().alpha(1).setDuration(500).start();
            ObjectAnimator animatorTermOfUseTv = ObjectAnimator.ofFloat(tvTermOfUse, "translationX", -130F);
            animatorTermOfUseTv.setDuration(350).start();

            ObjectAnimator animatorSettingBtn = ObjectAnimator.ofFloat(btnSetting, "translationY", -460F);
            ObjectAnimator animatorSettingBtn2 = ObjectAnimator.ofFloat(btnSetting, "translationX", -115F);
            animatorSettingBtn.setDuration(350).start();
            animatorSettingBtn2.setDuration(350).start();
            tvSetting.animate().alpha(1).setDuration(500).start();
            ObjectAnimator animatorSettingTv = ObjectAnimator.ofFloat(tvSetting, "translationY", -460F);
            ObjectAnimator animatorSettingTv2 = ObjectAnimator.ofFloat(tvSetting, "translationX", -115F);
            animatorSettingTv.setDuration(350).start();
            animatorSettingTv2.setDuration(350).start();

            tvSecondMenu.animate().alpha(0).setDuration(1000).start();
            tvShoppingCart.animate().alpha(0).setDuration(1000).start();

            btnSecondMenu.setTag("closeBtnSecondMenu");

        } else if (tag.equals("closeBtnSecondMenu")) {

            ObjectAnimator animatorAboutUsBtn = ObjectAnimator.ofFloat(btnAboutUs, "translationY", -575F);
            ObjectAnimator animatorAboutUsBtn2 = ObjectAnimator.ofFloat(btnAboutUs, "translationX", 0);
            animatorAboutUsBtn.setDuration(350).start();
            animatorAboutUsBtn2.setDuration(350).start();
            tvAboutUs.animate().alpha(0).setDuration(500).start();
            ObjectAnimator animatorAboutUsTv = ObjectAnimator.ofFloat(tvAboutUs, "translationY", -575F);
            ObjectAnimator animatorAboutUsTv2 = ObjectAnimator.ofFloat(tvAboutUs, "translationX", 0);
            animatorAboutUsTv.setDuration(350).start();
            animatorAboutUsTv2.setDuration(350).start();

            ObjectAnimator animatorTermOfUse = ObjectAnimator.ofFloat(btnTermOfUse, "translationX", 0);
            animatorTermOfUse.setDuration(350).start();
            tvTermOfUse.animate().alpha(0).setDuration(500).start();
            ObjectAnimator animatorTermOdUseTv = ObjectAnimator.ofFloat(tvTermOfUse, "translationX", 0);
            animatorTermOdUseTv.setDuration(350).start();

            ObjectAnimator animatorSettingBtn = ObjectAnimator.ofFloat(btnSetting, "translationY", -575F);
            ObjectAnimator animatorSettingBtn2 = ObjectAnimator.ofFloat(btnSetting, "translationX", 0);
            animatorSettingBtn.setDuration(350).start();
            animatorSettingBtn2.setDuration(350).start();
            tvSetting.animate().alpha(0).setDuration(500).start();
            ObjectAnimator animatorSettingTv = ObjectAnimator.ofFloat(tvSetting, "translationY", -575F);
            ObjectAnimator animatorSettingTv2 = ObjectAnimator.ofFloat(tvSetting, "translationX", 0);
            animatorSettingTv.setDuration(350).start();
            animatorSettingTv2.setDuration(350).start();

            tvSecondMenu.animate().alpha(1).setDuration(1000).start();
            tvShoppingCart.animate().alpha(1).setDuration(1000).start();

            btnSecondMenu.setTag("btnSecondMenuOpen");

        }
    }

    public void SecondMenuBtnMoving(String tag) {
        if (tag.equals(menuTag)) {

            ObjectAnimator animatorSecondMenuBtn = ObjectAnimator.ofFloat(btnSecondMenu, "translationY", -575F);
            animatorSecondMenuBtn.setDuration(700).start();
            tvSecondMenu.animate().alpha(1).setDuration(2000).start();
            ObjectAnimator animatorSecondMenuTv = ObjectAnimator.ofFloat(tvSecondMenu, "translationY", -575F);
            animatorSecondMenuTv.setDuration(700).start();

            ObjectAnimator animatorSettingBtn = ObjectAnimator.ofFloat(btnSetting, "translationY", -575F);
            animatorSettingBtn.setDuration(700).start();
            ObjectAnimator animatorSettingTv = ObjectAnimator.ofFloat(tvSetting, "translationY", -575F);
            animatorSettingTv.setDuration(700).start();

            ObjectAnimator animatorTermOfUseBtn = ObjectAnimator.ofFloat(btnTermOfUse, "translationY", -575F);
            animatorTermOfUseBtn.setDuration(700).start();
            ObjectAnimator animatorTermOfUseTv = ObjectAnimator.ofFloat(tvTermOfUse, "translationY", -575F);
            animatorTermOfUseTv.setDuration(700).start();

            ObjectAnimator animatorAboutUsBtn = ObjectAnimator.ofFloat(btnAboutUs, "translationY", -575F);
            animatorAboutUsBtn.setDuration(700).start();
            ObjectAnimator animatorAboutUsTv = ObjectAnimator.ofFloat(tvAboutUs, "translationY", -575F);
            animatorAboutUsTv.setDuration(700).start();

        } else if (tag.equals(closeMenuTag)) {

            ObjectAnimator animatorSecondMenuBtn = ObjectAnimator.ofFloat(btnSecondMenu, "translationY", 0);
            animatorSecondMenuBtn.setDuration(700).start();
            tvSecondMenu.animate().alpha(0).setDuration(2000).start();
            ObjectAnimator animatorSecondMenuTv = ObjectAnimator.ofFloat(tvSecondMenu, "translationY", 0);
            animatorSecondMenuTv.setDuration(700).start();

            ObjectAnimator animatorSettingBtn = ObjectAnimator.ofFloat(btnSetting, "translationY", 0);
            animatorSettingBtn.setDuration(700).start();
            tvSetting.animate().alpha(0).setDuration(2000).start();
            ObjectAnimator animatorSettingTv = ObjectAnimator.ofFloat(tvSetting, "translationY", 0);
            animatorSettingTv.setDuration(700).start();

            ObjectAnimator animatorTermOfUseBtn = ObjectAnimator.ofFloat(btnTermOfUse, "translationY", 0);
            animatorTermOfUseBtn.setDuration(700).start();
            tvTermOfUse.animate().alpha(0).setDuration(2000).start();
            ObjectAnimator animatorTermOfUseTv = ObjectAnimator.ofFloat(tvTermOfUse, "translationY", 0);
            animatorTermOfUseTv.setDuration(700).start();

            ObjectAnimator animatorAboutUsBtn = ObjectAnimator.ofFloat(btnAboutUs, "translationY", 0);
            animatorAboutUsBtn.setDuration(700).start();
            tvAboutUs.animate().alpha(0).setDuration(2000).start();
            ObjectAnimator animatorAboutUsTv = ObjectAnimator.ofFloat(tvAboutUs, "translationY", 0);
            animatorAboutUsTv.setDuration(700).start();

        }

    }

    public void menuButtonsAction(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                Intent intent = new Intent(Activity_Main.this, Activity_ListOfArts.class);
                intent.putExtra("whatStyle?", "Search");
                startActivity(intent);
                break;
            case R.id.btnAccount:
                if (currentUserPU != null) {
                    Intent intent2 = new Intent(Activity_Main.this, Activity_Account.class);
                    startActivity(intent2);
                } else {
                    Intent intent1 = new Intent(Activity_Main.this, Activity_SignUp_SignIn.class);
                    startActivity(intent1);
                }
                break;
            case R.id.btnShoppingCart:
                Intent intent3 = new Intent(Activity_Main.this, Activity_Shopping_cart.class);
                startActivity(intent3);
                break;
            case R.id.btnOrder:
                if (currentUserPU != null) {
                    Intent intent4 = new Intent(Activity_Main.this, Activity_OrderPicture.class);
                    startActivity(intent4);
                    Toast.makeText(this, getResources().getString(R.string.you_must_log_in), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(Activity_Main.this, Activity_SignUp_SignIn.class);
                    startActivity(intent1);
                }
                break;
            case R.id.btnSetting:
                Intent intent5 = new Intent(Activity_Main.this, Activity_settings_new.class);
                startActivity(intent5);
                break;
        }
        openCloseMenu();
    }

    public void fillLists() {
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_realism_henri_biva), "Realism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.surrealism_max_ernest), "Surrealism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_impressionism_renoir_canoist), "Impressionism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_neo_impressionism_paul_signac), "Neo-Impressionism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_post_impressionism_charles_theophile_angrand), "Post-Impressionism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_expressionism_edward_monk), "Expressionism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_abstract_expressionism_), "Abstract-Expressionism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_romanticism_caspar_david_friedrich), "Romanticism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_futurism), "Futurism"));
        model_Styles_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.style_list_cubism_pablo_picasso), "Cubism"));

        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item1"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item2"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item3"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item4"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item5"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item6"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item3"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item4"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item5"));
        model_PopularProducts_list.add(new Model_list_1st_2nd(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "item6"));

        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
        Model_list_artists_profiles.add(new Model_list_artists_profile(BitmapFactory.decodeResource(getResources(), R.drawable.no_image), "Name", "LastName", "No Method", 2.5F));
    }

    public void goToPopularArts(View view) {
        // this send to list of arts activity will send information for searching popular arts
        Intent intent = new Intent(Activity_Main.this, Activity_ListOfArts.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // this will show us a dialog box and ask us to "want to exit?"
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get current user information if logged in
        currentUserPU = ParseUser.getCurrentUser();
        if (currentUserPU != null) {
            getCurrentUserInformation();
        }
    }

    public void getCurrentUserInformation() {
        Model_Database_Users currentUserDB = new Model_Database_Users();
        currentUserDB.setName(currentUserPU.getUsername());
        currentUserDB.setEmail(currentUserPU.getEmail());
        currentUserDB.setPhoneNumber(Objects.requireNonNull(currentUserPU.get(getResources().getString(R.string.ParseUser_PhoneNumber))).toString());
        currentUserDB.setArtistOrBuyer(Objects.requireNonNull(currentUserPU.get(getResources().getString(R.string.ParseUser_Status))).toString());
        currentUserDB.setParseUserId(currentUserPU.getObjectId());
        Bitmap b = null;
        try {
            currentUserDB.setUserImageByteArray(Objects.requireNonNull(currentUserPU.get("userPic")).toString());
        } catch (java.lang.NullPointerException e) {
            // get and change the no image BMP to string and set it to the database
            b = BitmapFactory.decodeResource(getResources(), R.drawable.shopping_cart_no_image);
        }
        // change bitmap to string
        currentUserDB.setUserImageByteArray(z_bmp_to_string_to_bmp.bitmapToString(b));

        // add user to local database
        dao.insertUser(currentUserDB);

        Log.i("get current user info :", "insert user to database is done\ngetCurrentUserInformation_ActivityMain");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Request Codes
        int UPDATE_REQUEST_CODE = 101;
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "Update failed\n" + resultCode, Toast.LENGTH_LONG).show();
            }
        }
    }
}
