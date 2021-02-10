package com.reydev.tuto.androiddatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseAccess {




    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;
    private DatabaseAccess(Context context){

        this.openHelper = new DatabaseOpenHelper(context);
    }


    public static DatabaseAccess getInstance(Context context){

        if(instance == null){

            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close (){
        if(db !=null){

            this.db.close();
        }
    }
    ArrayList<HashMap<String,String>> arl = new ArrayList<HashMap<String, String>>();

    /*
     * Recherche 3:
     *   Methode A :
     * */
    public ArrayList<HashMap<String,String>> getPh_CP(String codepostal){

        ArrayList<HashMap<String,String>> ar1 = new ArrayList<HashMap<String,String>>();

        c  =db.rawQuery("SELECT DISTINCT  PH_NAME,PH_CODEPOSTAL " +
                "FROM  parmacie" +
                " WHERE  PH_CODEPOSTAL ='"+codepostal +"'",new String[]{});

        while (c.moveToNext()){
            HashMap<String,String> map;
            map = new HashMap<String, String>();
            map.put("name",c.getString(0));
            map.put("codepostal",c.getString(1));
            ar1.add(map);

        }
        return ar1;
    }


    /*
     * Recherche 3:
     *   Methode C :
     * */
    public ArrayList<HashMap<String,String>> getPh_CN(String codepostal,String nomph){

        ArrayList<HashMap<String,String>> ar1 = new ArrayList<HashMap<String,String>>();


        c = db.rawQuery("" +
                "SELECT DISTINCT   PH_NAME,PH_CODEPOSTAL" +
                " FROM   parmacie" +
                " WHERE  UPPER(PH_NAME)= UPPER('"+nomph+"') and PH_CODEPOSTAL='"+codepostal+"'",new String[]{});




        while (c.moveToNext()){
            HashMap<String,String> map;
            map = new HashMap<String, String>();
            map.put("namePh",c.getString(0));
            map.put("codepostalPh",c.getString(1));

            ar1.add(map);

        }
        return ar1;
    }
    /*
     * AutoComplitTextView : pour CODEPOSTAL
     * */
    public ArrayList<String> reqcodepostalph(){
        ArrayList<String> arr=new ArrayList<>();
        c=db.rawQuery("select PH_CODEPOSTAL  from parmacie ",new String[]{});
        while(c.moveToNext()){

            arr.add(c.getString(0));
        } return arr;
    }



    /*
     * AutoComplitTextView : pour NOMPH
     * */
    public ArrayList<String> reqnomph(String codepostal){
        ArrayList<String> arr=new ArrayList<>();
        c=db.rawQuery("select PH_NAME " +
                " from parmacie" +
                "  WHERE PH_CODEPOSTAL ='"+codepostal+"';",new String[]{});
        while(c.moveToNext()){

            arr.add(c.getString(0));
        } return arr;
    }

    //test

    ArrayList<Pharmacy> pharmacies;
    public ArrayList<HashMap<String,String>> getPhT_CN(String nomph,String codepostal){

        ArrayList<HashMap<String,String>> ar1 = new ArrayList<HashMap<String,String>>();


        c = db.rawQuery("" +
                "SELECT DISTINCT   PH_NAME,PH_FIX,PH_ADDRESS,PH_CODEPOSTAL,PH_LATITUDE,PH_LONGITUDE" +
                " FROM   parmacie" +
                " WHERE  UPPER(PH_NAME) = UPPER('"+nomph+"') and PH_CODEPOSTAL ='"+codepostal+"'",new String[]{});
        while (c.moveToNext()){

            HashMap<String,String> map;
            map = new HashMap<String, String>();
            map.put("name",c.getString(0));
            map.put("tel",c.getString(1));
            map.put("address",c.getString(2));
            map.put("codepostal",c.getString(3));
            map.put("longitude", String.valueOf(c.getDouble(4)));
            map.put("laltitude", String.valueOf(c.getDouble(5)));


            ar1.add(map);
        }

        return ar1;
    }

    /*
     * Donnes pharmacie:
     *Methode general3*1
     * */

    public ArrayList<HashMap<String,String>> getPhNF_N(String nomph){

        ArrayList<HashMap<String,String>> ar1 = new ArrayList<HashMap<String,String>>();


        c = db.rawQuery("" +
                "SELECT DISTINCT   PH_NAME,PH_FIX,PH_ADDRESS," +
                " FROM   parmacie" +
                " WHERE  UPPER(PH_NAME) = UPPER('"+nomph+"')",new String[]{});
        while (c.moveToNext()){

            HashMap<String,String> map;
            map = new HashMap<String, String>();
            map.put("laltitudePh",c.getString(0));
            map.put("longitudePh",c.getString(1));


            ar1.add(map);
        }

        return ar1;
    }

    public ArrayList<HashMap<String,String>> getPhAN_N(String nomph){

        ArrayList<HashMap<String,String>> ar1 = new ArrayList<HashMap<String, String>>();


        c = db.rawQuery("" +
                "SELECT DISTINCT   PH_NAME,PH_ADDRESS" +
                " FROM   pharmacies" +
                " WHERE  PH_NAME = UPPER('"+nomph+"')",new String[]{});




        while (c.moveToNext()){
            HashMap<String,String> map;
            map = new HashMap<String, String>();
            map.put("nomPh",c.getString(0));
            map.put("addressPh",c.getString(1));



            ar1.add(map);

        }
        return ar1;
    }
    public ArrayList<HashMap<String,String>> getPhLL_N(String nomph){

        ArrayList<HashMap<String,String>> ar1 = new ArrayList<HashMap<String, String>>();


        c = db.rawQuery("" +
                "SELECT DISTINCT   PH_LATITUDE,PH_LONGITUDE" +
                " FROM   pharmacies" +
                " WHERE  PH_NAME = UPPER('"+nomph+"')",new String[]{});




        while (c.moveToNext()){
            HashMap<String,String> map;
            map = new HashMap<String, String>();
            map.put("laltitudePh",c.getString(0));
            map.put("longitudePh",c.getString(1));


            ar1.add(map);

        }
        return ar1;
    }


}
