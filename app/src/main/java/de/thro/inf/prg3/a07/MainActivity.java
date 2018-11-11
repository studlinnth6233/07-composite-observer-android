package de.thro.inf.prg3.a07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.thro.inf.prg3.a07.api.OpenMensaAPI;
import de.thro.inf.prg3.a07.model.Meal;
import de.thro.inf.prg3.a07.utils.MealsFilterUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	private final SimpleDateFormat dateFormat;
	private OpenMensaAPI openMensaAPI;
	private ArrayAdapter<Meal> mealArrayAdapter;
	private CheckBox vegetarianCheckbox;

	public MainActivity() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* create Retrofit instance to get a OpenMensaAPI proxy object */
		Retrofit retrofit = new Retrofit.Builder()
			/* no type adapters are required so the default GSON converter is fine */
			.addConverterFactory(GsonConverterFactory.create())
			/* retrieve OpenMensaAPI URL from resources */
			.baseUrl(getResources().getString(R.string.openmensa_api_base_url))
			.build();

		openMensaAPI = retrofit.create(OpenMensaAPI.class);

		// this will inflate the layout from res/layout/activity_main.xml
		setContentView(R.layout.activity_main);

		/* 'caching' the reference to the CheckBox */
		vegetarianCheckbox = findViewById(R.id.vegetarianChBox);

		/* create the ArrayAdapter without an given list instance */
		mealArrayAdapter = new ArrayAdapter<>(
			this,
			R.layout.meal_entry
		);

		/* 'caching' the reference to the ListView */
		final ListView mealsListView = findViewById(R.id.mealsListView);
		mealsListView.setAdapter(mealArrayAdapter);

		/* retrieving a reference to the refresh button */
		Button button =  findViewById(R.id.refreshButton);

		/* register click handler */
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/* delegate call to helper method to reduce nesting */
				onRefreshBtnClick();
			}
		});

		/* register click handler to the CheckBox to filter whenever the CheckBox is checked or unchecked */
		vegetarianCheckbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* delegate call to the same helper method as for the button click */
				onRefreshBtnClick();
			}
		});
	}

	/**
	 * Handle the click on the refresh button and changed state of the CheckBox
	 */
	private void onRefreshBtnClick() {
		/* execute call to the OpenMensaAPI
		 * note that the .enqueue(...) method is used instead of .execute() */
		openMensaAPI.getMeals(dateFormat.format(new Date())).enqueue(new Callback<List<Meal>>() {
			@Override
			public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
				/* delegate success handling to helper method to reduce nesting */
				onApiSuccess(response);
			}

			@Override
			public void onFailure(Call<List<Meal>> call, Throwable t) {
				/* display an error message if call failed */
				Toast.makeText(
					MainActivity.this,
					"An error occured while fetching meals",
					Toast.LENGTH_LONG
				).show();
			}
		});
	}

	/**
	 * Helper method to handle the response of the OpenMensaAPI
	 * @param response
	 */
	private void onApiSuccess(Response<List<Meal>> response) {
		/* check if response code was 2xx */
		if(response.isSuccessful()){
			/* remove elements from the adapter */
			mealArrayAdapter.clear();
			/* unwrap the retrieved meals */
			List<Meal> retrievedMeals = response.body();

			/* Check if we should filter for vegetarian meals */
			if(vegetarianCheckbox.isChecked()){
				List<Meal> vegetarian = MealsFilterUtility.filterForVegetarian(retrievedMeals);

				/* add all filtered meals to the adapter to display them in the view */
				mealArrayAdapter.addAll(vegetarian);
			}else {
				/* add all retrieved meals to the adapter because we haven't to filter for anything */
				mealArrayAdapter.addAll(retrievedMeals);
			}
		}else {
			/* display an error message if response code was not one of 2xx */
			Toast.makeText(
				MainActivity.this,
				"An error occured while fetching meals",
				Toast.LENGTH_LONG
			).show();
		}
	}
}
