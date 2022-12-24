package tech.mavica.cusine_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * CountryCosine Arraylist .
     */
    static ArrayList<String> countriesCusinesList=new ArrayList<>();
    static ArrayList<Integer> countriesFlagsList=new ArrayList<>();
    static ArrayList<String> cusinesDescriptionList=new ArrayList<>();
    static ArrayList<Integer> ids=new ArrayList<Integer>();
    static CountriesCusinesAdapter countriesCusinesAdapter;
    static ListView listOfCusinesCountires;
    static Context context;
    /**
     * Country Cosine DBHelper and DBManager .
     */
    static DBHelper_CountriesCusines dbHelper_countriesCusines;
    static DBManager_CountriesCusines dbManager_countriesCusines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        /**
         * create database and get SQLiteDatabase Object .
         */
        dbHelper_countriesCusines=new DBHelper_CountriesCusines(this);
        /**
         * get SQLIte database Object that will be used in the whole CRUD Operations .
         */
        dbManager_countriesCusines = new DBManager_CountriesCusines(MainActivity.this,dbHelper_countriesCusines);

        listOfCusinesCountires=findViewById(R.id.lv_countires_cusines_list);
        /**
         * Set title for Page .
         */
        this.setTitle("Cusine App");


       // dbManager_countriesCusines.insertNewCusine("Egypt","Try and taste all Egyptian Food",R.drawable.egypt_flag);
       // dbManager_countriesCusines.insertNewCusine("Italy","Try and taste all Italian Food",R.drawable.italy_flag);
       // dbManager_countriesCusines.insertNewCusine("india","Try and taste all Indian Food",R.drawable.india_flag);
        /**
         * it will rebuild UI Each time you open this page .
         * rebuild mean clear all data inside ArrayList , get all recored from database
         * and pass all these records to ArrayLists .
         */
        refreshUI();
//        ids.add(1);
//        ids.add(2);
//        ids.add(3);
//
//        countriesCusinesList.add("Egypt");
//        countriesCusinesList.add("Italy");
//        countriesCusinesList.add("india");
//
//        cusinesDescriptionList.add("Try and taste all Egyptian Food");
//        cusinesDescriptionList.add("Try and taste all Italian Food");
//        cusinesDescriptionList.add("Try and taste all indian Food");
//
//        countriesFlagsList.add(R.drawable.egypt_flag);
//        countriesFlagsList.add(R.drawable.italy_flag);
//        countriesFlagsList.add(R.drawable.india_flag);

        /**
         * pass ArrayLists to adapter
         */
        countriesCusinesAdapter=new CountriesCusinesAdapter(this,countriesCusinesList,cusinesDescriptionList,countriesFlagsList);
        /**
         * Add adapter to listView .
         */
        listOfCusinesCountires.setAdapter(countriesCusinesAdapter);
        /**
         * when you press on any item , it navigate to this country foods page :
         */
        listOfCusinesCountires.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                Toast.makeText(MainActivity.this, "id in database : "+ids.get(index) ,Toast.LENGTH_SHORT).show();
                 showPopupMenu(view,ids.get(index));
                return true;
            }
        });
    }



    /**
     * it used to refresh Ui each time you open this page .
     */
    static void refreshUI(){
        Cursor cursor = dbManager_countriesCusines.display();
        countriesCusinesList.clear();
        countriesFlagsList.clear();
        cusinesDescriptionList.clear();
        while(cursor.moveToNext()){
            ids.add(cursor.getInt(0));
            countriesCusinesList.add(cursor.getString(1));
            cusinesDescriptionList.add(cursor.getString(2));
            countriesFlagsList.add(cursor.getInt(3));
        }
        // TODO , base new array lists to adapter .
        countriesCusinesAdapter = new CountriesCusinesAdapter(context,countriesCusinesList,cusinesDescriptionList,countriesFlagsList);
        listOfCusinesCountires.setAdapter(countriesCusinesAdapter);
    }




    /**
     * open dialog to insert new Cusine .
     * @param view
     */
    public void insert_new_cusine(View view){
        // TODO insert new cusine_country here .
        openDialog();
    }

    /**
     * Show Edit , Delete Popup Menu .
     * @param view
     */
    void showPopupMenu(View view,int idInDatabase){
        PopupMenu popup = new PopupMenu(MainActivity.this,view);
        popup.inflate(R.menu.edit_delete_menu);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.edit:
                        // TODO : implement edit method
                        Toast.makeText(MainActivity.this, "Edit clicked , id : "+idInDatabase, Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.Delete:
                        Toast.makeText(MainActivity.this, "Delete Clicked id : "+idInDatabase, Toast.LENGTH_SHORT).show();
                        // TODO : implement delete method .
                        dbManager_countriesCusines.deleteCusine(idInDatabase);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


    public void openDialog(){
        NewCusineDialogClass dialog = new NewCusineDialogClass(dbManager_countriesCusines);
        dialog.show(getSupportFragmentManager(),null);
    }

}


