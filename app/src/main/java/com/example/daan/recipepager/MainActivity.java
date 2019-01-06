package com.example.daan.recipepager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.daan.recipepager.model.Recipe;
import com.example.daan.recipepager.model.RecipeIngredientResponse;
import com.example.daan.recipepager.model.RecipeResponse;
import com.example.daan.recipepager.service.api.RecipeApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private RecipeApiService apiService;
    public static List<Recipe> recipes = new ArrayList<>();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init
        mViewPager = (ViewPager) findViewById(R.id.container);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        this.apiService = RecipeApiService.retrofit.create(RecipeApiService.class);
        getTrendingRecipes();
    }

    public void getTrendingRecipes(){
        Call<RecipeResponse> recipesCall = apiService.getTrendingRecipes();
        recipesCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful()) {
                    recipes = response.body().getRecipes();

                    for (int i = 0; i < recipes.size(); i++) {
                        loadIngredients(recipes.get(i), recipes.get(i).getRecipeId(), i);
                    }
                }
            }
            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                recipes = null;
            }
        });
    }

    public void loadIngredients(final Recipe recipe, String recipeId, final int totalAmount) {
        Call<RecipeIngredientResponse> recipeCall = apiService.getRecipeIngredients(recipeId);
        recipeCall.enqueue(new Callback<RecipeIngredientResponse>() {
            @Override
            public void onResponse(Call<RecipeIngredientResponse> call,
                                   Response<RecipeIngredientResponse> response) {
                if (response.isSuccessful()) {
                    recipe.setIngredients(response.body().getRecipe().getIngredients());

                    if (totalAmount == 2) {
                        initialiseAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeIngredientResponse> call, Throwable t) {
            }
        });
    }

    private void initialiseAdapter() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ImageView imageView = rootView.findViewById(R.id.imageView);
            TextView recipeTitle = rootView.findViewById(R.id.recipeTitle);
            TextView ingredientsText = rootView.findViewById(R.id.ingredientText);

            Recipe recipe = recipes.get(getArguments().getInt(ARG_SECTION_NUMBER));

            Glide.with(rootView.getContext())
                    .load(recipe.getImage())
                    .into(imageView);
            recipeTitle.setText(recipe.getTitle());

            StringBuilder ingredients = new StringBuilder();
            ingredients.append("Ingredients:\n");
            for (String line: recipe.getIngredients()) {
                ingredients.append("-" + line + "\n");
            }
            ingredientsText.setText(ingredients);

            return rootView;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return recipes.size();
        }
    }
}
