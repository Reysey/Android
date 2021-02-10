package com.reydev.tuto.androiddatabase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.String.valueOf;

public class IMC extends AppCompatActivity {
    EditText longueur;
    EditText poids;
    EditText age;
    TextView resultat;
    TextView imcmin,imcmax;
    Button calculer;
    Button effacer;
    Button conseilIMC;
    double val1=Double.NaN;
    double val2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        getSupportActionBar().setTitle("Calculer Votre IMC(Indice de masse corporel)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        longueur = (EditText) findViewById(R.id.longueur);
        poids = (EditText) findViewById(R.id.poids);
        age = (EditText) findViewById(R.id.age);
        resultat = (TextView)findViewById(R.id.resultat);
        //imcmin = (TextView)findViewById(R.id.IMCmin);
        //imcmax = (TextView)findViewById(R.id.IMCmax);
        //conseil =(TextView) findViewById(R.id.conseil);
        calculer =(Button) findViewById(R.id.btn_recherche);
        effacer =(Button) findViewById(R.id.btn_restart);
        conseilIMC =(Button) findViewById(R.id.btn_conseil);

        calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* String vp = poids.getText().toString();
                String vl = longueur.getText().toString();
                String va = age.getText().toString();*/
                double na = Double.parseDouble(age.getText().toString());
                double nl = Double.parseDouble(longueur.getText().toString());
                double np = Double.parseDouble(poids.getText().toString());
                if(!Double.isNaN(np)&& !Double.isNaN(nl)&& !Double.isNaN(na)){
                    double r = ((np/(nl*nl))*10000);
                    r=Math.round(r);
                   // double  rmin= ((18/10000)*(nl*nl));
                    //rmin=Math.round(rmin);
                    //double  rmax= ((float)(30/10000)*(nl*nl));
                   // rmax=Math.round(rmax);
                    resultat.setText(valueOf(r).toString());
                   // imcmin.setText(valueOf(rmin).toString());
                    //imcmax.setText(valueOf(rmax).toString());

                    if(r<18){

                        Toast.makeText(getApplicationContext(),"Vous etes trop Maigre comme valeur IMC : "+r+" ",Toast.LENGTH_SHORT).show();
                        calculer.setBackgroundColor(Color.parseColor("#c7cf00"));
                        resultat.setBackgroundColor(Color.parseColor("#efd807"));


                    }
                    else{
                        if(r==18){

                            Toast.makeText(getApplicationContext(),"Vous Avez le Poids Idèal "+r+" ",Toast.LENGTH_SHORT).show();
                            //document.ex1.nouvelle_imc.style.backgroundColor='green';

                            calculer.setBackgroundColor(Color.parseColor("#9c9b3d"));
                            resultat.setBackgroundColor(Color.parseColor("#a5af3e"));

                        }
                        else{
                            if(r>18 && r<25){

                                Toast.makeText(getApplicationContext(),"Vous Avez un Poids Normal, une bonne IMC "+r+" ",Toast.LENGTH_SHORT).show();
                                //document.ex1.nouvelle_imc.style.backgroundColor='green';

                                calculer.setBackgroundColor(Color.parseColor("#92ff15"));
                                resultat.setBackgroundColor(Color.parseColor("#92ff15"));
                            }
                            else{
                                if(r==25){

                                    Toast.makeText(getApplicationContext(),"Vous etes au Limite du Poids Normal "+r+" ",Toast.LENGTH_SHORT).show();
                                    // document.ex1.nouvelle_imc.style.backgroundColor='gray';

                                    calculer.setBackgroundColor(Color.parseColor("#677179"));
                                    resultat.setBackgroundColor(Color.parseColor("#8a8a8a"));

                                }
                                else{

                                    Toast.makeText(getApplicationContext(),"Vous etes Obése "+r+" ",Toast.LENGTH_SHORT).show();
                                    // document.ex1.nouvelle_imc.style.backgroundColor='red';


                                    calculer.setBackgroundColor(Color.parseColor("#ff7f00"));
                                    resultat.setBackgroundColor(Color.parseColor("#ff151c"));
                                }
                            }}
                    }







                }else{
                    Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs !!",Toast.LENGTH_SHORT).show();
                }

            }


        });

        conseilIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent= new Intent(IMC.this,MainActivitytestHtml.class);
              startActivity(intent);

            }


        });


    }

    public void clear(View v) {

        effacer.setOnClickListener(new ImageView.OnClickListener(){

            public void onClick(View v){

                Toast.makeText(IMC.this,"C'est  effacé !!! ", Toast.LENGTH_LONG).show();
                 longueur.setText("");
                 poids.setText("");
                 age.setText("");
                 resultat.setText("0");
                
            }

        });
    }

}