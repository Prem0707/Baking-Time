package json_parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import models.Ingredient;
import models.Recipe;
import models.Step;

/**
 * Created by Prem on 28-10-2017.
 * This is a utility class which will parse the Recipe data
 */

public class recipeJsonParcer {

    public static ArrayList<Recipe> getRecipeData(String mRecipeData) throws JSONException {

        ArrayList<Recipe> mRecipe = new ArrayList<>();

        if (mRecipeData != null) {

            JSONArray arrayOFJsonObject = new JSONArray(mRecipeData);

            for (int i = 0; i < arrayOFJsonObject.length(); i++) {
                Recipe recipe = new Recipe();

                JSONObject objectForEachRecipe = arrayOFJsonObject.getJSONObject(i);
                recipe.setId(objectForEachRecipe.getInt("id"));
                recipe.setName(objectForEachRecipe.getString("name"));
                recipe.setImage(objectForEachRecipe.getString("image"));
                recipe.setServings(objectForEachRecipe.getInt("servings"));

                ArrayList<Ingredient> ingredients = new ArrayList<>();
                JSONArray arrayOfIngredients = objectForEachRecipe.getJSONArray("ingredients");

                for (int p = 0; p < arrayOfIngredients.length(); p++) {
                    JSONObject objectOfIngredientArray = arrayOfIngredients.getJSONObject(p);
                    Ingredient ingredient = new Ingredient();
                    ingredient.setQuantity(objectOfIngredientArray.getDouble("quantity"));
                    ingredient.setMeasure(objectOfIngredientArray.getString("measure"));
                    ingredient.setIngredient(objectOfIngredientArray.getString("ingredient"));
                    ingredients.add(ingredient);
                }
                recipe.setIngredients(ingredients);

                ArrayList<Step> steps = new ArrayList<>();
                JSONArray arrayOfRecipeSteps = objectForEachRecipe.getJSONArray("steps");
                for (int q = 0; q < arrayOfRecipeSteps.length(); q++) {
                    JSONObject objectOfRecipeSteps = arrayOfRecipeSteps.getJSONObject(q);
                    Step step = new Step();
                    step.setId(objectOfRecipeSteps.getInt("id"));
                    step.setShortDescription(objectOfRecipeSteps.getString("shortDescription"));
                    step.setDescription(objectOfRecipeSteps.getString("description"));
                    step.setVideoURL(objectOfRecipeSteps.getString("videoURL"));
                    step.setThumbnailURL(objectOfRecipeSteps.getString("thumbnailURL"));
                    steps.add(step);
                }
                recipe.setSteps(steps);
                mRecipe.add(recipe);
            }
        }
        return mRecipe;
    }
}
