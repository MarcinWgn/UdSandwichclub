package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView alsoKnowsAs = findViewById(R.id.also_known_tv);
        TextView alsoKnowsAsLabel = findViewById(R.id.also_known_id);

        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView placeOfOriginLabel = findViewById(R.id.place_of_origin_id);

        TextView ingredients = findViewById(R.id.ingredients_tv);

        TextView description = findViewById(R.id.description_tv);


        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOrigin.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);

        } else placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnowsAs.setVisibility(View.GONE);
            alsoKnowsAsLabel.setVisibility(View.GONE);
        } else {
            int size = sandwich.getAlsoKnownAs().size();
            for (int i = 0; i < size; i++) {
                if (size == 1 || size - 1 == i)
                    alsoKnowsAs.append(sandwich.getAlsoKnownAs().get(i));
                else alsoKnowsAs.append(sandwich.getAlsoKnownAs().get(i) + ", ");
            }
        }

        int size = sandwich.getIngredients().size();
        for (int i = 0; i < size; i++) {
            if (size == 1 || size - 1 == i)
                ingredients.append(sandwich.getIngredients().get(i));
            else ingredients.append(sandwich.getIngredients().get(i) + ", ");
        }

        description.setText(sandwich.getDescription());

    }

}
