package com.openclassrooms.entrevoisins.service;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void ajoutDeNeighbour (){

        Neighbour neighbour = new Neighbour(
                System.currentTimeMillis(),
                "non",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d",
                "adresse",
                "523456789003",
                "ABOUTmE"
        );

        service.createNeighbour(neighbour);

        assertTrue(service.getNeighbours().contains(neighbour));
    }

    @Test
    public void ajoutFavoris (){

        Neighbour neighbour =neighbourAleatoir();

        service.changeFavoris(neighbour);

        assertTrue(service.getFavoris().contains(neighbour));
    }

    @Test
    public void retraitFavoris (){

        Neighbour neighbour = neighbourAleatoir();

        service.changeFavoris(neighbour);

        Neighbour neighbourFavoris = service.getFavoris().get(0);

        service.changeFavoris(neighbourFavoris);

        assertFalse(service.getFavoris().contains(neighbourFavoris));
    }

    Neighbour neighbourAleatoir(){
        List<Neighbour> neighbours=DI.getNeighbourApiService().getNeighbours();

        Random random=new Random();

        int nbrF= random.nextInt(neighbours.size());

        return neighbours.get(nbrF);
    }

}
