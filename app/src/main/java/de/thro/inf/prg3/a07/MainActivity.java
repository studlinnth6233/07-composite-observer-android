package de.thro.inf.prg3.a07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import de.thro.inf.prg3.a07.api.OpenMensaAPI;
import de.thro.inf.prg3.a07.model.Meal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Defines the main entry point to the App
 */
public class MainActivity extends AppCompatActivity
{
	private ListView listViewMeals;
	private Button   buttonRefresh;
	private CheckBox checkBoxFilter;

	private ArrayAdapter<String> mealsContentAdapter;

	private List<Meal> meals;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		listViewMeals  = findViewById(R.id.meals);
		buttonRefresh  = findViewById(R.id.refresh);
		checkBoxFilter = findViewById(R.id.filterVegetarian);

		mealsContentAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.meal_entry);
		listViewMeals.setAdapter(mealsContentAdapter);

		meals = new ArrayList<>();

		final Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://openmensa.org/api/v2/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		final OpenMensaAPI mensaAPI = retrofit.create(OpenMensaAPI.class);

		buttonRefresh.setOnClickListener(view ->
		{
			meals.clear();

			Call<List<Meal>> call = mensaAPI.getMeals(229, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

			call.enqueue(new Callback<List<Meal>>()
			{
				@Override
				public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response)
				{
					if (!response.isSuccessful())
						System.out.println(response.body());

					else
					{
						meals.addAll(response.body());

						displayData(checkBoxFilter.isChecked());
					}
				}

				@Override
				public void onFailure(Call<List<Meal>> call, Throwable t)
				{
					System.out.println(t.getMessage());
				}
			});
		});

		checkBoxFilter.setOnClickListener(view -> displayData(checkBoxFilter.isChecked()));
	}

	private void displayData(boolean vegetarianFilter)
	{
		mealsContentAdapter.clear();

		if (vegetarianFilter)
			mealsContentAdapter.addAll(meals.stream()
				.filter(Meal::isVegetarian)
				.map(Meal::getName)
				.collect(Collectors.toCollection(ArrayList::new)));

		else
			mealsContentAdapter.addAll(meals.stream()
				.map(Meal::getName)
				.collect(Collectors.toCollection(ArrayList::new)));
	}
}
