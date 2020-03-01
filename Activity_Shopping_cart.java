package com.ArashTorDev.tablo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Shopping_cart extends AppCompatActivity {

    ListView listView_shopping_cart_list;
    List<Model_shopping_cart_list> models_shoppingCartList;
    Adapter_shopping_cart_list adapter;
    View.OnClickListener onEliminationButtonClickListener, onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        // initialize Variables
        listView_shopping_cart_list = findViewById(R.id.listView_shopping_cart_list);
        models_shoppingCartList = new ArrayList<>();
        adapter = new Adapter_shopping_cart_list(this, models_shoppingCartList);
        // fill the list
        fillList();
        // setting countryListAdapter to list view
        listView_shopping_cart_list.setAdapter(adapter);

        onEliminationButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                models_shoppingCartList.remove(position);
                adapter.notifyDataSetChanged();
            }
        };

        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adapter_shopping_cart_list.ViewHolder holder = (Adapter_shopping_cart_list.ViewHolder) view.getTag();
                int position = holder.getAdapterPosition();
                Model_shopping_cart_list model = models_shoppingCartList.get(position);
                // here i have type codes that i want to do with the item of the list
                Toast.makeText(Activity_Shopping_cart.this, "you selected item : " + position, Toast.LENGTH_SHORT).show();
            }
        };

        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnEliminateButtonClickListener(onEliminationButtonClickListener);
    }

    private void fillList() {
        // getting information from no item
        String productName = getResources().getString(R.string.tv_NoName);
        String productArtist = getResources().getString(R.string.shoppingCartList_defineArtist) + getResources().getString(R.string.tv_NoArtists);
        String productSize = getResources().getString(R.string.tv_NoSize);
        String productSeller = getResources().getString(R.string.shoppingCartList_defineSeller) + getResources().getString(R.string.tv_NoSeller);
        String productGuarantee = getResources().getString(R.string.tv_NoGuarantee);
        Bitmap productPic = BitmapFactory.decodeResource(getResources(), R.drawable.shopping_cart_no_image);
        // fill the list
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        models_shoppingCartList.add(new Model_shopping_cart_list(productName, productSize, productArtist, productSeller, productGuarantee, productPic));
        // notify the countryListAdapter that the data is changed
        adapter.notifyDataSetChanged();
    }

    public void finishOrder(View view) {

    }
}
