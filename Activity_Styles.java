package com.ArashTorDev.tablo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Activity_Styles extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Model_list_style_StylesActivity> models;
    Adapter_StyleList_StyleActivity adapter;
    ListView listView_stylesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styles);
        // initialize variables
        models = new ArrayList<>();
        listView_stylesList = findViewById(R.id.listView_StylesList);
        fillList();
        adapter = new Adapter_StyleList_StyleActivity(this, models);
        listView_stylesList.setAdapter(adapter);
        listView_stylesList.setOnItemClickListener(this);
    }

    public void fillList() {
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Realism), getResources().getString(R.string.styleList_Realism_info), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_realism_henri_biva)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Surrealism), getResources().getString(R.string.styleList_SurrealismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.surrealism_max_ernest)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Impressionism), getResources().getString(R.string.styleList_ImpressionismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_impressionism_renoir_canoist)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Neo_Impressionism), getResources().getString(R.string.styleList_Neo_impressionismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_neo_impressionism_paul_signac)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Post_Impressionism), getResources().getString(R.string.styleList_Post_impressionismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_post_impressionism_charles_theophile_angrand)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Expressionism), getResources().getString(R.string.styleList_ExpressionismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_expressionism_edward_monk)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Abstract_Expressionism), getResources().getString(R.string.styleList_Abstract_Expressionism_info), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_abstract_expressionism_)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Romanticism), getResources().getString(R.string.styleList_RomanticismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_romanticism_caspar_david_friedrich)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Futurism), getResources().getString(R.string.styleList_FuturismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_futurism)));
        models.add(new Model_list_style_StylesActivity(getResources().getString(R.string.styleList_Cubism), getResources().getString(R.string.styleList_CubismInfo), BitmapFactory.decodeResource(getResources(), R.drawable.style_list_cubism_pablo_picasso)));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Intent intent = new Intent(Activity_Styles.this, Activity_ListOfArts.class);
        // all of these items guide us to the same activity but with different extras
        switch (position) {
            case 0:
                intent.putExtra("whatStyle?", "Realism");
                startActivity(intent);
                break;
            case 1:
                intent.putExtra("whatStyle?", "Surrealism");
                startActivity(intent);
                break;
            case 2:
                intent.putExtra("whatStyle?", "Impressionism");
                startActivity(intent);
                break;
            case 3:
                intent.putExtra("whatStyle?", "Neo-Impressionism");
                startActivity(intent);
                break;
            case 4:
                intent.putExtra("whatStyle?", "Post-Impressionism");
                startActivity(intent);
                break;
            case 5:
                intent.putExtra("whatStyle?", "Expressionism");
                startActivity(intent);
                break;
            case 6:
                intent.putExtra("whatStyle?", "Abstract-Expressionism");
                startActivity(intent);
                break;
            case 7:
                intent.putExtra("whatStyle?", "Romanticism");
                startActivity(intent);
                break;
            case 8:
                intent.putExtra("whatStyle?", "Futurism");
                startActivity(intent);
                break;
            case 9:
                intent.putExtra("whatStyle?", "Cubism");
                startActivity(intent);
                break;
        }


    }

}
