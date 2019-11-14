package de.thro.inf.prg3.a07.api;

import java.util.List;

import de.thro.inf.prg3.a07.model.Meal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface defining the Calls to OpenMensaAPI
 */
public interface OpenMensaAPI
{
	/**
	 * Request a list of meals for the given location at the given date
	 *
	 * @param canteen Location to request the meals for
	 * @param date    Date to request the meals for
	 *
	 * @return Call for requesting the list of meals
	 */
	@GET("canteens/{canteen}/days/{date}/meals")
	Call<List<Meal>> getMeals(@Path("canteen") int canteen, @Path("date") String date);
}
