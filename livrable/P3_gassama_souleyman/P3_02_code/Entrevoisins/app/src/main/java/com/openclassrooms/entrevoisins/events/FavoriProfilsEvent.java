package com.openclassrooms.entrevoisins.events;

//import android.content.Context;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.io.Serializable;

public class FavoriProfilsEvent implements Serializable {
    public Neighbour favoriProfils;

    public FavoriProfilsEvent(Neighbour favoriProfils){

        this.favoriProfils=favoriProfils;
    }
}
