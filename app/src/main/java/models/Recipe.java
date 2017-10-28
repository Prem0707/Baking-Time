package models;

import java.util.ArrayList;

/**
 * Created by Prem on 28-10-2017.
 */

public class Recipe {

    private int id;
    private String name;
    private String image;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;



    public Recipe(int id, String name, String image, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }
}
