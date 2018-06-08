package com.example.ybbaek.test_popup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class PopupActivity extends AppCompatActivity {

    Button btn_menu_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        btn_menu_show = (Button)findViewById(R.id.btn_menu_show);

        btn_menu_show.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(
                    getApplicationContext(), v);
                    //화면제어권자,             기준객체
            getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(popupClick);
            popup.show();

        }
    };

    PopupMenu.OnMenuItemClickListener popupClick = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()){
                case R.id.menu1:
                    Toast.makeText(getApplicationContext(),"menu1 click", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.menu2:
                    Toast.makeText(getApplicationContext(),"menu2 click", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.menu3:
                    Toast.makeText(getApplicationContext(),"menu3 click", Toast.LENGTH_SHORT).show();
                    break;

            }

            return false;
        }
    };



}
