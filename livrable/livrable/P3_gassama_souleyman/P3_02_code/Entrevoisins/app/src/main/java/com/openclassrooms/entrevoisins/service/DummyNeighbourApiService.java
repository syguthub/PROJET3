package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
        public void changeFavoris(Neighbour neighbour) {
            for(int i=0; i<neighbours.size(); i++){
                if(neighbours.get(i).getId()==neighbour.getId()){
                    neighbours.get(i).setFavoris(!neighbours.get(i).getFavoris());
                }
            }
        }

    @Override
    public List<Neighbour> getFavoris() {
        List<Neighbour> getFavoris = new ArrayList<>();

        for(int i=0; i<neighbours.size(); i++){
            if(neighbours.get(i).getFavoris()){
                getFavoris.add(neighbours.get(i));
            }
        }
        return getFavoris;
    }
}
