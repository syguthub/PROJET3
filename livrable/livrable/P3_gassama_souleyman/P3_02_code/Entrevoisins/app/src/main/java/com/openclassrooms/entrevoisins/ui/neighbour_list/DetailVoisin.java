package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;


public class DetailVoisin extends AppCompatActivity  {

    private ImageView imageProfile;
    private TextView nomProfile;
    private TextView adresseProfile;
    private TextView telephoneProfile;
    private TextView faceBook;
    private TextView AproposProfile;
    private FloatingActionButton boutonFavoris;
    private Neighbour neighbour;
    private String FAVORIS = "favoris";
    private String NON_FAVORIS = "non_favoris";
    public static final String CODE_GET_INTENT = "CODE_GET_INTENT";


    @Override
    public boolean onSupportNavigateUp() {
//        finish() et onBackPressed(); meme resultat
        finish();
       return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent= getIntent();
        neighbour= (Neighbour) intent.getSerializableExtra(CODE_GET_INTENT);
        setContentView(R.layout.activity_detail_voisin);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(neighbour.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        ratacherLesId();
        chargeInfoProfileSurPage ();
        metreIconEnFavoris(neighbour.getFavoris());



        boutonFavoris.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                if((boutonFavoris.getContentDescription()==NON_FAVORIS)) {
                    metreIconEnFavoris(true);
                    DI.getNeighbourApiService().changeFavoris(neighbour);
                    boutonFavoris.setContentDescription(FAVORIS);

                }else {
                    metreIconEnFavoris(false);
                    DI.getNeighbourApiService().changeFavoris(neighbour);
                    boutonFavoris.setContentDescription(NON_FAVORIS);

                }
            }
        });
    }

    void metreIconEnFavoris(boolean favoris){
        if(favoris){
            boutonFavoris.setImageDrawable(getDrawable(R.drawable.star_48px));
            boutonFavoris.setContentDescription(FAVORIS);
        }else{
            boutonFavoris.setImageDrawable(getDrawable(R.drawable.ic_star_border_white_24dp));
            boutonFavoris.setContentDescription(NON_FAVORIS);
        }
    }


    public void ratacherLesId(){
        imageProfile=findViewById(R.id.imagePofil);
        nomProfile=findViewById(R.id.nomProfil);
        adresseProfile =  findViewById(R.id.adresseProfil);
        telephoneProfile = findViewById(R.id.telephoneProfil);
        faceBook=findViewById(R.id.webProfil);
        AproposProfile = findViewById(R.id.aPropos);
        boutonFavoris = findViewById(R.id.boutonFavoris);
    }
    public void chargeInfoProfileSurPage (){

        Glide.with(imageProfile.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(imageProfile);
        nomProfile.setText(neighbour.getName());
        adresseProfile.setText(neighbour.getAddress());
        telephoneProfile.setText(neighbour.getPhoneNumber());
        AproposProfile.setText(neighbour.getAboutMe());
        faceBook.setText("www.facebook.fr/"+neighbour.getName());
    }

}
