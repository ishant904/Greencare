package com.example.malayishant.greencare.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

import static com.example.malayishant.greencare.AddTree.DOP;
import static com.example.malayishant.greencare.AddTree.GEO;
import static com.example.malayishant.greencare.AddTree.ID;
import static com.example.malayishant.greencare.AddTree.MANURE;
import static com.example.malayishant.greencare.AddTree.WATER;
import static com.example.malayishant.greencare.AddTree.WEED;

public class Tree {

    private String id;
    private Timestamp dop;
    private GeoPoint geo;
    private Timestamp water;
    private Timestamp weed;
    private Timestamp manure;

    public Tree() {
    }

    public Tree( Timestamp dop,GeoPoint geo,Timestamp water,
                Timestamp weed, Timestamp manure ) {

        this.dop = dop;
        this.geo = geo;
        this.water = water;
        this.weed = weed;
        this.manure = manure;
    }
    public Tree(String id, Timestamp dop,GeoPoint geo,Timestamp water,
                Timestamp weed, Timestamp manure ) {
        this.id = id;
        this.dop = dop;
        this.geo = geo;
        this.water = water;
        this.weed = weed;
        this.manure = manure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDop() {
        return dop;
    }

    public void setDop(Timestamp dop) {
        this.dop = dop;
    }

    public Timestamp getWater() {
        return water;
    }

    public void setWater(Timestamp water) {
        this.water = water;
    }

    public Timestamp getWeed() {
        return weed;
    }

    public void setWeed(Timestamp weed) {
        this.weed = weed;
    }

    public Timestamp getManure() {
        return manure;
    }

    public void setManure(Timestamp manure) {
        this.manure = manure;
    }

    public GeoPoint getGeo(){return  geo;}

    public Double getLatitude(){return geo.getLatitude();}

    public Double getLongitude(){return geo.getLongitude();}

    public void setGeo(GeoPoint geo) {
        this.geo = geo;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> tree = new HashMap<>();

        tree.put(ID, id);
        tree.put(DOP, this.dop);
        tree.put(WATER, this.water);
        tree.put(GEO,this.geo);
        tree.put(WEED, this.weed);
        tree.put(MANURE, this.manure);
        return tree;
    }
}
